package org.rochlitz.K2Converter.toTypeConverter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GenericRecord
{

    private String type;
    //starts with 0
    private List<String> fields;



    public String getFieldValue(int pos)
    {
        return fields.get(pos-1);
    }

}
