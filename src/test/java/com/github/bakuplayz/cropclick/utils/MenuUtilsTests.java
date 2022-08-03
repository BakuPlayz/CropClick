package com.github.bakuplayz.cropclick.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class MenuUtilsTests {

    @Test
    public void isAir() {
        assertAll("Checks wheaten or not the item is air.",
                () -> assertTrue(MenuUtils.isAir(null)),
                () -> {
                    ItemStack stack = new ItemStack(Material.AIR);
                    assertTrue(MenuUtils.isAir(stack));
                }
        );
    }

}