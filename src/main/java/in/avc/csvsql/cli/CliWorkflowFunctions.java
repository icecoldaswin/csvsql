package in.avc.csvsql.cli;

import in.avc.csvsql.io.Output;

public class CliWorkflowFunctions {
    public static void prompt(final Output output) {
        output.write("> ");
    }

    public static void readCommand(final Output output) {
        output.readLine();
    }
}
