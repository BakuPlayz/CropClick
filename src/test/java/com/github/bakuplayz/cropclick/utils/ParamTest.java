package com.github.bakuplayz.cropclick.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class ParamTest {

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
                () -> assertEquals(param.key, "test"),
                () -> assertEquals(param.value, "value"),
                () -> assertEquals(param.toString(), "test=value")
        );
    }

}