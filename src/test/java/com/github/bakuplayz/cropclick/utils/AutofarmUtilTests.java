package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crop.CropManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public final class AutofarmUtilTests {

    private ServerMock server;

    private CropClick plugin;
    private CropManager cropManager;


    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(CropClick.class);
        cropManager = plugin.getCropManager();
    }


    @After
    public void tearDown() {
        MockBukkit.unload();
    }


    @Test
    public void testIsContainer() {
        /*BlockMock block = new BlockMock();
        block.setType(Material.CHEST);

        assertAll("",
                () -> assertNotNull(block),
                () -> assertNotNull(plugin),
                () -> block.assertType(Material.CHEST),
                () -> AutofarmUtil.isContainer(block)
        );*/
    }


    @Test
    public void testGetContainer() {
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