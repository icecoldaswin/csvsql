package in.avc.csvsql.cli;

import in.avc.csvsql.io.Output;

public class Cli implements Runnable {
    private final Output output;

    public Cli() {
        output = new Output();
    }

    public void run () {
        prompt();
    }

    private void prompt() {
        output.write("> ");
    }
}
