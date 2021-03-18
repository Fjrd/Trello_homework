package org.levelup.trello.utils;

import java.util.Objects;

public class StringUtils {

    // 1. null              -> null
    // 2. ""                -> ""
    // 3. "   "             -> "    "
    // 4. "usual string"    -> reverse
    // 5. "   abc   "       -> "   cba   "
    // 6. "0"               -> "0"
    // 7. "abc<enter>"           -> "<enter>cba"
    public static String reverse(String original) {
        Objects.requireNonNull(original);

        if (original.trim().isEmpty()) {
            return original;
        }

        return new StringBuilder(original)
                .reverse()
                .toString();
    }

}
