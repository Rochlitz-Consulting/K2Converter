package org.rochlitz.K2Converter;

public class ThreadLocalContext {
    private static final ThreadLocal<String> threadLocalVariable = new ThreadLocal<>();

    public static void setTableName(String value) {
        threadLocalVariable.set(value);
    }

    public static String getTableName() {
        return threadLocalVariable.get();
    }

    public static void remove() {
        threadLocalVariable.remove();
    }
}
