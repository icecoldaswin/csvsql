package in.avc.csvsql.cli;

import in.avc.csvsql.parser.SQLParser;
import in.avc.csvsql.rowsource.CsvFileRowSource;
import in.avc.csvsql.rowsource.ParsedQueryRowSource;
import in.avc.csvsql.worker.consumer.OutputConsumer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;

public class CliTest {

    @Test
    public void test() throws Exception {
        //

        SQLParser parser = new SQLParser();

        time(() -> {
            String[] colsFilter = {"FDC16","DCE1F","A0EA1"};
            ParsedQueryRowSource parsedQueryRowSource = new ParsedQueryRowSource(
                    parser.parse("select * from mycsv.csv"));
            OutputConsumer consoleOutputConsumer = OutputConsumer.consoleOutputConsumer();
            consoleOutputConsumer.accept(parsedQueryRowSource);

            return null;
        }, "Process parsed query rowsource.");
    }


    private <T> T time(final Callable<T> callable, final String comment) throws Exception {
        Instant start = Instant.now();
        try {
            return callable.call();
        } catch (Exception e) {
            System.out.println("An error occurred: ");
            e.printStackTrace();
            throw e;
        } finally {
            System.out.println(String.format("Task '%s' completed in %d milli seconds.", comment,
                    Duration.between(start, Instant.now()).toMillis()));
        }
    }
}
