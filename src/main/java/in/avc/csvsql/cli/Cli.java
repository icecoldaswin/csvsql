package in.avc.csvsql.cli;

import in.avc.csvsql.io.Output;

import java.util.concurrent.Callable;

public class Cli implements Callable<Void> {
    private final Output output;

    public Cli() {
        output = new Output();
    }

    @Override
    public Void call() throws Exception {
//        prompt();
        return null;
    }


}
