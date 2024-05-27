package org.rochlitz.K2Converter;

public class FeldRecord
{

    private String id;

    private String fieldName;

    private Boolean primaryKey = false;

    private Boolean nullable;

    private Integer length;

    private String fieldValue;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public Boolean getPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    public Boolean getNullable()
    {
        return nullable;
    }

    public void setNullable(Boolean nullable)
    {
        this.nullable = nullable;
    }

    public Integer getLength()
    {
        return length;
    }

    public void setLength(Integer length)
    {
        this.length = length;
    }

    public String getFieldValue()
    {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue)
    {
        this.fieldValue = fieldValue;
    }

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

}
