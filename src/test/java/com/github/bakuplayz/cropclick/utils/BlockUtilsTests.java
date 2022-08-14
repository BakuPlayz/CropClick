package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class BlockUtilsTests {

    private BlockMock block;


    @BeforeAll
    public void initialize() {
        MockBukkit.mock();
    }


    @BeforeEach
    public void setUp() {
        block = new BlockMock();
        block.setType(Material.STONE);
    }


    @AfterAll
    public void tearDown() {
        MockBukkit.unmock();
    }


    @Test
    public void testIsAir() {
        assertAll("Checks wheaten or not the block is air.",
                () -> assertTrue(BlockUtils.isAir(null)),
                () -> {
                    block.setType(Material.AIR);
                    assertTrue(BlockUtils.isAir(block));
                }
        );
    }


    @Test
    public void testIsPlantableSurface() {
        assertAll("Checks wheaten or not the block is plantable.",
                () -> assertFalse(BlockUtils.isPlantableSurface(block)),
                () -> {
                    block.setType(Material.GRASS);
                    assertFalse(BlockUtils.isPlantableSurface(block));
                },
                () -> {
                    block.setType(Material.SAND);
                    assertTrue(BlockUtils.isPlantableSurface(block));
                },
                () -> {
                    block.setType(Material.FARMLAND);
                    assertTrue(BlockUtils.isPlantableSurface(block));
                }
        );
    }


    @Test
    @SuppressWarnings("ConstantConditions")
    public void testIsSameType() {
        BlockMock block2 = new BlockMock();

        assertAll("Checks if the types of two blocks are the same.",
                () -> {
                    BlockMock b = null;
                    assertThrows(Exception.class, () -> BlockUtils.isSameType(block, b));
                    assertThrows(Exception.class, () -> BlockUtils.isSameType(b, block));
                },
                () -> {
                    block2.setType(Material.GRASS);
                    assertFalse(BlockUtils.isSameType(block, block2));
                },
                () -> {
                    block2.setType(Material.STONE);
                    assertTrue(BlockUtils.isSameType(block, block2));
                }
        );
    }


    @Test
    public void testIsSameType2() {
        assertAll("Checks if the block's type matches the other type.",
                () -> assertFalse(BlockUtils.isSameType(block, Material.AIR)),
                () -> assertTrue(BlockUtils.isSameType(block, Material.STONE))
        );
    }


    @Test
    @SuppressWarnings({"ConstantConditions", "ConfusingArgumentToVarargsMethod"})
    public void testIsAnyType() {
        assertAll("Checks if the block's type matches any of the other types.",
                () -> assertThrows(Exception.class, () -> BlockUtils.isAnyType(block, (Material[]) null)),
                () -> assertThrows(Exception.class, () -> BlockUtils.isAnyType(block, null)),
                () -> assertFalse(BlockUtils.isAnyType(block, Material.AIR)),
                () -> assertTrue(BlockUtils.isAnyType(block, Material.STONE)),
                () -> assertTrue(BlockUtils.isAnyType(block, Material.AIR, Material.SAND, Material.STONE))
        );
    }

}