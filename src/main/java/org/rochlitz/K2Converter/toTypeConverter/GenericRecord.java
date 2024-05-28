package org.rochlitz.K2Converter.toTypeConverter;

import static org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor.CRLF;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GenericRecord
{

    private String type;
    //starts with 0
    private HashMap<Integer, String> fields;



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
