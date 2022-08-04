package com.github.bakuplayz.cropclick.utils;

import com.google.gson.JsonElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class RequestUtilTests {

    // USING: https://mockapi.io/projects/62e8472293938a545be3b2ad

    private RequestUtil requestUtil;


    @BeforeAll
    public void setUp()
            throws IOException {
        requestUtil = new RequestUtil("https://62e8472293938a545be3b2ac.mockapi.io/api/v1/test/updates");
    }


    @Test
    public void testSetDefaultHeaders() {
        RequestUtil result = requestUtil.setHeaders(new HashMap<>(), true);

        assertAll("Checks if default headers are set correctly.",
                () -> assertNotNull(result.getHeaders()),
                () -> assertEquals(result.getHeaders(),
                        new HashMap<String, String>() {
                            {
                                put("User-Agent", "Mozilla/5.0");
                            }
                        }
                )
        );
    }


    @Test
    public void testSetParams() {
        RequestUtil result = requestUtil.setParams(
                new Param("key", "value")
        );

        assertAll("Checks if params are set correctly.",
                () -> assertNotNull(result.getParams()),
                () -> assertEquals(result.getParams(), "key=value"),
                () -> {
                    result.setParams(
                            new Param("key1", "value1"),
                            new Param("key2", "value2")
                    );
                    assertEquals(result.getParams(), "key1=value1&key2=value2");
                }
        );
    }


    @Test
    public void testSetHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>() {{
            put("Test", "Value");
        }};
        HashMap<String, String> baseHeaders = new HashMap<>(headers);
        RequestUtil result = requestUtil.setHeaders(headers, true);

        assertAll("Checks if headers are set correctly",
                () -> assertNotNull(result.getHeaders()),
                () -> assertNotEquals(baseHeaders, result.getHeaders()),
                () -> assertEquals(headers, result.getHeaders())
        );
    }


    @Test
    public void testConnection() {
        HttpURLConnection result = requestUtil.getClient();

        assertAll("Checks if the client is open.",
                () -> assertNotNull(requestUtil),
                () -> assertNotNull(result)
        );
    }


    @Test
    public void testPost()
            throws Exception {
        RequestUtil result = requestUtil
                .setDefaultHeaders()
                .setParams(new Param("key", "value"))
                .post(true);

        assertAll("Checks if the post function works.",
                () -> assertNotNull(requestUtil),
                () -> assertNotNull(result)
        );
    }


    @Test
    public void testGetResponse()
            throws Exception {
        JsonElement result = requestUtil.getResponse();
        
        assertAll("Checks if getting the response works.",
                () -> assertNotNull(requestUtil),
                () -> assertNotNull(result)
        );
    }

}