package com.github.bakuplayz.cropclick.sql;

import com.github.bakuplayz.cropclick.LoggerContext;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class QueryScheduler implements LoggerContext {


    private static final int NUM_WORKERS = 4;


    private final ConnectionPool pool;

    private final BlockingQueue<DatabaseJob> queuedJobs;


    public QueryScheduler(@NotNull ConnectionPool pool) {
        this.queuedJobs = new LinkedBlockingQueue<>();
        this.pool = pool;

        start();
    }


    /**
     * Checks whether a connection is available for executing queries.
     *
     * @return true if available, false otherwise.
     */
    public boolean canQuery() {
        return pool.isEstablished();
    }


    /**
     * Queues the database job to be executed asynchronously later,
     * inside a worker thread.
     *
     * @param job the job to queue.
     */
    public void queue(@NotNull DatabaseJob job) {
        queuedJobs.add(job);
    }


    private void start() {
        for (int i = 0; i < NUM_WORKERS; ++i) {
            new Thread(() -> {
                while (true) {
                    try {
                        DatabaseJob job = queuedJobs.take();
                        Connection connection = pool.acquire();
                        job.execute(connection);
                        pool.release(connection);
                    } catch (InterruptedException e) {
                        logDebug("(Debug) Database worker is interrupted.", e);
                        Thread.currentThread().interrupt();
                    }
                }
            }, String.format("(CropClick-%d)", i)).start();
        }
    }


    public interface DatabaseJob {

        void execute(@NotNull Connection connection);

    }

}
