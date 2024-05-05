package com.coding.core.util;

public final class Utils {
    public static boolean isObjectEmpty(Object object) {
        return object == null || object.toString().trim().isEmpty();
    }
    private Utils() {}
}
