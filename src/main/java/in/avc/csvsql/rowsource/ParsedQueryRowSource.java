package in.avc.csvsql.rowsource;

import com.google.common.collect.Iterables;
import in.avc.csvsql.model.Headers;
import in.avc.csvsql.parser.Keyword;
import in.avc.csvsql.parser.model.ParseTree;
import in.avc.csvsql.parser.model.ParseTreeNode;
import in.avc.csvsql.parser.model.QueryPart;
import in.avc.csvsql.parser.model.StringList;

import java.io.File;
import java.util.stream.Stream;

public class ParsedQueryRowSource implements RowSource {
    private final RowSource rowSource;
    private final String[] columnsToFilter;
    private final boolean namedColumnSelectionRequested;
    private Headers headers;

    public ParsedQueryRowSource(final RowSource rowSource, final String[] columnsToSelect) {
        this.rowSource = rowSource;
        this.columnsToFilter = columnsToSelect;
        namedColumnSelectionRequested = !(columnsToSelect == null || columnsToSelect.length == 0);
        this.headers = namedColumnSelectionRequested
                ? new Headers(columnsToSelect)
                : rowSource.getHeaders();
    }

    public ParsedQueryRowSource(final ParseTree tree) {
        ParseTreeNode rootNode = tree.getRoot();

        Object columnSpecification = null;
        Object rowSourceSpecification = null;

        for (Object child : rootNode.getChildren()) {
            ParseTreeNode node = (ParseTreeNode)child;
            QueryPart qPart = (QueryPart)node.getContent();

            if (qPart.isRepresentsARowsource()) {
                rowSourceSpecification = Iterables.getOnlyElement(((StringList) qPart).getStrings());
            } else if (qPart != Keyword.SELECT && qPart != Keyword.FROM) {
                columnSpecification = qPart;
            }
        }

        columnsToFilter = (this.namedColumnSelectionRequested = (columnSpecification != Keyword.STAR))
                ? ((StringList)columnSpecification).getAsArray()
                : null;

        rowSource = (rowSourceSpecification instanceof String)
                ? new CsvFileRowSource(new File((String)rowSourceSpecification), true)
                : (RowSource)rowSourceSpecification;
    }

    public final Stream<Object[]> streamData() {
        Stream<Object[]> dataStream = rowSource.streamData();
        if (namedColumnSelectionRequested) {
            dataStream = dataStream.map(objects -> ColumnFilter.stripColumnsOtherThan(
                    objects, rowSource.getHeaders(), columnsToFilter));
        }

        return dataStream;
    }

    @Override
    public Headers getHeaders() {
        return this.rowSource.getHeaders();
    }

    @Override
    public void setHeaders(final Headers headers) {
        // No-op - a parsed query rowsource's headers are set only when the query is parsed
    }
}
