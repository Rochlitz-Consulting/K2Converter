package org.rochlitz.K2Converter.type.record.types;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InsertRecord<T> {
    public final static Integer MAX_FIELD_SIZE = 1000000; // 1 mil ascii chars == 600 A4

    private List<String> values;

}
