package com.github.bakuplayz.cropclick.autofarm;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;


public enum AutofarmState {

    SELECTED,
    UNLINKED,
    LINKED;

    @Override
    public @NotNull String toString() {
        return StringUtils.capitalize(name().toLowerCase());
    }

}