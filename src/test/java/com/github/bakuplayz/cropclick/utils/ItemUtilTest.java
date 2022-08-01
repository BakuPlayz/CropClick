package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class ItemUtilTest {

    private ItemUtil itemUtil;

    private CropClick plugin;

    @BeforeAll
    public void beforeAll() {
        MockBukkit.mock();
        plugin = MockBukkit.load(CropClick.class);
    }

    @BeforeEach
    public void setUp() {
        itemUtil = new ItemUtil(Material.STONE);
    }

    @After
    public void tearDown() {
        MockBukkit.unload();
    }

    @Test
    public void testConstructor() {
        ItemUtil util = new ItemUtil(Material.STONE);
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertEquals(Material.STONE, util.getMaterial()),
                () -> assertEquals("", util.getName()),
                () -> assertEquals(1, util.getAmount()),
                () -> assertNull(util.getLore())
        );
    }

    @Test
    public void testConstructor1() {
        ItemUtil util = new ItemUtil(Material.STONE, 2);
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertEquals(Material.STONE, util.getMaterial()),
                () -> assertEquals("", util.getName()),
                () -> assertEquals(2, util.getAmount()),
                () -> assertNull(util.getLore())
        );
    }

    @Test
    public void testSetAmount() {
        ItemUtil result = itemUtil.setAmount(10);
        assertAll("Check wheaten or not the #setAmount updates the amount.",
                () -> assertNotEquals(-1, result.getAmount()),
                () -> assertNotEquals(1, result.getAmount()),
                () -> assertEquals(10, result.getAmount())
        );
    }

    @Test
    public void testSetDamage() {
        ItemUtil result = itemUtil.setDamage(10);
        assertAll("Check wheaten or not the #setDamage updates the damage.",
                () -> assertNotEquals(-1, result.getDamage()),
                () -> assertNotEquals(1, result.getDamage()),
                () -> assertEquals(10, result.getDamage())
        );
    }

    @Test
    public void testSetMaterial() {
        ItemUtil result = itemUtil.setMaterial(5);
        assertAll("Check wheaten or not the #setMaterial updates the material.",
                () -> assertNotEquals(null, result.getMaterial()),
                () -> assertNotEquals(Material.AIR, result.getMaterial()),
                () -> assertEquals(Material.WOOD, result.getMaterial())
        );
    }

    @Test
    public void testSetMaterial2() {
        ItemUtil result = itemUtil.setMaterial(Material.STONE);
        assertAll("Check wheaten or not the #setMaterial updates the material.",
                () -> assertNotEquals(null, result.getMaterial()),
                () -> assertNotEquals(Material.AIR, result.getMaterial()),
                () -> assertEquals(Material.STONE, result.getMaterial())
        );
    }

    @Test
    public void testSetName() {
        ItemUtil result = itemUtil.setName("Name");
        assertAll("Check wheaten or not the #setName updates the name.",
                () -> assertNotEquals(null, result.getName()),
                () -> assertNotEquals("name", result.getName()),
                () -> assertEquals("Name", result.getName())
        );
    }

    @Test
    public void testSetName2() {
        ItemUtil result = itemUtil.setName(plugin, LanguageAPI.Menu.BACK_ITEM_NAME);
        assertAll("Check wheaten or not the #setName updates the name.",
                () -> assertNotEquals(null, result.getName()),
                () -> assertNotEquals("name", result.getName()),
                () -> assertEquals("§cBack", result.getName())
        );
    }

    @Test
    public void testSetLore() {
        ItemUtil result = itemUtil.setLore(Collections.singletonList("Test"));
        assertAll("Check wheaten or not the #setLore updates the lore.",
                () -> assertNotEquals(null, result.getLore()),
                () -> assertNotEquals(new ArrayList<>(), result.getLore()),
                () -> assertNotEquals(Collections.singletonList("TEst"), result.getLore()),
                () -> assertEquals(Collections.singletonList("Test"), result.getLore())
        );
    }

    @Test
    public void testSetLore2() {
        ItemUtil result = itemUtil.setLore("Test");
        assertAll("Check wheaten or not the #setLore updates the lore.",
                () -> assertNotEquals(null, result.getLore()),
                () -> assertNotEquals(new ArrayList<>(), result.getLore()),
                () -> assertNotEquals(Collections.singletonList("TEst"), result.getLore()),
                () -> assertEquals(Collections.singletonList("Test"), result.getLore())
        );
    }

    @Test
    public void testToItemStack() {
        ItemStack result = itemUtil.toItemStack();
        assertAll("Check wheaten or not the #toItemStack results in a correct ItemStack.",
                () -> assertNotEquals(null, result),
                () -> assertNotEquals(new ItemUtil(Material.AIR).toItemStack(), result),
                () -> assertEquals(new ItemUtil(Material.STONE).toItemStack(), result)
        );
    }

}