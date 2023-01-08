package com.github.bakuplayz.cropclick.runnables;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;


/**
 * An interface designed to handle a Runnable.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Runnable {

    /**
     * Executes the implementing object's runnable when called.
     */
    void run();

    /**
     * Clears the implementing object's runnable {@link Timer timer} when called.
     *
     * @return a cleaning task.
     */
    @NotNull TimerTask clean();

}