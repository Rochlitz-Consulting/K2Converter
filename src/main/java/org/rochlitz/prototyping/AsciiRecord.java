package org.rochlitz.prototyping;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import lombok.Data;

@Data
@CsvRecord(separator = "','", skipFirstLine = false)
public class AsciiRecord {

    @DataField(pos = 1)
    private String field1;

    @DataField(pos = 2)
    private String field2;

    @DataField(pos = 3)
    private String field3;

    @DataField(pos = 4)
    private String field4;

    @DataField(pos = 5)
    private String field5;

    @DataField(pos = 6)
    private String field6;

    @DataField(pos = 7)
    private String field7;

    @DataField(pos = 8)
    private String field8;

    @DataField(pos = 9)
    private String field9;

    @DataField(pos = 10)
    private String field10;

    @DataField(pos = 11)
    private String field11;

    @Override public String toString()
    {
        return "TypeKRecord{" +
            "field1='" + field1 + '\'' +
            ", field2='" + field2 + '\'' +
            ", field3='" + field3 + '\'' +
            ", field4='" + field4 + '\'' +
            ", field5='" + field5 + '\'' +
            ", field6='" + field6 + '\'' +
            ", field7='" + field7 + '\'' +
            ", field8='" + field8 + '\'' +
            ", field9='" + field9 + '\'' +
            ", field10='" + field10 + '\'' +
            ", field11='" + field11 + '\'' +
            '}';
    }
}