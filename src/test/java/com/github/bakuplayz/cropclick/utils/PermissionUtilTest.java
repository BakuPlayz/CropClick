package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.github.bakuplayz.cropclick.CropClick;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PermissionUtilTest {

    private PlayerMock player;
    private CropClick plugin;

    @Before
    public void setUp() {
        ServerMock server = MockBukkit.mock();
        plugin = MockBukkit.load(CropClick.class);
        player = server.addPlayer();
        player.setOp(true);
    }

    @After
    public void tearDown() {
        MockBukkit.unload();
    }

    @Test
    public void testCanLink() throws Exception {
        boolean result = PermissionUtil.canLink(player);
        System.out.println(result);
        Assert.assertTrue(result);
    }

    @Test
    public void testCanUnlink() throws Exception {
        boolean result = PermissionUtil.canUnlink(player);
        Assert.assertTrue(result);
    }

    @Test
    public void testCanUpdateLink() throws Exception {
        boolean result = PermissionUtil.canUpdateLink(player);
        Assert.assertTrue(result);
    }

    @Test
    public void testCanInteractAtFarm() throws Exception {
        boolean result = PermissionUtil.canInteractAtFarm(player);
        Assert.assertTrue(result);
    }

    @Test
    public void testCanDestroyCrop() throws Exception {
        boolean result = PermissionUtil.canDestroyCrop(player);
        Assert.assertTrue(result);
    }

    @Test
    public void testCanPlaceCrop() throws Exception {
        boolean result = PermissionUtil.canPlaceCrop(player);
        Assert.assertTrue(result);
    }

    @Test
    public void testCanHarvestCrop() throws Exception {
        boolean result = PermissionUtil.canHarvestCrop(player, "wheat");
        Assert.assertTrue(result);
    }
}