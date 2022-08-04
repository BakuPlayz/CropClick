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
 * @version 1.6.0
 * @since 1.6.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class AutofarmUtilsTests {

    private BlockMock block;


    @BeforeAll
    public void initialize() {
        MockBukkit.mock();
    }


    @BeforeEach
    public void setUp() {
        block = new BlockMock();
    }


    @AfterAll
    public void tearDown() {
        MockBukkit.unload();
    }


    @Test
    public void testIsContainer() {
        block.setType(Material.CHEST);

        assertAll("Checks if chests are recognized as containers.",
                () -> assertNotNull(block),
                () -> assertEquals(Material.CHEST, block.getType()),
                () -> assertTrue(AutofarmUtils.isContainer(block))
        );
    }


    @Test
    public void testIsContainer1() {
        block.setType(Material.BLACK_SHULKER_BOX);

        assertAll("Checks if shulkers are recognized as containers.",
                () -> assertNotNull(block),
                () -> assertEquals(Material.BLACK_SHULKER_BOX, block.getType()),
                () -> assertTrue(AutofarmUtils.isContainer(block))
        );
    }


    //TODO: Figure out how to make a double chest.
    @Test
    public void testIsContainer2() {
        block.setType(Material.CHEST);

        assertAll("Checks if double chests are recognized as containers.",
                () -> assertNotNull(block),
                () -> assertEquals(Material.CHEST, block.getType())
                // () -> assertTrue(AutofarmUtils.isContainer(block, true))
        );
    }


    @Test
    public void testGetContainer() {
        // Cannot run due to bad mock!
    }


    @Test
    public void testGetContainer1() {
        // Cannot run due to bad mock!
    }


    @Test
    public void testGetContainer2() {
        // Cannot run due to bad mock!
    }


    @Test
    public void testIsDispenser() {
        // Cannot run due to bad mock!
    }


    @Test
    public void testGetDispenser() {
        // Cannot run due to bad mock!
    }


    @Test
    public void testIsCrop() {
        // Cannot run due to bad mock!
    }


    @Test
    public void testGetCrop() {
        // Cannot run due to bad mock!
    }

}