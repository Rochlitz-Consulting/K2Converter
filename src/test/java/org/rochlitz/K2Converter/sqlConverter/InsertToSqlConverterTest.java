package org.rochlitz.K2Converter.sqlConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.INSERT_INTO;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.INSERT_VALUES;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.SEMICOLON;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.Context;
import org.rochlitz.K2Converter.toTypeConverter.FeldRecord;
import org.rochlitz.K2Converter.toTypeConverter.InsertRecord;

class InsertToSqlConverterTest {

    private Exchange exchange;

    @BeforeEach
    public void setUp() {
        exchange = new DefaultExchange(new DefaultCamelContext());
    }

    @Test
    void testProcess_HappyPath() throws Exception {
        InsertRecord insertRecord = new InsertRecord();
        insertRecord.setValues(Arrays.asList("value1"));
        Context.setTableName("my_table");

        FeldRecord record = new FeldRecord();
        record.setPrimaryKey(false);
        record.setNullable(false);


        record.setFieldName("field1");
        Context.setTableInfo(record);

        exchange.getIn().setBody(insertRecord);

        // Call the process method
        new InsertToSqlConverter().process(exchange);

        // Verify the generated SQL statement
        String expectedSql = String.format(INSERT_INTO, "my_table", "field1") +
            String.format(INSERT_VALUES, "'value1'") + SEMICOLON;
        assertEquals(expectedSql, exchange.getIn().getBody(String.class));
    }




}