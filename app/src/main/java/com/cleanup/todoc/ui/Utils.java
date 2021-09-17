package com.cleanup.todoc.ui;

/**
 * Created by pmeignen on 16/09/2021.
 */
public abstract class Utils {

    /**
     * List of all possible sort methods for task
     */
    public enum SortMethod {
        /**
         * No sort (by default recent first)
         */
        NONE,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED
    }
}
