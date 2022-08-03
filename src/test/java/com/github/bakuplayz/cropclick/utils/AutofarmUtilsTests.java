package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.CropManager;
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

    private ServerMock server;

    private CropClick plugin;
    private CropManager cropManager;

    private BlockMock block;


    @BeforeAll
    public void initialize() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(CropClick.class);
    }


    @BeforeEach
    public void setUp() {
        block = new BlockMock();
        //cropManager = plugin.getCropManager();
    }


    @AfterAll
    public void tearDown() {
        MockBukkit.unload();
    }


    @Test
    public void testIsContainer() {
        block.setType(Material.CHEST);

        assertAll("",
                () -> assertNotNull(block),
                () -> assertEquals(Material.CHEST, block.getType()),
                () -> assertTrue(AutofarmUtils.isContainer(block))
        );
    }


    @Test
    public void testIsContainer1() {
        block.setType(Material.BLACK_SHULKER_BOX);

        assertAll("",
                () -> assertNotNull(block),
                () -> assertEquals(Material.BLACK_SHULKER_BOX, block.getType()),
                () -> assertTrue(AutofarmUtils.isContainer(block))
        );
    }


    //TODO: Figure out how to make a double chest.
    @Test
    public void testIsContainer2() {
        block.setType(Material.CHEST);

        assertAll("",
                () -> assertNotNull(block),
                () -> assertEquals(Material.CHEST, block.getType())
                // () -> assertTrue(AutofarmUtils.isContainer(block, true))
        );
    }


    @Test
    public void testGetContainer() {
        // Cannot run due to bad block mock!
    }


    @Test
    public void testGetContainer1() {
        // Cannot run due to bad block mock!
    }


    @Test
    public void testGetContainer2() {
        // Cannot run due to bad block mock!
    }


    @Test
    public void testIsDispenser() {
    }


    @Test
    public void testGetDispenser() {
    }


    @Test
    public void testIsCrop() {
    }


    @Test
    public void testGetCrop() {
    }

}