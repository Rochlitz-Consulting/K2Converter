package org.rochlitz.K2Converter;

import java.util.ArrayList;
import java.util.List;

import org.rochlitz.K2Converter.toTypeConverter.FeldRecord;

public class Context
{
    private static final ThreadLocal<String> tableName = new ThreadLocal<>();

    private static final ThreadLocal<List> tableInfo = new ThreadLocal<>();

    public static void setTableName(String value) {
        tableName.set(value);
    }

    public static String getTableName() {
        return tableName.get();
    }

    public static void setTableInfo(FeldRecord value) {
        if(tableInfo.get() == null){
            tableInfo.set(new ArrayList());
        }
        tableInfo.get().add(value);
    }

    public static List getTableInfo() {
        return tableInfo.get();
    }

    public static FeldRecord getTableInfo(int pos) {
        return (FeldRecord) tableInfo.get().get(pos);
    }


}
