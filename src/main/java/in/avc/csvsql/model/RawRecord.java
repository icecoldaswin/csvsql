package in.avc.csvsql.model;

import com.google.common.base.Preconditions;

public class RawRecord {
    private static final String DELIMITER = ","; // TODO: Read from config

    /**
     * Returns column values as an Object array.
     *
     * @param csvString
     * @return
     */
    public static Object[] fromCSVString(final String csvString) {
        Preconditions.checkNotNull(csvString);
        return csvString.split(DELIMITER);
    }
}
