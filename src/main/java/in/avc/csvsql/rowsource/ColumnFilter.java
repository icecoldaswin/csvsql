package in.avc.csvsql.rowsource;

import in.avc.csvsql.model.Headers;

public class ColumnFilter {
    public static Object[] stripColumnsOtherThan(final Object[] dataValues, final Headers dataHeaders,
                                          final String[] headersToKeep) {
        Object[] selectedColumns = new Object[headersToKeep.length];
        int columnIndexes[] = new int[headersToKeep.length];

        for (int idx = 0; idx < columnIndexes.length; idx++) {
            columnIndexes[idx] = dataHeaders.getHeaderIndex(headersToKeep[idx]);
        }

        int selectedColumnIndex = 0;
        for (Integer columnIndex: columnIndexes) {
            selectedColumns[selectedColumnIndex] = dataValues[columnIndex];
            selectedColumnIndex++;
        }

        return selectedColumns;
    }
}
