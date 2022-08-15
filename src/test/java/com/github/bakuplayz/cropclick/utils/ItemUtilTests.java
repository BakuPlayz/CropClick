package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class ItemUtilTests {

    private ItemUtil itemUtil;

    private CropClick plugin;


    @BeforeAll
    public void beforeAll() {
        MockBukkit.mock();
    }


    @BeforeEach
    public void setUp() {
        plugin = MockBukkit.load(CropClick.class);
        itemUtil = new ItemUtil(Material.STONE);
    }


    @AfterAll
    public void tearDown() {
        MockBukkit.unmock();
    }


    @Test
    public void testConstructor() {
        ItemUtil result = new ItemUtil(Material.STONE);

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals("", result.getName()),
                () -> assertEquals(1, result.getAmount()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor1() {
        ItemUtil result = new ItemUtil(
                Material.STONE,
                2
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals("", result.getName()),
                () -> assertEquals(2, result.getAmount()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor2() {
        ItemUtil result = new ItemUtil(
                Material.STONE,
                (short) 2
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals("", result.getName()),
                () -> assertEquals(2, result.getDamage()),
                () -> assertEquals(1, result.getAmount()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor3() {
        ItemUtil result = new ItemUtil(
                Material.STONE,
                "Arkham"
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals("Arkham", result.getName()),
                () -> assertEquals(1, result.getAmount()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor4() {
        ItemUtil result = new ItemUtil(
                Material.STONE,
                "Arkham",
                2
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals("Arkham", result.getName()),
                () -> assertEquals(2, result.getAmount()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor5() {
        ItemUtil result = new ItemUtil(
                Material.STONE,
                "Arkham",
                2,
                (short) 2
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals("Arkham", result.getName()),
                () -> assertEquals(2, result.getAmount()),
                () -> assertEquals(2, result.getDamage()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor6() {
        ItemUtil result = new ItemUtil(Material.STONE,
                "Arkham",
                2,
                Collections.singletonList("City")
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertNotNull(result.getLore()),
                () -> assertEquals(2, result.getAmount()),
                () -> assertEquals("Arkham", result.getName()),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals(Collections.singletonList("City"), result.getLore())
        );
    }


    @Test
    @SuppressWarnings("deprecation")
    public void testConstructor7() {
        ItemUtil result = new ItemUtil(5);

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertNull(result.getMeta()),
                () -> assertEquals("", result.getName()),
                () -> assertEquals(1, result.getAmount()),
                () -> assertEquals(Material.WOOD, result.getMaterial()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor8() {
        ItemUtil result = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2)
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertNull(result.getName()),
                () -> assertEquals(2, result.getAmount()),
                () -> assertEquals(2, result.getDamage()),
                () -> assertEquals(new ItemMetaMock(), result.getMeta()),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor9() {
        ItemUtil result = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2),
                10
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertNull(result.getName()),
                () -> assertEquals(2, result.getDamage()),
                () -> assertEquals(10, result.getAmount()),
                () -> assertEquals(new ItemMetaMock(), result.getMeta()),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor10() {
        ItemUtil result = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2),
                "Test"
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals(2, result.getDamage()),
                () -> assertEquals(2, result.getAmount()),
                () -> assertEquals("Test", result.getName()),
                () -> assertEquals(new ItemMetaMock(), result.getMeta()),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor11() {
        ItemUtil result = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2),
                "Test",
                10
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals(2, result.getDamage()),
                () -> assertEquals(10, result.getAmount()),
                () -> assertEquals("Test", result.getName()),
                () -> assertEquals(new ItemMetaMock(), result.getMeta()),
                () -> assertEquals(Material.STONE, result.getMaterial()),
                () -> assertEquals(Collections.emptyList(), result.getLore())
        );
    }


    @Test
    public void testConstructor12() {
        ItemUtil result = new ItemUtil(
                new ItemStack(Material.STONE, 2, (short) 2),
                "Test",
                10,
                Collections.singletonList("Arkham")
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals(2, result.getDamage()),
                () -> assertEquals(10, result.getAmount()),
                () -> assertEquals("Test", result.getName()),
                () -> assertEquals(new ItemMetaMock(), result.getMeta()),
                () -> assertEquals(Collections.singletonList("Arkham"), result.getLore()),
                () -> assertEquals(Material.STONE, result.getMaterial())
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
    @SuppressWarnings("deprecation")
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
        ItemUtil result = itemUtil.setName(plugin, LanguageAPI.Menu.GENERAL_BACK_ITEM_NAME);

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
                () -> assertNotEquals(Collections.emptyList(), result.getLore()),
                () -> assertNotEquals(Collections.singletonList("TEst"), result.getLore()),
                () -> assertEquals(Collections.singletonList("Test"), result.getLore())
        );
    }


    @Test
    public void testSetLore2() {
        ItemUtil result = itemUtil.setLore("Test");

        assertAll("Check wheaten or not the #setLore updates the lore.",
                () -> assertNotNull(result.getLore()),
                () -> assertNotEquals(Collections.emptyList(), result.getLore()),
                () -> assertNotEquals(Collections.singletonList("TEst"), result.getLore()),
                () -> assertEquals(Collections.singletonList("Test"), result.getLore())
        );
    }


    @Test
    public void testToItemStack() {
        ItemStack result = itemUtil.toItemStack();
        ItemStack airStack = new ItemUtil(Material.AIR).toItemStack();
        ItemStack stoneStack = new ItemUtil(Material.STONE).toItemStack();

        assertAll("Check wheaten or not the #toItemStack results in a correct ItemStack.",
                () -> assertNotNull(result),
                () -> assertNotEquals(airStack, result),
                () -> assertEquals(stoneStack, result)
        );
    }

}