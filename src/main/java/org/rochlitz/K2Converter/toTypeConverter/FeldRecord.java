package org.rochlitz.K2Converter.toTypeConverter;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class FeldRecord<T> {
    public final static Integer MAX_FIELD_SIZE = 1000000; // 1 mil ascii chars == 600 A4

    private String id;
    private String fieldName;
    private Boolean primaryKey = false;
    private Boolean nullable = false;
    private Integer bytes;
    private Class<T> dataType;



    public void convertAndSetPrimaryKey(String primaryKey) {
        this.primaryKey = "1".equals(primaryKey);
    }

    public void convertAndSetNullable(String fieldValue) {
        this.nullable = "1".equals(fieldValue);
    }

    /**
     * Typ der Feldlänge hat folgenden Wertebereich:
     * F feste Feldlänge, in ID 06 ist die konkrete Länge angegeben
     * V variable Feldlänge, in ID 06 ist die maximale Länge angegeben
     * U variable, unbegrenzte Feldlänge, ID 06 ist nicht belegt
     *
     * @param fieldValue
     * @param length
     */
    public void convertAndSetLength(String fieldValue, String length) {
        boolean fixSizeLimit = "F".equals(fieldValue);
        boolean variableSizeLimit = "V".equals(fieldValue);
        boolean unlimitedSize = "U".equals(fieldValue);

        if (fixSizeLimit || variableSizeLimit) {
            this.bytes = Integer.valueOf(length);
        } else
        {
            if (unlimitedSize) {
                this.bytes = MAX_FIELD_SIZE; // maximum
            }
        }
    }

    /**
     * Feldlänge in Byte, maximal 10-stellig, Datentyp NU1
     * Converts the field value to the correct data type
     * @param fieldValue
     */
    public void convertAndSetDataType(String fieldValue) throws ClassNotFoundException
    {
        K2DataTypeToJavaConverter<Object> typeConverter = new K2DataTypeToJavaConverter<>();
        dataType = (Class<T>) typeConverter.convertNU1ToJavaType(fieldValue);
    }

}
