package com.github.bakuplayz.cropclick.autofarm;

import lombok.Getter;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

public class Container {

    private @Getter Inventory inventory;

    private @Getter ContainerType type;

    public Container(Inventory inventory,
                     ContainerType type) {
        this.inventory = inventory;
        this.type = type;
    }
}