package org.rochlitz.prototyping;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
@Deprecated
@CsvRecord(separator = "\r\n")
public class KRecord {

    @DataField(pos = 1)
    private String recordType;

    @DataField(pos = 2)
    private String field01;

    @DataField(pos = 3)
    private String field02;

    @DataField(pos = 4)
    private String field03;

    @DataField(pos = 5)
    private String field04;

    @DataField(pos = 6)
    private String field05;

    @DataField(pos = 7)
    private String field06;

    @DataField(pos = 8)
    private String field07;

    // Weitere Felder...

    // Getter und Setter
    @Override
    public String toString() {
        return "KRecord{" +
            "recordType='" + recordType + '\'' +
            ", field01='" + field01 + '\'' +
            ", field02='" + field02 + '\'' +
            ", field03='" + field03 + '\'' +
            ", field04='" + field04 + '\'' +
            ", field05='" + field05 + '\'' +
            ", field06='" + field06 + '\'' +
            ", field07='" + field07 + '\'' +
            '}';
    }
}
