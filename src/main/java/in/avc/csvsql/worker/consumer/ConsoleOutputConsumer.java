package in.avc.csvsql.worker.consumer;

import in.avc.csvsql.rowsource.RowSource;

import java.util.function.Consumer;

public class ConsoleOutputConsumer implements Consumer<RowSource> {
    @Override
    public void accept(final RowSource rowSource) {
        System.out.println("Data: ");
        printRecord((Object[])rowSource.getHeaders().asArray());
        System.out.println("------------------------------------------------");
        long rowsProcessed = rowSource.streamData().map(columnValues -> {this.printRecord(columnValues); return null;})
                                   .count();
        System.out.println(String.format("Rows processed: %d.", rowsProcessed));
    }

    private void printRecord(final Object... objects) {
        for(Object object: objects) {
            System.out.print(String.format("%s\t\t", object));
        }
        System.out.println();
    }
}
