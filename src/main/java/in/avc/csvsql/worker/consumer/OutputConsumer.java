package in.avc.csvsql.worker.consumer;

import in.avc.csvsql.rowsource.RowSource;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

public class OutputConsumer implements Consumer<RowSource> {
    private PrintStream printStream;

    private OutputConsumer(final PrintStream printStream) {
        // Prevent instantiation
        this.printStream = printStream;
    }

    public static OutputConsumer nullOutputConsumer() {
        return new OutputConsumer(new PrintStream(new NullOutputStream()));
    }

    public static OutputConsumer consoleOutputConsumer() {
        return new OutputConsumer(System.out);
    }

    @Override
    public void accept(final RowSource rowSource) {
        printStream.println("Data: ");
        printRecord((Object[])rowSource.getHeaders().asArray());
        printStream.println("------------------------------------------------");
        long rowsProcessed = rowSource.streamData().map(columnValues -> {this.printRecord(columnValues); return null;})
                                   .count();
        printStream.println(String.format("Rows processed: %d.", rowsProcessed));
    }

    private void printRecord(final Object... objects) {
        for(Object object: objects) {
            printStream.print(String.format("%s\t\t", object));
        }
        printStream.println();
    }

    private static class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
        }
    }
}
