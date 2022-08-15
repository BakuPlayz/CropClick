package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.github.bakuplayz.cropclick.CropClick;
import org.bukkit.plugin.PluginManager;
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
public final class PermissionUtilsTests {

    private ServerMock server;

    private PlayerMock player;


    @BeforeAll
    public void initialize() {
        server = MockBukkit.mock();

        CropClick plugin = MockBukkit.load(CropClick.class);
        PluginManager pluginManager = server.getPluginManager();
        plugin.getDescription().getPermissions().forEach(pluginManager::addPermission);
    }


    @BeforeEach
    public void setUp() {
        player = server.addPlayer();
        player.setOp(true);
    }


    @AfterAll
    public void tearDown() {
        MockBukkit.unmock();
    }


    @Test
    public void testCanLink() {
        boolean result = PermissionUtils.canLinkFarm(player);

        assertAll("Checks if an OP player can link.",
                () -> assertNotNull(player),
                () -> assertTrue(player.isOp()),
                () -> assertTrue(result)
        );
    }


    @Test
    public void testCanUnlink() {
        boolean result = PermissionUtils.canUnlinkFarm(player);

        assertAll("Checks if an OP player can unlink.",
                () -> assertNotNull(player),
                () -> assertTrue(player.isOp()),
                () -> assertTrue(result)
        );
    }


    @Test
    public void testCanInteractAtFarm() {
        boolean result = PermissionUtils.canInteractAtFarm(player);

        assertAll("Checks if an OP player can interact at a farm.",
                () -> assertNotNull(player),
                () -> assertTrue(player.isOp()),
                () -> assertTrue(result)
        );
    }


    @Test
    public void testCanDestroyCrop() {
        boolean result = PermissionUtils.canDestroyCrop(player, "wheat");

        assertAll("Checks if an OP player can destroy a wheat crop.",
                () -> assertNotNull(player),
                () -> assertTrue(player.isOp()),
                () -> assertTrue(result)
        );
    }


    @Test
    public void testCanPlantCrop() {
        boolean result = PermissionUtils.canPlantCrop(player, "wheat");

        assertAll("Checks if an OP player can plant a wheat crop.",
                () -> assertNotNull(player),
                () -> assertTrue(player.isOp()),
                () -> assertTrue(result)
        );
    }


    @Test
    public void testCanHarvestCrop() {
        boolean result = PermissionUtils.canHarvestCrop(player, "wheat");

        assertAll("Checks if an OP player can harvest a wheat crop.",
                () -> assertNotNull(player),
                () -> assertTrue(player.isOp()),
                () -> assertTrue(result)
        );
    }

}