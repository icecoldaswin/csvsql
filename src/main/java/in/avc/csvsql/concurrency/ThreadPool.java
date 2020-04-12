package in.avc.csvsql.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // TODO: Use a config file
}
