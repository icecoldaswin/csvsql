package in.avc.csvsql.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // TODO: Use a config file

    public <T> Future<T> submitAsync(final Callable<T> callable) {
        return executorService.submit(callable);
    }

    public <T> T submitSync(final Callable<T> callable) {
        try {
            return executorService.submit(callable).get();
        } catch (ExecutionException|InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
