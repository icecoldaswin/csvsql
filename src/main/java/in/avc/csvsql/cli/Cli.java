package in.avc.csvsql.cli;

import in.avc.csvsql.io.ConsoleIO;
import in.avc.csvsql.parser.SQLParser;

public class Cli {
    private final CliWorkflow cliWorkflow;

    public Cli() {
        cliWorkflow = new CliWorkflow(ConsoleIO.INSTANCE, new SQLParser());
    }

    private void run() {
        while (true) {
            try {
                cliWorkflow.prompt();
                cliWorkflow.streamData(cliWorkflow.parse(cliWorkflow.readCommand()));
            } catch(Exception e) {

            }
        }
    }

    public static void main(final String args[]) {
        Cli cli = new Cli();

        cli.run();
    }
}
