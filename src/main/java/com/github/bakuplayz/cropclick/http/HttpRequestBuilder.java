package com.github.bakuplayz.cropclick.http;

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
 * A class for handling <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods">HTTP Requests</a>.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class HttpRequestBuilder {

    private final @Getter HttpURLConnection connection;

    private @Getter String params;
    private @Getter HttpStatus status;
    private @Getter HashMap<String, String> headers;


    public HttpRequestBuilder(@NotNull String url)
            throws IOException {
        this.connection = (HttpURLConnection) new URL(url).openConnection();
        this.headers = new HashMap<>();
        this.params = "";
    }


    /**
     * It sets the default headers for the request.
     *
     * @return The RequestUtil object.
     */
    public HttpRequestBuilder setDefaultHeaders() {
        headers.put("User-Agent", "Mozilla/5.0");
        return this;
    }


    /**
     * It takes a list of Param objects and converts them to a string.
     *
     * @return A RequestUtil object.
     */
    public HttpRequestBuilder setParams(@NotNull HttpParam... param) {
        this.params = Arrays.stream(param)
                            .map(HttpParam::toString)
                            .collect(Collectors.joining("&"));
        return this;
    }


    /**
     * It sets the headers for the request.
     *
     * @param headers    The headers to be sent with the request.
     * @param addDefault Adds the default request headers.
     *
     * @return The RequestUtil object.
     */
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
     * @return a builder instance.
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
     * @return a builder instance.
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
     * @return a builder instance.
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
     * @return a builder instance.
     */
    public HttpRequestBuilder delete(boolean doOutput)
            throws IOException {
        return doRequest("DELETE", doOutput);
    }

    /**
     * Makes an <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods">HTTP request</a> with the given method.
     *
     * @param method   the http method.
     * @param doOutput makes the request provide a response.
     *
     * @return a builder instance.
     */
    private HttpRequestBuilder doRequest(String method, boolean doOutput)
            throws IOException {
        connection.setRequestMethod(method);
        connection.setDoOutput(doOutput);

        writeParams();
        updateStatus();

        return this;
    }


    /**
     * Writes the provided {@link HttpParam HTTP params}, if provided.
     */
    private void writeParams()
            throws IOException {
        if (params.equals("")) {
            return;
        }

        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.writeBytes(params);
        dos.flush();
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
     * Returns the response of the http request, unless it is unchanged then it returns an {@link JsonNull#INSTANCE JsonNull instance}.
     *
     * @return A JsonElement object.
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