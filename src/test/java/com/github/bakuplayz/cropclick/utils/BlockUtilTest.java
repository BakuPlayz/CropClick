package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public class BlockUtilTest {

    private static BlockMock block;

    @BeforeAll
    public static void setUp() {
        BlockUtilTest.block = new BlockMock();
    }


    @Test
    public void testIsAir() {
        assertAll("Checks wheaten or not the block is air.",
                () -> assertTrue(BlockUtil.isAir(BlockUtilTest.block)),
                () -> {
                    BlockUtilTest.block.setType(Material.AIR);
                    assertTrue(BlockUtil.isAir(BlockUtilTest.block));
                }
        );
    }


    @Test
    public void testIsPlantableSurface() {
        assertAll("Checks wheaten or not the block is plantable.",
                () -> assertFalse(BlockUtil.isPlantableSurface(BlockUtilTest.block)),
                () -> {
                    BlockUtilTest.block.setType(Material.GRASS);
                    assertFalse(BlockUtil.isPlantableSurface(BlockUtilTest.block));
                },
                () -> {
                    BlockUtilTest.block.setType(Material.SAND);
                    assertTrue(BlockUtil.isPlantableSurface(BlockUtilTest.block));
                },
                () -> {
                    BlockUtilTest.block.setType(Material.SOIL);
                    assertTrue(BlockUtil.isPlantableSurface(BlockUtilTest.block));
                }
        );
    }


    @Test
    public void testIsSameType() {
        BlockMock block2 = new BlockMock();
        BlockUtilTest.block.setType(Material.STONE);
        assertAll("Checks if the types of two blocks are the same.",
                () -> {
                    BlockMock b = null;
                    assertThrows(IllegalArgumentException.class, () -> BlockUtil.isSameType(BlockUtilTest.block, b));
                    assertThrows(IllegalArgumentException.class, () -> BlockUtil.isSameType(b, BlockUtilTest.block));
                },
                () -> {
                    block2.setType(Material.GRASS);
                    assertFalse(BlockUtil.isSameType(BlockUtilTest.block, block2));
                },
                () -> {
                    block2.setType(Material.STONE);
                    assertTrue(BlockUtil.isSameType(BlockUtilTest.block, block2));
                }
        );
    }


    @Test
    public void testIsSameType2() {
        BlockUtilTest.block.setType(Material.STONE);
        assertAll("Checks if the block's type matches the other type.",
                () -> assertThrows(IllegalArgumentException.class, () -> BlockUtil.isSameType(BlockUtilTest.block, (Material) null)),
                () -> assertFalse(BlockUtil.isSameType(BlockUtilTest.block, Material.AIR)),
                () -> assertTrue(BlockUtil.isSameType(BlockUtilTest.block, Material.STONE))
        );
    }


    @Test
    public void testIsAnyType() {
        BlockUtilTest.block.setType(Material.STONE);
        assertAll("Checks if the block's type matches any of the other types.",
                () -> assertThrows(IllegalArgumentException.class, () -> BlockUtil.isAnyType(BlockUtilTest.block, null)),
                () -> assertFalse(BlockUtil.isAnyType(BlockUtilTest.block, Material.AIR)),
                () -> assertTrue(BlockUtil.isAnyType(BlockUtilTest.block, Material.STONE)),
                () -> assertTrue(BlockUtil.isAnyType(BlockUtilTest.block, Material.AIR, Material.SAND, Material.STONE))
        );
    }

}