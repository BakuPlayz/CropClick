package com.github.bakuplayz.cropclick.utils;

import org.jetbrains.annotations.NotNull;

import java.util.TimerTask;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Runnable {

    /**
     *
     * */
    void run();

    /**
     *
     * */
    @NotNull TimerTask clean();

}