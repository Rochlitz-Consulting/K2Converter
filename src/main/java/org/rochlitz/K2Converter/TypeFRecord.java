package org.rochlitz.K2Converter;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

@FixedLengthRecord
public class TypeFRecord extends BaseRecord {

    @DataField(pos = 3, length = 3)
    private String field01;

    @DataField(pos = 6, length = 4)
    private String field02;

    @DataField(pos = 10, length = 10)
    private String field03;

    // Getter und Setter

    @Override
    public String toString() {
        return "TypeFRecord{" +
            "recordType='" + getRecordType() + '\'' +
            ", field01='" + field01 + '\'' +
            ", field02='" + field02 + '\'' +
            ", field03='" + field03 + '\'' +
            '}';
    }
}
