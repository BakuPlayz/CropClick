package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class VersionUtilsTests {

    @BeforeAll
    public void setUp() {
        MockBukkit.mock();
    }


    @AfterAll
    public void tearDown() {
        MockBukkit.unmock();
    }


    @Test
    public void getServerVersion() {
        String result = VersionUtils.getServerVersion();

        assertAll("Should return a formatted and double parsable version number.",
                () -> assertNotNull(result),
                () -> assertEquals(result, "12.1")
        );
    }


    @Test
    public void between() {
        assertAll("Should return true, when the version is in the interval or equal to sent version.",
                () -> assertFalse(VersionUtils.between(0.0, 12.0)),
                () -> assertTrue(VersionUtils.between(12.0, 12.9)),
                () -> assertTrue(VersionUtils.between(12.0, 12.1))
        );
    }


    @Test
    public void supportsBeetroots() {
        assertAll("Should return true, due to 1.12 supporting beetroots.", VersionUtils::supportsBeetroots);
    }


    @Test
    public void supportsShulkers() {
        assertAll("Should return true, due to 1.12 supporting shulkers.", VersionUtils::supportsShulkers);
    }

}