package com.datx02_18_35.model;

import java.util.Arrays;

/**
 * Created by robin on 2018-03-14.
 */

public class Util {
    private Util(){}

    public static <T> T[] tail(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array must contain at least one element.");
        }
        return Arrays.copyOfRange(array, 1, array.length);
    }

    public static String join(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s);
        }
        return sb.toString();
    }
}
