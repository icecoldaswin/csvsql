package in.avc.csvsql.io;

import java.io.PrintStream;

public class Output {
    private static final PrintStream DEFAULT_OUTPUT_STREAM = System.out;
    private final PrintStream outputStream;

    public Output(){
        outputStream = DEFAULT_OUTPUT_STREAM; // TODO: Load user preference from config file
    }

    public void write(final String string) {
        println(string);
    }

    public void writeTabDelimited(final String... strings) {
        for(String string: strings) {
            print(string);
            print("\t");
        }
    }

    private void print(final String string) {
        outputStream.print(string);
    }

    private void println(final String string) {
        outputStream.println(string);
    }
}
