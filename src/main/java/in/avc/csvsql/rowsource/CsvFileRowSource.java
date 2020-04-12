package in.avc.csvsql.rowsource;

import in.avc.csvsql.model.Headers;
import in.avc.csvsql.model.RawRecord;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class CsvFileRowSource implements RowSource {
    private Headers headers;
    private Stream<Object[]> rawRecords;

    public CsvFileRowSource(final File file, final boolean firstRowAsHeader) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            headers = firstRowAsHeader ? new Headers(RawRecord.fromCSVString(reader.readLine())) : null;
            Stream<String> allData = reader.lines();
            rawRecords = allData.map(RawRecord::fromCSVString);
        } catch (IOException e) {

            throw new RuntimeException(e); // TODO: Define package specific runnable exception
        }
    }

    @Override
    public Stream<Object[]> streamData() {
        return rawRecords;
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
