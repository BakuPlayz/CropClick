package com.github.bakuplayz.cropclick.update;

/**
 * Represents all the possible update states the plugin can be in.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public enum UpdateState {

    /**
     * The response sent when a new update was found on the update server.
     */
    NEW_UPDATE,

    /**
     * The response sent when no update at all was found on the update server.
     */
    NO_UPDATE_FOUND,

    /**
     * The response sent when no new update was found on the update server.
     */
    UP_TO_DATE,

    /**
     * The response sent when the update server has yet to be checked for an update.
     */
    NOT_FETCHED_YET,

    /**
     * The response sent when an update was unable to be retrieved.
     */
    FAILED_TO_FETCH

}