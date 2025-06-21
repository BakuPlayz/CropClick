package com.github.bakuplayz.cropclick.configurations.observers;

import org.jetbrains.annotations.NotNull;

public interface ConfigurationValueObserver {

    <T> void onValueChanged(@NotNull T value);

}
