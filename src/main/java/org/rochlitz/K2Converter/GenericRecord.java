package org.rochlitz.K2Converter;

import java.util.HashMap;

import lombok.Data;
import lombok.Generated;
import lombok.Setter;

//@Data
public class GenericRecord
{
    public GenericRecord(String type, HashMap<Integer, String> fields)
    {
        this.type = type;
        this.fields = fields;
    }

    private String type;
    private HashMap<Integer, String> fields;
    private final String field01ID = "01";
    private final String field02ID = "02";

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public HashMap<Integer, String> getFields()
    {
        return fields;
    }

    public void setFields(HashMap<Integer, String> fields)
    {
        this.fields = fields;
    }

    public String getField01Value( )
    {
        return getFields().get(0).split(field01ID)[1];
    }

    public String getField02Value( )
    {
        return getFields().get(1).split(field02ID)[1];
    }

}
