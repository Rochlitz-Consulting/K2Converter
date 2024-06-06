package org.rochlitz.K2Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.rochlitz.K2Converter.toTypeConverter.FeldRecord;
import org.rochlitz.K2Converter.toTypeConverter.KopfRecord;

public class RouteContext
{
    private static final ThreadLocal<String> tableName = new ThreadLocal<>();


    private static ConcurrentHashMap<String, String> sharedData = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Object> sharedObjects = new ConcurrentHashMap<>();


    private static final ThreadLocal<List> tableInfo = new ThreadLocal<>();

    private static final ThreadLocal<Integer> countInserts = new ThreadLocal<>();

    private static final String kopfKey = "kopf";

    public static void setTableName(String value) {
        tableName.set(value);
        sharedData.put("table", value);
    }

    public static String getTableName() {

        return sharedData.get("table");
    }

//TODO all like this
    public static void setKopfInfo(KopfRecord value) {
        sharedObjects.put(kopfKey, value);
    }

    public static KopfRecord getKopfInfo() {

        return (KopfRecord) sharedObjects.get(kopfKey);
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


    public static Integer incrementCountInserts() {
        Integer count = countInserts.get();
        if(count == null){
            count = 0;
        }
        count++;
        countInserts.set(count);
        return getCountInserts();
    }

    public static Integer getCountInserts() {
        return countInserts.get();
    }


}
