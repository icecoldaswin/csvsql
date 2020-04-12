package in.avc.csvsql.parser.model;

import in.avc.csvsql.rowsource.RowSource;

public class CompiledSubQuery implements QueryPart {
    // Each sub-query will act as a row-source
    private RowSource rowSource;

    @Override
    public boolean isRowsource() {
        return true;
    }
}
