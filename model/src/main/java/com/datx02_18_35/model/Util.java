package com.datx02_18_35.model;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by robin on 2018-03-14.
 */

public class Util {
    private Util(){}


    private static final long kibi = 1L << 10L;
    private static final long mebi = 1L << 20L;
    private static final long gibi = 1L << 30L;
    private static final long tebi = 1L << 40L;

    public static String toPrefixedByteString(long i) {
        if (i > tebi) return String.format("%.2fTiB", (double)i/tebi);
        if (i > gibi) return String.format("%.2fGiB", (double)i/gibi);
        if (i > mebi) return String.format("%.2fMiB", (double)i/mebi);
        if (i > kibi) return String.format("%.2fKiB", (double)i/kibi);
                      return String.format("%lB", i);
    }

    public static <T> T[] tail(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array must contain at least one element.");
        }
        return Arrays.copyOfRange(array, 1, array.length);
    }

    public static String join(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
        return joinImpl(delimiter, elements);
    }
    public static String join(CharSequence delimiter, CharSequence... elements) {
        return joinImpl(delimiter, Arrays.asList(elements));
    }
    public static String join(Iterable<? extends CharSequence> elements) {
        return joinImpl("", elements);
    }
    public static String join(CharSequence... elements) {
        return joinImpl("", Arrays.asList(elements));
    }

    // Helper functions for the above join functions
    private static String joinImpl(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
        StringBuilder sb = new StringBuilder();
        Iterator<? extends CharSequence> elemIterator = elements.iterator();
        if (elemIterator.hasNext()) {
            sb.append(elemIterator.next());
        }
        while (elemIterator.hasNext()) {
            sb.append(delimiter);
            sb.append(elemIterator.next());
        }
        return sb.toString();
    }


    public static void log(String string) {
        if (!Config.DEBUG_LOG_OUTPUT) {
            return;
        }
        StackTraceElement st = new Throwable().getStackTrace()[1];
        String[] lines = string.split("\r\n|\n|\r");
        for (String line : lines) {
            StringBuilder sb = new StringBuilder();
            sb.append("LOG [");
            String[] packages = st.getClassName().split("\\.");
            sb.append(packages[packages.length-1]);
            sb.append(":");
            sb.append(st.getMethodName());
            sb.append(":");
            sb.append(st.getLineNumber());
            sb.append("]: ");
            sb.append(line);
            System.out.println(sb);
        }


    }
}
