package org.rochlitz.K2Converter.csv;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
@Deprecated
@CsvRecord(separator = "\r\n")
public class MyRecord {
    @DataField(pos = 1)
    private String field1;

    @DataField(pos = 2)
    private String field2;

    // Füge weitere Felder hinzu entsprechend der Datei

    // Getter und Setter Methoden
}

