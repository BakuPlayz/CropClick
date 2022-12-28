package com.github.bakuplayz.cropclick.http;

/**
 * A class representing common <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status">HTTP responses</a> when working with plugin updates.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public enum HttpStatus {

    /**
     * HTTP Code: 200
     */
    OK,

    /**
     * HTTP Code: 304
     */
    UNCHANGED,

    /**
     * HTTP Code: 404
     */
    NOT_FOUND,

}