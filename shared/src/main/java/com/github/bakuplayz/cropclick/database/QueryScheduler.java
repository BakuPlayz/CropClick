package com.github.bakuplayz.cropclick.database;

import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.tasks.TaskScheduler;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class QueryScheduler {
    
    private static final long RESTART_WORKERS_INTERVAL = 1000 * 60 * 60 * 20;

    private static final int NUM_WORKERS = 4;

    private final AtomicInteger workers;

    private final TaskScheduler taskScheduler;

    private final ConnectionPool connectionPool;

    private final BlockingQueue<DatabaseJob> queuedJobs;


    public QueryScheduler(@NotNull ConnectionPool connectionPool, @NotNull TaskScheduler taskScheduler) {
        this.queuedJobs = new LinkedBlockingQueue<>();
        this.connectionPool = connectionPool;
        this.taskScheduler = taskScheduler;
        this.workers = new AtomicInteger();

        start();
    }


    /**
     * Checks whether a connection is available for executing queries.
     *
     * @return true if available, false otherwise.
     */
    public boolean canQuery() {
        return connectionPool.isEstablished();
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


    /**
     * Starts the database worker system. This schedules a repeating task that monitors
     * the current number of workers and restarts them if all have stopped.
     */
    private void start() {
        taskScheduler.scheduleRepeatingTask(() -> {
            if (workers.get() == 0) {
                workers.set(NUM_WORKERS);
                for (int i = 0; i < NUM_WORKERS; ++i) {
                    taskScheduler.scheduleLater(this::startWorker, 0);
                }
            }
        }, 0, RESTART_WORKERS_INTERVAL);
    }


    /**
     * A long-running worker method that continuously takes database jobs
     * from the queue and processes them. If interrupted (typically on shutdown),
     * it stops and updates the active worker count, to make the other restart task
     * aware of having to restart once it reaches zero.
     */
    private void startWorker() {
        while (true) {
            Connection connection = null;
            try {
                DatabaseJob job = queuedJobs.take();
                connection = connectionPool.acquire();
                job.execute(connection);
            } catch (InterruptedException e) {
                Log.debug("Database worker is interrupted, stopping.", e);
                Thread.currentThread().interrupt();
                break;
            } finally {
                if (connection != null) {
                    connectionPool.release(connection);
                }
            }
        }

        // Make sure we decrement our workers if they crash
        // so that we can handle creation of new workers if
        // all crashed.
        workers.decrementAndGet();
    }


    public interface DatabaseJob {

        void execute(@NotNull Connection connection);

    }

}
