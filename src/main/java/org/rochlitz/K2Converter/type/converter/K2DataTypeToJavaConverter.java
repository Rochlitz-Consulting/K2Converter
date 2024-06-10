package org.rochlitz.K2Converter.type.converter;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class K2DataTypeToJavaConverter<T>
{
    private static final Map<String, Class<?>> DATA_TYPE_MAP = new HashMap<>();

    static {
        DATA_TYPE_MAP.put("AL1", String.class);
        DATA_TYPE_MAP.put("AN1", String.class);
        DATA_TYPE_MAP.put("AN2", String.class);
        DATA_TYPE_MAP.put("AN3", String.class);
        DATA_TYPE_MAP.put("ATC", String.class);
        DATA_TYPE_MAP.put("B64", Base64.class);
        DATA_TYPE_MAP.put("DT8", LocalDateTime.class);//DT8achtstellige Datumsangabe: Zeichenkette aus den Ziffern 0-9, Ausprägung: JJJJMMTT
//        DATA_TYPE_MAP.put("DTV", LocalDateTime.class); //TODO Angabe von Monats- und Jahreszahl (z. B.: Verfalldatum): Zeichenkette aus den //Ziffern 0-9, Ausprägung: MMJJJJ
        DATA_TYPE_MAP.put("FLA", Boolean.class);
        DATA_TYPE_MAP.put("FN1", String.class);
        DATA_TYPE_MAP.put("FN2", String.class);
        DATA_TYPE_MAP.put("GK1", Double.class);
        DATA_TYPE_MAP.put("GRU", String.class);
        DATA_TYPE_MAP.put("ID1", String.class);
        DATA_TYPE_MAP.put("IKZ", String.class);
        DATA_TYPE_MAP.put("IND", String.class);
        DATA_TYPE_MAP.put("MPG", Double.class);
        DATA_TYPE_MAP.put("NU1", Integer.class);
        DATA_TYPE_MAP.put("NU2", Integer.class);
        DATA_TYPE_MAP.put("NU3", Integer.class);
        DATA_TYPE_MAP.put("NU4", Integer.class);
        DATA_TYPE_MAP.put("PNH", Integer.class);
        DATA_TYPE_MAP.put("PRO", String.class);
        DATA_TYPE_MAP.put("PZN", Integer.class);
        DATA_TYPE_MAP.put("PZ8", Integer.class);
        DATA_TYPE_MAP.put("WGS", String.class);
    }


    public Class<T> convertNU1ToJavaType(String fieldValue) throws ClassNotFoundException
    {
        String k2Type = fieldValue.length() >= 3 ? fieldValue.substring(0, 3) : fieldValue;
        Class<?> clazz = DATA_TYPE_MAP.get(k2Type);
        if (clazz != null) {
            return (Class<T>) clazz;
        } else {
            throw new ClassNotFoundException("No data type found for k2Type: " + k2Type);
        }
    }

}
