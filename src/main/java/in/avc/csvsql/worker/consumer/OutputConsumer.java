package in.avc.csvsql.worker.consumer;

import in.avc.csvsql.io.ConsoleIO;
import in.avc.csvsql.io.IO;
import in.avc.csvsql.rowsource.RowSource;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.function.Consumer;

public class OutputConsumer implements Consumer<RowSource> {
    private final IO io;

    private OutputConsumer(final IO io) {
        // Prevent instantiation
        this.io = io;
    }

    public static OutputConsumer nullOutputConsumer() {
        return new OutputConsumer(null);
    }

    public static OutputConsumer consoleOutputConsumer() {
        return new OutputConsumer(ConsoleIO.INSTANCE);
    }

    @Override
    public void accept(final RowSource rowSource) {
        io.newLine();
        io.newLine();

        printRecord((Object[])rowSource.getHeaders().asArray());
        io.write("------------------------------------------------");

        io.newLine();

        long rowsProcessed = rowSource.streamData().map(columnValues -> {this.printRecord(columnValues); return null;})
                                   .count();

        io.info(String.format("Rows processed: %d.", rowsProcessed));
        io.newLine();
    }

    private void printRecord(final Object... objects) {
        io.writeTabDelimited(Arrays.copyOf(objects, objects.length, String[].class));
        io.newLine();
    }

    private static class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
        }
    }
}
