package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.github.bakuplayz.cropclick.CropClick;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageUtilTest {

    private CropClick plugin;

    @BeforeAll
    public void beforeAll() {
        MockBukkit.mock();
        plugin = MockBukkit.load(CropClick.class);
    }

    @After
    public void tearDown() {
        MockBukkit.unload();
    }

    @Test
    public void testColorize() {
        String result = MessageUtil.colorize("&6Message");
        assertAll("Checks if the colorization is applied correctly.",
                () -> assertNotNull(result),
                () -> assertEquals("§6Message", result)
        );
    }

    @Test
    public void testBeautify() {
        String messageUnderscore = MessageUtil.beautify("message_with_underscore", true);
        String messageCaps = MessageUtil.beautify("MessageWithCaps", false);
        assertAll("Checks if the message is beautified correctly.",
                () -> assertNotNull(messageUnderscore),
                () -> assertEquals("Message With Underscore", messageUnderscore),
                () -> assertNotNull(messageCaps),
                () -> assertEquals("Message With Caps", messageCaps)
        );
    }

    @Test
    public void testReadify() {
        List<String> result = MessageUtil.readify("&6Message with two words per line.", 2);
        List<String> expectedResult = Arrays.asList("&6Message with", "&6two words", "&6per line.");
        assertAll("Checks if the message is 'readified' with two lines correctly.",
                () -> assertNotNull(result),
                () -> assertEquals(expectedResult, result)
        );
    }

    @Test
    public void testGetEnabledStatus() {
        String enabledResult = MessageUtil.getEnabledStatus(plugin, true);
        String disabledResult = MessageUtil.getEnabledStatus(plugin, false);
        assertAll("Checks if it gets the correct enabled statuses.",
                () -> assertNotNull(enabledResult),
                () -> assertEquals("Enabled", enabledResult),
                () -> assertNotNull(disabledResult),
                () -> assertEquals("Disabled", disabledResult)
        );
    }

}