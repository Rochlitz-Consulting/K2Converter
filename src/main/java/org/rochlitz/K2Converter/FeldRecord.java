package org.rochlitz.K2Converter;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class FeldRecord<T> {
    public final static Integer MAX_FIELD_SIZE = 1000000; // 1 mil ascii chars == 600 A4

    private String id;
    private String fieldName;
    private Boolean primaryKey = false;
    private Boolean nullable = false;
    private Integer length;
    private Class<T> dataType;

    private static final Map<String, Class<?>> DATA_TYPE_MAP = new HashMap<>();

    static {
        DATA_TYPE_MAP.put("AL1", String.class);
        DATA_TYPE_MAP.put("AN1", String.class);
        DATA_TYPE_MAP.put("AN2", String.class);
        DATA_TYPE_MAP.put("AN3", String.class);
        DATA_TYPE_MAP.put("ATC", String.class);
        DATA_TYPE_MAP.put("B64", Base64.class);
        DATA_TYPE_MAP.put("DT8", LocalDateTime.class);
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

    public void convertAndSetPrimaryKey(String primaryKey) {
        this.primaryKey = "1".equals(primaryKey);
    }

    public void convertAndSetNullable(String fieldValue) {
        this.nullable = "1".equals(fieldValue);
    }

    public void convertAndSetLength(String fieldValue, String length) {
        if ("F".equals(fieldValue) || "V".equals(fieldValue)) {
            this.length = Integer.valueOf(length);
        } else if ("U".equals(fieldValue)) {
            this.length = MAX_FIELD_SIZE; // maximum
        }
    }

    /**
     * Converts the field value to the correct data type
     * @param fieldValue
     */
    public void convertAndSetDataType(String fieldValue) throws ClassNotFoundException {
        String key = fieldValue.length() >= 3 ? fieldValue.substring(0, 3) : fieldValue;
        Class<?> clazz = DATA_TYPE_MAP.get(key);
        if (clazz != null) {
            this.dataType = (Class<T>) clazz;
        } else {
            throw new ClassNotFoundException("No data type found for key: " + key);
        }
    }
}
