package in.avc.csvsql.rowsource;

import in.avc.csvsql.parser.SQLParser;
import in.avc.csvsql.parser.model.ParseTree;
import in.avc.csvsql.worker.consumer.OutputConsumer;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.Callable;

public class ParsedQueryRowSourceTest {
    @Test
    public void test_with_CsvRowSource() throws Exception {
        File file = new File("mycsv.csv");
        if (!file.exists()) {
            time(() -> {

                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                Random rng = new Random();
                for (int i = 0; i < 500000; i++) {
                    StringBuffer rec = new StringBuffer();
                    for (int j = 0; j < 10; j++) {
                        rec.append(String.format("%s,", generateString(rng, "ABCDEF0123456789", 5)));
                    }
                    bufferedWriter.write(rec.toString());
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                bufferedWriter.close();

                return null;
            }, "Generate a CSV file");
        }

        time(() -> {
            String[] colsFilter = {"FDC16","DCE1F","A0EA1"};
            ParsedQueryRowSource parsedQueryRowSource = new ParsedQueryRowSource(
                    new CsvFileRowSource(file, true),
                    colsFilter);
            OutputConsumer consoleOutputConsumer = OutputConsumer.consoleOutputConsumer();
            consoleOutputConsumer.accept(parsedQueryRowSource);

            return null;
        }, "Process parsed query rowsource.");
    }

    @Test
    public void test_with_ParseTree() throws Exception {

        SQLParser parser = new SQLParser();

        ParseTree tree = parser.parse("select * from src/test/data/mycsv2.csv");

        time(() -> {
            ParsedQueryRowSource parsedQueryRowSource = new ParsedQueryRowSource(tree);
            OutputConsumer consoleOutputConsumer = OutputConsumer.consoleOutputConsumer();
            consoleOutputConsumer.accept(parsedQueryRowSource);

            return null;
        }, "Process parsed query rowsource.");
    }

    private static String generateString(final Random rng, final String characters, final int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
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
