package org.rochlitz.K2Converter.objectConverter;


import static org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor.CRLF;

import java.util.HashMap;

/*
 * Starts with 0
 */
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

    public String getFieldValue(int pos)
    {
        final String fieldNumber = getFieldNumber(pos);
        return getFields().get(pos-1).split( CRLF+fieldNumber )[1];
    }

    private static String getFieldNumber(int pos)
    {
        if(pos<10){
            return "0"+pos;
        }
        return Integer.toString(pos);
    }
}
