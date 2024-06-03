package org.rochlitz.K2Converter.sqlConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.toTypeConverter.FeldRecord;

public class SqlConverterTest {

    private StringBuffer sql;
    private FeldRecord feldRecord;

    @BeforeEach
    public void setUp() {
        sql = new StringBuffer();
        feldRecord = new FeldRecord();
    }

    @Test
    public void addPrimaryKey_shouldGenerateCorrectSql() {
        feldRecord.setFieldName("id");
        feldRecord.setBytes(Integer.valueOf("22000"));
        SqlConverter.addPrimaryKey(sql, "testTable", feldRecord);

        assertEquals("CREATE TABLE testTable (id BIGINT PRIMARY KEY) ", sql.toString());
    }

    @Test
    public void addPrimaryKey_bigint_shouldGenerateCorrectSql() {
        feldRecord.setFieldName("id");
        feldRecord.setBytes(Integer.valueOf("2147483646"));
        SqlConverter.addPrimaryKey(sql, "testTable", feldRecord);

        assertEquals("CREATE TABLE testTable (id BIGINT PRIMARY KEY) ".trim(), sql.toString().trim());
    }

    @Test
    public void addFieldType_shouldGenerateCorrectSqlForStringType() {
        feldRecord.setDataType(String.class);
        feldRecord.setBytes(5000);
        SqlConverter.addFieldType(feldRecord, sql);

        assertEquals("TEXT", sql.toString().trim());
    }

    @Test
    public void addFieldType_shouldGenerateCorrectSqlForBooleanType() {
        feldRecord.setDataType(Boolean.class);
        SqlConverter.addFieldType(feldRecord, sql);

        assertEquals("BOOLEAN", sql.toString().trim());
    }

    @Test
    public void addFieldType_shouldGenerateCorrectSqlForIntegerType() {
        feldRecord.setDataType(Integer.class);
        feldRecord.setBytes(32000);
        SqlConverter.addFieldType(feldRecord, sql);

        assertEquals("SMALLINT", sql.toString().trim());
    }

    @Test
    public void addFieldType_shouldGenerateCorrectSqlForLocalDateTimeType() {
        feldRecord.setDataType(LocalDateTime.class);
        SqlConverter.addFieldType(feldRecord, sql);

        assertEquals("DATE", sql.toString().trim());
    }

    @Test
    public void addFieldName_shouldGenerateCorrectSql() {
        SqlConverter.addFieldName(sql, "testTable", feldRecord);

        assertEquals("ALTER TABLE testTable ADD null ".trim(), sql.toString().trim());
    }
}