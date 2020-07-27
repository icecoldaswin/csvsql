package in.avc.csvsql.io;

import in.avc.csvsql.rowsource.RowSource;

import java.io.IOException;

public interface IO {
    public void write(final String string);
    public void warn(final String string);
    public void info(final String string);
    public void error(final String string);
    public void writeTabDelimited(final String... strings);
    public void newLine();
    public void streamData(final RowSource rowSource);
    public String readLine() throws IOException;
}
