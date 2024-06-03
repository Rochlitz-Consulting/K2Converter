package org.rochlitz.K2Converter.sqlConverter;


import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.toTypeConverter.FeldRecord;
import org.rochlitz.K2Converter.Context;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeldToSqlConverterTest {

    private FeldToSqlConverter converter;
    private Exchange exchange;

    @BeforeEach
    public void setUp() {
        converter = new FeldToSqlConverter();
        exchange = new DefaultExchange(new DefaultCamelContext());
    }

    @Test
    public void process_with_add_field() throws ClassNotFoundException
    {
        FeldRecord record = new FeldRecord();
        record.setPrimaryKey(false);
        record.setNullable(false);
        exchange.getIn().setBody(record);

        converter.process(exchange);

        String sql = exchange.getIn().getBody(String.class);
        assertEquals("ALTER TABLE " + Context.getTableName() + " ADD null  NOT NULL;", sql);
    }

    @Test
    public void process_withPrimaryKey() throws ClassNotFoundException
    {
        FeldRecord record = new FeldRecord();
        record.setPrimaryKey(true);
        record.setNullable(true);
        exchange.getIn().setBody(record);

        converter.process(exchange);

        String sql = exchange.getIn().getBody(String.class);
        assertEquals("CREATE TABLE " + Context.getTableName() + " (" + record.getFieldName() +" BIGINT PRIMARY KEY"+ ") ;", sql);
    }


}