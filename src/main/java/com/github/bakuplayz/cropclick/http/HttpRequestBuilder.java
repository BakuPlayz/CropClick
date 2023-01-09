/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;


/**
 * A class for handling <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods">HTTP Requests</a>.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class HttpRequestBuilder {

    private final @Getter String url;

    private @Getter String params;
    private @Getter HttpStatus status;
    private @Getter HttpURLConnection connection;
    private @Getter HashMap<String, String> headers;


    public HttpRequestBuilder(@NotNull String url) {
        this.headers = new HashMap<>();
        this.params = "";
        this.url = url;
    }


    /**
     * Sets the default headers of a request.
     *
     * @return the {@link HttpRequestBuilder HttpRequestBuilder instance}.
     */
    public HttpRequestBuilder setDefaultHeaders() {
        headers.put("User-Agent", "Mozilla/5.0");
        return this;
    }


    /**
     * Sets the provided parameters to the request.
     *
     * @param param the params to set.
     *
     * @return the {@link HttpRequestBuilder HttpRequestBuilder instance}.
     */
    public HttpRequestBuilder setParams(@NotNull HttpParam... param) {
        this.params = Arrays.stream(param)
                            .map(HttpParam::toString)
                            .collect(Collectors.joining("&"));
        return this;
    }


    /**
     * Sets the provided headers to the request.
     *
     * @param headers    the headers to set.
     * @param addDefault true if it should add the default headers.
     *
     * @return the {@link HttpRequestBuilder HttpRequestBuilder instance}.
     */
    @SuppressWarnings("unused")
    public HttpRequestBuilder setHeaders(@NotNull HashMap<String, String> headers, boolean addDefault) {
        this.headers = headers;
        if (addDefault) {
            setDefaultHeaders();
        }
        return this;
    }


    /**
     * Makes a <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/GET">GET request</a>.
     *
     * @param doOutput makes the request provide a response.
     *
     * @return the {@link HttpRequestBuilder HttpRequestBuilder instance}.
     */
    public HttpRequestBuilder get(boolean doOutput)
            throws IOException {
        return doRequest("GET", doOutput);
    }


    /**
     * Makes a <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/POST">POST request</a>.
     *
     * @param doOutput makes the request provide a response.
     *
     * @return the {@link HttpRequestBuilder HttpRequestBuilder instance}.
     */
    public HttpRequestBuilder post(boolean doOutput)
            throws IOException {
        return doRequest("POST", doOutput);
    }


    /**
     * Makes a <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/UPDATE">UPDATE request</a>.
     *
     * @param doOutput makes the request provide a response.
     *
     * @return the {@link HttpRequestBuilder HttpRequestBuilder instance}.
     */
    public HttpRequestBuilder update(boolean doOutput)
            throws IOException {
        return doRequest("UPDATE", doOutput);
    }


    /**
     * Makes a <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/DELETE">DELETE request</a>.
     *
     * @param doOutput makes the request provide a response.
     *
     * @return the {@link HttpRequestBuilder HttpRequestBuilder instance}.
     */
    @SuppressWarnings("unused")
    public HttpRequestBuilder delete(boolean doOutput)
            throws IOException {
        return doRequest("DELETE", doOutput);
    }


    /**
     * Makes an <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods">HTTP request</a> with the given method.
     *
     * @param method   the http method to request with.
     * @param doOutput makes the request provide a response.
     *
     * @return the {@link HttpRequestBuilder HttpRequestBuilder instance}.
     */
    private HttpRequestBuilder doRequest(String method, boolean doOutput)
            throws IOException {
        connection = (HttpURLConnection) new URL(
                !params.equals("")
                ? url + "?" + params
                : url
        ).openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(doOutput);

        updateStatus();

        return this;
    }


    /**
     * Updates the {@link #status} to the suitable based on the response code.
     */
    private void updateStatus()
            throws IOException {
        switch (connection.getResponseCode()) {
            case 200:
                status = HttpStatus.OK;
                break;

            case 304:
                status = HttpStatus.UNCHANGED;
                break;

            default:
                status = HttpStatus.NOT_FOUND;
                break;

        }
    }


    /**
     * Gets the response of the request.
     *
     * @return the request response, otherwise JsonNull.
     */
    public JsonElement getResponse()
            throws IOException {
        if (status == HttpStatus.UNCHANGED) {
            return JsonNull.INSTANCE;
        }

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        JsonElement body = new JsonParser().parse(reader);
        reader.close();
        return body;
    }

}