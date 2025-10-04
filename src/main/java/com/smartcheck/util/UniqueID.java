package com.smartcheck.util;

public class UniqueID {

    public static String getIdWithSuffix(String suffix) {
        return suffix + getId();
    }

    public static String getId() {
        return String.valueOf(System.currentTimeMillis() + (int) (Math.random() * 10));
    }
}
