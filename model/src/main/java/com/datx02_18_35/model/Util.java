package com.datx02_18_35.model;

import java.util.Arrays;
import java.util.Stack;

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

    public static void Log(String string) {
        StackTraceElement st = new Throwable().getStackTrace()[1];
        StringBuilder sb = new StringBuilder();
        sb.append("LOG [");
        String[] packages = st.getClassName().split("\\.");
        sb.append(packages[packages.length-1]);
        //sb.append(st.getClassName());
        sb.append(":");
        sb.append(st.getMethodName());
        sb.append(":");
        sb.append(st.getLineNumber());
        sb.append("]: ");
        sb.append(string);
        System.out.println(sb);
    }
}
