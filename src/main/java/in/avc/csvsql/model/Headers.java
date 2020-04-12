package in.avc.csvsql.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Headers {
    private final Map<String, Integer> columnHeaderIndexMap = new LinkedHashMap();
    private final String[] headerValues;

    public Headers(final Object[] headerValues) {
        this.headerValues = (String[])headerValues;
        for (int i=0; i < headerValues.length; i++) {
            columnHeaderIndexMap.put((String)headerValues[i], i);
        }
    }

    public int getHeaderIndex(final String string) {
        return columnHeaderIndexMap.get(string);
    }

    public String[] asArray() {
        return headerValues;
    }

    public int size() {
        return headerValues.length;
    }
}
