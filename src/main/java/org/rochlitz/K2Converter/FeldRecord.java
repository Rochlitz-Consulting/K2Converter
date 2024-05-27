package org.rochlitz.K2Converter;

import lombok.Data;

@Data
public class FeldRecord
{
    public final static Integer MAX_FIELD_SIZE = 1000000;//1 mil ascii chars ==  600 A4

    private String id;

    private String fieldName;

    private Boolean primaryKey = false;

    private Boolean nullable = false;

    private Integer length;

    private String fieldValue;

    private String dataType;




    @Override public String toString()
    {
        return "FeldRecord{" +
            "id='" + id + '\'' +
            ", fieldName='" + fieldName + '\'' +
            ", primaryKey=" + primaryKey +
            ", nullable=" + nullable +
            ", length=" + length +
            ", fieldValue='" + fieldValue + '\'' +
            '}';
    }

    public void convertAndSetPrimaryKey(String primaryKey)
    {
        if(primaryKey.equals("1")){
            this.primaryKey = true;
        }
    }

    public void convertAndSetNullable(String fieldValue)
    {
        if(fieldValue.equals("1")){
            this.nullable = true;
        }
    }

    public void convertAndSetLength(String fieldValue, String length)
    {
        if(fieldValue.equals("F")){//feste Feldlänge,
            this.setLength(Integer.valueOf(length));
        }
        if (fieldValue.equals("V")){//variable Feldlänge,
            this.setLength(Integer.valueOf(length));
        }
        if (fieldValue.equals("U")) {//variable, unbegrenzte Feldlänge
            this.setLength(MAX_FIELD_SIZE);//maximum
        }
    }

    public void convertAndSetDataType(String fieldValue)
    {
        this.dataType=fieldValue;
    }
}
