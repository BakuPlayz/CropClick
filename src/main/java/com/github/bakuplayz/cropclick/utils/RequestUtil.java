package com.github.bakuplayz.cropclick.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import lombok.Getter;
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
 * @version 2.0.0
 * @since 2.0.0
 */
public final class RequestUtil {

    private @Getter String params;
    private @Getter HashMap<String, String> headers;
    private final @Getter HttpURLConnection connection;

    private @Getter boolean isDataChanged;


    public RequestUtil(@NotNull String URL)
            throws IOException {
        this.connection = (HttpURLConnection) new URL(URL).openConnection();
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
     * @param addDefault Adds the default request headers.
     *
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
     *
     * @return The RequestUtil object
     */
    public RequestUtil post(boolean doOutput)
            throws Exception {
        connection.setRequestMethod("POST");
        connection.setDoOutput(doOutput);

        if (!params.equals("")) {
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(params);
            dos.flush();
        }

        this.isDataChanged = connection.getResponseCode() != 304;

        return this;
    }


    /**
     * If the data has changed, return null. Otherwise, read the response from the server and return it.
     *
     * @return A JsonElement object.
     */
    public JsonElement getResponse()
            throws IOException {
        if (!isDataChanged) {
            return JsonNull.INSTANCE;
        }

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        JsonElement body = JsonParser.parseReader(reader);
        reader.close();
        return body;
    }

}