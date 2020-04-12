package in.avc.csvsql.rowsource;

import in.avc.csvsql.model.Headers;

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
        return this.headers;
    }

    @Override
    public void setHeaders(final Headers headers) {
        this.headers = headers;
    }
}
