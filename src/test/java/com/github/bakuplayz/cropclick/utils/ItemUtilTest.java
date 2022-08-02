package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
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

    @Before
    public void beforeAll() {
        MockBukkit.mock();
    }

    @BeforeEach
    public void setUp() {
        plugin = MockBukkit.load(CropClick.class);
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
    public void testConstructor2() {
        ItemUtil util = new ItemUtil(Material.STONE, (short) 2);
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertEquals(Material.STONE, util.getMaterial()),
                () -> assertEquals("", util.getName()),
                () -> assertEquals(2, util.getDamage()),
                () -> assertEquals(1, util.getAmount()),
                () -> assertNull(util.getLore())
        );
    }

    @Test
    public void testConstructor3() {
        ItemUtil util = new ItemUtil(Material.STONE, "Arkham");
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertEquals(Material.STONE, util.getMaterial()),
                () -> assertEquals("Arkham", util.getName()),
                () -> assertEquals(1, util.getAmount()),
                () -> assertNull(util.getLore())
        );
    }

    @Test
    public void testConstructor4() {
        ItemUtil util = new ItemUtil(Material.STONE, "Arkham", 2);
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertEquals(Material.STONE, util.getMaterial()),
                () -> assertEquals("Arkham", util.getName()),
                () -> assertEquals(2, util.getAmount()),
                () -> assertNull(util.getLore())
        );
    }

    @Test
    public void testConstructor5() {
        ItemUtil util = new ItemUtil(Material.STONE, "Arkham", 2, (short) 2);
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertEquals(Material.STONE, util.getMaterial()),
                () -> assertEquals("Arkham", util.getName()),
                () -> assertEquals(2, util.getAmount()),
                () -> assertEquals(2, util.getDamage()),
                () -> assertNull(util.getLore())
        );
    }

    @Test
    public void testConstructor6() {
        ItemUtil util = new ItemUtil(Material.STONE, "Arkham", 2, Collections.singletonList("City"));
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertNotNull(util.getLore()),
                () -> assertEquals(2, util.getAmount()),
                () -> assertEquals("Arkham", util.getName()),
                () -> assertEquals(Material.STONE, util.getMaterial()),
                () -> assertEquals(Collections.singletonList("City"), util.getLore())
        );
    }

    @Test
    public void testConstructor7() {
        ItemUtil util = new ItemUtil(5);
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertNull(util.getLore()),
                () -> assertNull(util.getMeta()),
                () -> assertEquals("", util.getName()),
                () -> assertEquals(1, util.getAmount()),
                () -> assertEquals(Material.WOOD, util.getMaterial())
        );
    }

    @Test
    public void testConstructor8() {
        ItemUtil util = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2)
        );
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertNull(util.getName()),
                () -> assertEquals(2, util.getAmount()),
                () -> assertEquals(2, util.getDamage()),
                () -> assertEquals(new ArrayList<>(), util.getLore()),
                () -> assertEquals(new ItemMetaMock(), util.getMeta()),
                () -> assertEquals(Material.STONE, util.getMaterial())
        );
    }

    @Test
    public void testConstructor9() {
        ItemUtil util = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2),
                10
        );
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertNull(util.getName()),
                () -> assertEquals(2, util.getDamage()),
                () -> assertEquals(10, util.getAmount()),
                () -> assertEquals(new ArrayList<>(), util.getLore()),
                () -> assertEquals(new ItemMetaMock(), util.getMeta()),
                () -> assertEquals(Material.STONE, util.getMaterial())
        );
    }

    @Test
    public void testConstructor10() {
        ItemUtil util = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2),
                "Test"
        );
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertEquals(2, util.getDamage()),
                () -> assertEquals(2, util.getAmount()),
                () -> assertEquals("Test", util.getName()),
                () -> assertEquals(new ArrayList<>(), util.getLore()),
                () -> assertEquals(new ItemMetaMock(), util.getMeta()),
                () -> assertEquals(Material.STONE, util.getMaterial())
        );
    }

    @Test
    public void testConstructor11() {
        ItemUtil util = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2),
                "Test",
                10
        );
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertEquals(2, util.getDamage()),
                () -> assertEquals(10, util.getAmount()),
                () -> assertEquals("Test", util.getName()),
                () -> assertEquals(new ArrayList<>(), util.getLore()),
                () -> assertEquals(new ItemMetaMock(), util.getMeta()),
                () -> assertEquals(Material.STONE, util.getMaterial())
        );
    }

    @Test
    public void testConstructor12() {
        ItemUtil util = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2),
                "Test",
                10,
                Collections.singletonList("Arkham")
        );
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(util),
                () -> assertEquals(2, util.getDamage()),
                () -> assertEquals(10, util.getAmount()),
                () -> assertEquals("Test", util.getName()),
                () -> assertEquals(new ItemMetaMock(), util.getMeta()),
                () -> assertEquals(Collections.singletonList("Arkham"), util.getLore()),
                () -> assertEquals(Material.STONE, util.getMaterial())
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
                () -> assertNotNull(result.getMaterial()),
                () -> assertNotEquals(Material.AIR, result.getMaterial()),
                () -> assertEquals(Material.WOOD, result.getMaterial())
        );
    }

    @Test
    public void testSetMaterial2() {
        ItemUtil result = itemUtil.setMaterial(Material.STONE);
        assertAll("Check wheaten or not the #setMaterial updates the material.",
                () -> assertNotNull(result.getMaterial()),
                () -> assertNotEquals(Material.AIR, result.getMaterial()),
                () -> assertEquals(Material.STONE, result.getMaterial())
        );
    }

    @Test
    public void testSetName() {
        ItemUtil result = itemUtil.setName("Name");
        assertAll("Check wheaten or not the #setName updates the name.",
                () -> assertNotNull(result.getName()),
                () -> assertNotEquals("name", result.getName()),
                () -> assertEquals("Name", result.getName())
        );
    }

    @Test
    public void testSetName2() {
        ItemUtil result = itemUtil.setName(plugin, LanguageAPI.Menu.BACK_ITEM_NAME);
        assertAll("Check wheaten or not the #setName updates the name.",
                () -> assertNotNull(result.getName()),
                () -> assertNotEquals("name", result.getName()),
                () -> assertEquals("§cBack", result.getName())
        );
    }

    @Test
    public void testSetLore() {
        ItemUtil result = itemUtil.setLore(Collections.singletonList("Test"));
        assertAll("Check wheaten or not the #setLore updates the lore.",
                () -> assertNotNull(result.getLore()),
                () -> assertNotEquals(new ArrayList<>(), result.getLore()),
                () -> assertNotEquals(Collections.singletonList("TEst"), result.getLore()),
                () -> assertEquals(Collections.singletonList("Test"), result.getLore())
        );
    }

    @Test
    public void testSetLore2() {
        ItemUtil result = itemUtil.setLore("Test");
        assertAll("Check wheaten or not the #setLore updates the lore.",
                () -> assertNotNull(result.getLore()),
                () -> assertNotEquals(new ArrayList<>(), result.getLore()),
                () -> assertNotEquals(Collections.singletonList("TEst"), result.getLore()),
                () -> assertEquals(Collections.singletonList("Test"), result.getLore())
        );
    }

    @Test
    public void testToItemStack() {
        ItemStack result = itemUtil.toItemStack();
        assertAll("Check wheaten or not the #toItemStack results in a correct ItemStack.",
                () -> assertNotNull(result),
                () -> assertNotEquals(new ItemUtil(Material.AIR).toItemStack(), result),
                () -> assertEquals(new ItemUtil(Material.STONE).toItemStack(), result)
        );
    }

}