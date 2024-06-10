package org.rochlitz.K2Converter.sql.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.RouteContext;
import org.rochlitz.K2Converter.type.record.InsertRecord;
import org.rochlitz.K2Converter.type.record.types.FeldRecord;

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
        RouteContext.setTableName("my_table");

        FeldRecord record = new FeldRecord();
        record.setPrimaryKey(false);
        record.setNullable(false);


        record.setFieldName("field1");
        RouteContext.setTableInfo(record);

        exchange.getIn().setBody(insertRecord);

        // Call the process method
        new InsertToSqlConverter().process(exchange);

        // Verify the generated SQL statement
        String expectedSql = String.format(SqlTemplates.INSERT_INTO, "my_table", "field1") +
            String.format(SqlTemplates.INSERT_VALUES, "'value1'") + SqlTemplates.SEMICOLON;
        assertEquals(expectedSql, exchange.getIn().getBody(String.class));
    }




}