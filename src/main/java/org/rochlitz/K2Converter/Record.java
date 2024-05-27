package org.rochlitz.K2Converter;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;
@Deprecated
@FixedLengthRecord
public class Record {                         //TODO lombok

    @DataField(pos = 1)
    private String field1;

    @DataField(pos = 2)
    private String field2;

    @DataField(pos = 3)
    private String field3;

    // Füge weitere Felder nach Bedarf hinzu

    // Getter und Setter
    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    @Override
    public String toString() {
        return "Record{" +
            "field1='" + field1 + '\'' +
            ", field2='" + field2 + '\'' +
            ", field3='" + field3 + '\'' +
            '}';
    }
}
