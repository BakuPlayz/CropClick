package com.github.bakuplayz.cropclick.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class ParamTests {

    private Param param;


    @Before
    public void setUp() {
        param = new Param("test", "value");
    }


    @Test
    public void testConstructor() {
        assertAll("Should contain, equal and mach the values and equal instances.",
                () -> assertEquals(new Param("test", "value"), param),
                () -> assertEquals(param.getKey(), "test"),
                () -> assertEquals(param.getValue(), "value")
        );
    }


    @Test
    public void testToString() {
        assertAll("Should return param in a http encoded format.",
                () -> assertEquals(param.getKey(), "test"),
                () -> assertEquals(param.getValue(), "value"),
                () -> assertEquals(param.toString(), "test=value")
        );
    }

}