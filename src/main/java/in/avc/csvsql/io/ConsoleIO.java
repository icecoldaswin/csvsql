package in.avc.csvsql.io;

import in.avc.csvsql.rowsource.RowSource;
import in.avc.csvsql.worker.consumer.OutputConsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ConsoleIO implements IO {
    private static final PrintStream DEFAULT_OUTPUT_STREAM = System.out;
    private static final BufferedReader DEFAULT_INPUT_STREAM = new BufferedReader(new InputStreamReader(System.in));
    private final PrintStream outputStream;

    public static final ConsoleIO INSTANCE = new ConsoleIO();

    private static final OutputConsumer OUTPUT_CONSUMER = OutputConsumer.consoleOutputConsumer();

    private ConsoleIO(){
        outputStream = DEFAULT_OUTPUT_STREAM; // TODO: Load user preference from config file
    }

    public void write(final String string) {
        print(string);
    }

    public void error(final String string) {
        write(ColoredTextGenerator.colorStringWith(string, ColoredTextGenerator.ConsoleTextColor.RED));
    }

    public void warn(final String string) {
        write(ColoredTextGenerator.colorStringWith(string, ColoredTextGenerator.ConsoleTextColor.YELLOW));
    }

    public void info(final String string) {
        write(ColoredTextGenerator.colorStringWith(string, ColoredTextGenerator.ConsoleTextColor.CYAN));
    }

    public void newLine() {
        println("");
    }

    public void writeTabDelimited(final String... strings) {
        for(String string: strings) {
            print(string);
            print("\t");
        }
    }

    public void streamData(final RowSource rowSource) {
        OUTPUT_CONSUMER.accept(rowSource);
    }

    public String readLine() throws IOException {
        return DEFAULT_INPUT_STREAM.readLine();
    }

    private void print(final String string) {
        outputStream.print(string);
    }

    private void println(final String string) {
        outputStream.println(string);
    }
}
