package in.avc.csvsql.rowsource;

import in.avc.csvsql.worker.consumer.ConsoleOutputConsumer;
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
    public void test() throws Exception {
//        runMeasuringTime(() -> {
//            File file = new File("mycsv.csv");
//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
//            for (int i = 0; i < 500000; i++) {
//                StringBuffer rec = new StringBuffer();
//                for (int j = 0; j < 10; j++) {
//                    rec.append(String.format("%s,", generateString("ABCDEF0123456789", 5)));
//                }
//                bufferedWriter.write(rec.toString());
//                bufferedWriter.newLine();
//            }
//            bufferedWriter.flush();
//            bufferedWriter.close();
//
//            return null;
//        });

        runMeasuringTime(() -> {
            String[] colsFilter = {"612F6", "8D2DB"};
            ParsedQueryRowSource parsedQueryRowSource = new ParsedQueryRowSource(
                    new CsvFileRowSource(new File("mycsv.csv"), true),
                    colsFilter);
            ConsoleOutputConsumer consoleOutputConsumer = new ConsoleOutputConsumer();
            consoleOutputConsumer.accept(parsedQueryRowSource);

            return null;
        });
    }

    private static String generateString(String characters, int length)
    {
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    private <T> T runMeasuringTime(final Callable<T> callable) throws Exception {
        Instant start = Instant.now();
        try {
            return callable.call();
        } catch (Exception e) {
            System.out.println("An error occurred: ");
            e.printStackTrace();
            throw e;
        } finally {
            System.out.println("Done in " + Duration.between(start, Instant.now()).toMillis() + "ms.");
        }
    }
}
