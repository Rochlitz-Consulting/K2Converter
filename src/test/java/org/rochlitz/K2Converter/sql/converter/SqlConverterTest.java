package org.rochlitz.K2Converter.sql.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.type.record.types.FeldRecord;

public class SqlConverterTest {

    private StringBuffer sql;
    private FeldRecord feldRecord;

    @BeforeEach
    public void setUp() {
        sql = new StringBuffer();
        feldRecord = new FeldRecord();
    }

    @Test
    public void addFieldType_shouldGenerateCorrectSqlForTinyIntType() {
        feldRecord.setDataType(Integer.class);
        feldRecord.setBytes(1);
        SqlConverter.addFieldType(feldRecord, sql);

        assertEquals("TINYINT", sql.toString().trim());
    }

    @Test
    public void addFieldType_shouldGenerateCorrectSqlForMediumIntType() {
        feldRecord.setDataType(Integer.class);
        feldRecord.setBytes(3);
        SqlConverter.addFieldType(feldRecord, sql);

        assertEquals("MEDIUMINT", sql.toString().trim());
    }

    @Test
    public void addFieldType_shouldGenerateCorrectSqlForBigIntType() {
        feldRecord.setDataType(Integer.class);
        feldRecord.setBytes(6);
        SqlConverter.addFieldType(feldRecord, sql);

        assertEquals("BIGINT", sql.toString().trim());
    }

    @Test
    public void addFieldName_shouldGenerateCorrectSql() {
        feldRecord.setFieldName("testField");
        SqlConverter.addFieldName(sql, "testTable", feldRecord);

        assertEquals("ALTER TABLE testTable ADD testField", sql.toString().trim());
    }
}