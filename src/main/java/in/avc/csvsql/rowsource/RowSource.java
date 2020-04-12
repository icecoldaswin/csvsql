package in.avc.csvsql.rowsource;

import in.avc.csvsql.model.Headers;

import java.util.stream.Stream;

public interface RowSource {
    Stream<Object[]> streamData();
    Headers getHeaders();
    void setHeaders(Headers headers);
}
