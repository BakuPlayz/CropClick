package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public class MenuUtilTest {

    @Before
    public void setUp() {
        MockBukkit.mock();
    }

    @After
    public void tearDown() {
        MockBukkit.unload();
    }

    @Test
    public void isAir() {
        assertAll("Checks wheaten or not the item is air.",
                () -> assertTrue(MenuUtil.isAir(null)),
                () -> {
                    ItemStack stack = new ItemStack(Material.AIR);
                    assertTrue(MenuUtil.isAir(stack));
                }
        );
    }

}