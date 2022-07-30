package com.github.bakuplayz.cropclick.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class RequestUtil {

    private String params;
    private HashMap<String, String> headers;
    private final HttpURLConnection client;

    private boolean isDataChanged;

    public RequestUtil(@NotNull String url)
            throws IOException {
        this.client = (HttpURLConnection) new URL(url).openConnection();
        this.headers = new HashMap<>();
        this.params = "";
    }

    /**
     * This function sets the default headers for the request.
     *
     * @return The RequestUtil object.
     */
    public RequestUtil setDefaultHeaders() {
        headers.put("User-Agent", "Mozilla/5.0");
        return this;
    }

    /**
     * It takes a list of Param objects and converts them to a string.
     *
     * @return A RequestUtil object
     */
    public RequestUtil setParams(@NotNull Param... param) {
        this.params = Arrays.stream(param)
                .map(Param::toString)
                .collect(Collectors.joining("&"));
        return this;
    }

    /**
     * It sets the headers for the request.
     *
     * @param headers    The headers to be sent with the request.
     * @param addDefault If true, the default headers will be added to the headers you pass in.
     * @return The RequestUtil object
     */
    public RequestUtil setHeaders(@NotNull HashMap<String, String> headers, boolean addDefault) {
        this.headers = headers;
        if (addDefault) setDefaultHeaders();
        return this;
    }

    /**
     * Set the request method to POST, set the doOutput flag to the value of the doOutput parameter, and if the params
     * string is not empty, write the params string to the output stream.
     *
     * @param doOutput If true, the request will be sent to the server. If false, the request will not be sent to the
     *                 server.
     * @return The RequestUtil object
     */
    public RequestUtil post(boolean doOutput)
            throws Exception {
        client.setRequestMethod("POST");
        client.setDoOutput(doOutput);

        if (!params.equals("")) {
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeBytes(params);
            dos.flush();
        }

        this.isDataChanged = client.getResponseCode() != 304;

        return this;
    }

    /**
     * If the data has changed, return null. Otherwise, read the response from the server and return it.
     *
     * @return A JsonElement object.
     */
    public JsonElement getResponse() throws IOException {
        if (isDataChanged) return JsonNull.INSTANCE;

        InputStreamReader reader = new InputStreamReader(client.getInputStream());
        JsonElement body = JsonParser.parseReader(reader);
        reader.close();
        return body;
    }

}