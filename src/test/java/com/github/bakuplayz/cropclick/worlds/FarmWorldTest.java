package com.github.bakuplayz.cropclick.worlds;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class FarmWorldTest {

    private FarmWorld farmWorld;


    @Before
    public void initialize() {
        MockBukkit.mock();
    }


    @BeforeEach
    public void setUp() {
        farmWorld = new FarmWorld(
                "World",
                false,
                false,
                false,
                new ArrayList<>()
        );
    }


    @After
    public void tearDown() {
        MockBukkit.unload();
    }


    @Test
    public void testConstructor() {
        FarmWorld result = new FarmWorld(
                "World",
                false,
                true,
                true,
                new ArrayList<>()
        );

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals("World", result.getName()),
                () -> assertFalse(result.isBanished()),
                () -> assertTrue(result.allowsPlayers()),
                () -> assertTrue(result.allowsAutofarms())
        );
    }


    @Test
    public void testConstructor1() {
        WorldMock world = new WorldMock();
        FarmWorld result = new FarmWorld(world);

        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertNotNull(result),
                () -> assertEquals("World", result.getName()),
                () -> assertFalse(result.isBanished()),
                () -> assertTrue(result.allowsPlayers()),
                () -> assertTrue(result.allowsAutofarms())
        );
    }


    @Test
    public void testAllowsPlayersGetter() {
        assertAll("Checks wheaten the getter, allowsPlayers, gets the correct value.",
                () -> assertNotNull(farmWorld),
                () -> assertFalse(farmWorld.allowsPlayers())
        );
    }


    @Test
    public void testAllowsAutofarmsGetter() {
        assertAll("Checks wheaten the getter, allowsAutofarms, gets the correct value.",
                () -> assertNotNull(farmWorld),
                () -> assertFalse(farmWorld.allowsAutofarms())
        );
    }


    @Test
    public void testIsBanishedGetter() {
        assertAll("Checks wheaten the getter, isBanished, gets the correct value.",
                () -> assertNotNull(farmWorld),
                () -> assertFalse(farmWorld.isBanished())
        );
    }


    @Test
    public void testAllowsPlayersSetter() {
        farmWorld.allowsPlayers(true);

        assertAll("Checks wheaten the setter, allowsPlayers, sets the correct value.",
                () -> assertNotNull(farmWorld),
                () -> assertTrue(farmWorld.allowsPlayers())
        );
    }


    @Test
    public void testAllowsAutofarmsSetter() {
        farmWorld.allowsAutofarms(true);

        assertAll("Checks wheaten the setter, allowsAutofarms, sets the correct value.",
                () -> assertNotNull(farmWorld),
                () -> assertTrue(farmWorld.allowsAutofarms())
        );
    }


    @Test
    public void testIsBanishedSetter() {
        farmWorld.isBanished(true);

        assertAll("Checks wheaten the setter, isBanished, sets the correct value.",
                () -> assertNotNull(farmWorld),
                () -> assertTrue(farmWorld.isBanished())
        );
    }


    @Test
    public void testToString() {
        String result = farmWorld.toString();
        String toStringed = "FarmWorld(name=World, isBanished=false, allowsPlayers=false, allowsAutofarms=false, banishedAddons=[])";

        assertAll("Checks wheaten the toString formats the variables to a string correctly.",
                () -> assertNotNull(farmWorld),
                () -> assertNotNull(result),
                () -> assertEquals(result, toStringed)
        );
    }


    @Test
    public void testEquals() {
        FarmWorld equalWorld = new FarmWorld("World", false, false, false, new ArrayList<>());
        FarmWorld nonEqualWorld = new FarmWorld("Sword", true, true, true, new ArrayList<>());

        assertAll("Checks wheaten the equal uses equality correctly.",
                () -> assertNotNull(farmWorld),
                () -> assertNotNull(equalWorld), () -> assertNotNull(nonEqualWorld),
                () -> assertNotEquals(nonEqualWorld, farmWorld), () -> assertEquals(equalWorld, farmWorld)
        );
    }


    @Test
    public void testHashCode() {
        int result = farmWorld.hashCode();

        assertAll("Checks wheaten the hashCode uses a good hash algorithm.",
                () -> assertNotNull(farmWorld),
                () -> assertEquals(result, -1737165185)
        );
    }

}