package in.avc.csvsql.cli;

import in.avc.csvsql.io.ConsoleIO;
import in.avc.csvsql.parser.SQLParser;
import in.avc.csvsql.parser.model.ParseTree;
import in.avc.csvsql.rowsource.ParsedQueryRowSource;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;

public class CliWorkflow {
    private final ConsoleIO consoleIO;
    private final SQLParser parser;

    public CliWorkflow(final ConsoleIO consoleIO, final SQLParser parser){
        this.consoleIO = consoleIO;
        this.parser = parser;
    }

    public void prompt() {
        this.consoleIO.write("csvsql > ");
    }

    public String readCommand() {
        try {
            return this.consoleIO.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ParseTree parse(final String command) {
        try {
            return time(() -> parser.parse(command), "ParseCommand");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void streamData(final ParseTree parseTree) {
        try {
            time(() -> {
                consoleIO.streamData(new ParsedQueryRowSource(parseTree));
                return null;
            }, "StreamData");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T time(final Callable<T> callableTask, final String taskName) throws Exception {
        Instant start = Instant.now();
        try {
            return callableTask.call();
        } catch (Throwable e) {
            consoleIO.error("An error occurred: ");
            e.printStackTrace();
            throw e;
        } finally {
            consoleIO.info(String.format("Task '%s' completed in %d milli seconds.", taskName,
                    Duration.between(start, Instant.now()).toMillis()));
            consoleIO.newLine();
        }
    }
}
