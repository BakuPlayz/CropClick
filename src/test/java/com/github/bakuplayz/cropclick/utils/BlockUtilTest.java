package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class BlockUtilTest {

    private BlockMock block;

    @BeforeAll
    public void setUp() {
        block = new BlockMock();
    }


    @Test
    public void testIsAir() {
        assertAll("Checks wheaten or not the block is air.",
                () -> assertTrue(BlockUtil.isAir(block)),
                () -> {
                    block.setType(Material.AIR);
                    assertTrue(BlockUtil.isAir(block));
                }
        );
    }


    @Test
    public void testIsPlantableSurface() {
        assertAll("Checks wheaten or not the block is plantable.",
                () -> assertFalse(BlockUtil.isPlantableSurface(block)),
                () -> {
                    block.setType(Material.GRASS);
                    assertFalse(BlockUtil.isPlantableSurface(block));
                },
                () -> {
                    block.setType(Material.SAND);
                    assertTrue(BlockUtil.isPlantableSurface(block));
                },
                () -> {
                    block.setType(Material.SOIL);
                    assertTrue(BlockUtil.isPlantableSurface(block));
                }
        );
    }


    @Test
    public void testIsSameType() {
        BlockMock block2 = new BlockMock();
        block.setType(Material.STONE);
        assertAll("Checks if the types of two blocks are the same.",
                () -> {
                    BlockMock b = null;
                    assertThrows(Exception.class, () -> BlockUtil.isSameType(block, b));
                    assertThrows(Exception.class, () -> BlockUtil.isSameType(b, block));
                },
                () -> {
                    block2.setType(Material.GRASS);
                    assertFalse(BlockUtil.isSameType(block, block2));
                },
                () -> {
                    block2.setType(Material.STONE);
                    assertTrue(BlockUtil.isSameType(block, block2));
                }
        );
    }


    @Test
    public void testIsSameType2() {
        block.setType(Material.STONE);
        assertAll("Checks if the block's type matches the other type.",
                () -> {
                    Material m = null;
                    assertThrows(Exception.class, () -> BlockUtil.isSameType(block, m));
                },
                () -> assertFalse(BlockUtil.isSameType(block, Material.AIR)),
                () -> assertTrue(BlockUtil.isSameType(block, Material.STONE))
        );
    }


    @Test
    public void testIsAnyType() {
        block.setType(Material.STONE);
        assertAll("Checks if the block's type matches any of the other types.",
                () -> assertThrows(Exception.class, () -> BlockUtil.isAnyType(block, (Material[]) null)),
                () -> assertThrows(Exception.class, () -> BlockUtil.isAnyType(block, null)),
                () -> assertFalse(BlockUtil.isAnyType(block, Material.AIR)),
                () -> assertTrue(BlockUtil.isAnyType(block, Material.STONE)),
                () -> assertTrue(BlockUtil.isAnyType(block, Material.AIR, Material.SAND, Material.STONE))
        );
    }

}