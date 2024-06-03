package org.rochlitz.K2Converter.sqlConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.toTypeConverter.GenericRecord;

public class KopfToSqlConverterTest {

    private KopfToSqlConverter converter;
    private Exchange exchange;

    @BeforeEach
    public void setUp() {
        converter = new KopfToSqlConverter();
        exchange = new DefaultExchange(new DefaultCamelContext());
    }

    @Test
    public void process_shouldGenerateCorrectSql() throws ClassNotFoundException {
        System.setProperty("DB", "laien_info" );
        GenericRecord record = new GenericRecord("K", Arrays.asList("01", "tableName"));
        exchange.getIn().setBody(record);

        converter.process(exchange);

        String sql = exchange.getIn().getBody(String.class);
        assertEquals("CREATE SCHEMA laien_info;USE laien_info;", sql);
    }
}