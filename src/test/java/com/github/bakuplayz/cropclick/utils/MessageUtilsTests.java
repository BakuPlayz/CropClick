package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.github.bakuplayz.cropclick.CropClick;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class MessageUtilsTests {

    private CropClick plugin;


    @BeforeAll
    public void beforeAll() {
        MockBukkit.mock();
        plugin = MockBukkit.load(CropClick.class);
    }


    @AfterAll
    public void tearDown() {
        MockBukkit.unload();
    }


    @Test
    public void testColorize() {
        String result = MessageUtils.colorize("&6Message");

        assertAll("Checks if the colorization is applied correctly.",
                () -> assertNotNull(result),
                () -> assertEquals("§6Message", result)
        );
    }


    @Test
    public void testBeautify() {
        String messageUnderscore = MessageUtils.beautify("message_with_underscore", true);
        String messageCaps = MessageUtils.beautify("MessageWithCaps", false);

        assertAll("Checks if the message is beautified correctly.",
                () -> assertNotNull(messageUnderscore),
                () -> assertEquals("Message With Underscore", messageUnderscore),
                () -> assertNotNull(messageCaps),
                () -> assertEquals("Message With Caps", messageCaps)
        );
    }


    @Test
    public void testReadify() {
        List<String> twoWordEven = MessageUtils.readify("&6Message with two words per line.", 2);
        List<String> twoWordOdd = MessageUtils.readify("&6Message with two words line.", 2);
        List<String> emptyTwoWord = MessageUtils.readify("", 2);
        List<String> evenResult = Arrays.asList("&6Message with", "&6two words", "&6per line.");
        List<String> oddResult = Arrays.asList("&6Message with", "&6two words", "&6line.");
        List<String> emptyResult = Collections.singletonList("");

        assertAll("Checks if the message is 'readified' with two lines correctly.",
                () -> assertNotNull(twoWordEven),
                () -> assertNotNull(twoWordOdd),
                () -> assertNotNull(emptyTwoWord),
                () -> assertEquals(evenResult, twoWordEven),
                () -> assertEquals(oddResult, twoWordOdd),
                () -> assertEquals(emptyResult, emptyTwoWord)
        );
    }


    @Test
    public void testGetEnabledStatus() {
        String enabledResult = MessageUtils.getEnabledStatus(plugin, true);
        String disabledResult = MessageUtils.getEnabledStatus(plugin, false);

        assertAll("Checks if it gets the correct enabled statuses.",
                () -> assertNotNull(enabledResult),
                () -> assertEquals("Enabled", enabledResult),
                () -> assertNotNull(disabledResult),
                () -> assertEquals("Disabled", disabledResult)
        );
    }

}