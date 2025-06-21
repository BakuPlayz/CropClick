package com.github.bakuplayz.cropclick.datacontainers.migrations;

public interface Migration {

    String getVersion();

    void migrate();

}
