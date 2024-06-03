package org.rochlitz.K2Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rochlitz.K2Converter.toTypeConverter.GenericRecord;

public class K2ConverterTest {

    private K2Converter k2Converter;
    private CamelContext camelContext;

    @BeforeEach
    public void setUp() {
        k2Converter = new K2Converter();
        camelContext = new DefaultCamelContext();
    }

    @Disabled
    @Test
    public void isTypeOfFeld_shouldReturnTrueWhenTypeStartsWithF() {
        GenericRecord record = new GenericRecord("F01", null);

        Exchange exchange = new DefaultExchange(camelContext);
        exchange.getIn().setBody(record);

        boolean result = k2Converter.isTypeOfFeld(exchange);

        assertEquals(true, result);
    }

    @Test
    public void isTypeOfFeld_shouldReturnFalseWhenTypeDoesNotStartWithF() {
        GenericRecord record = Mockito.mock(GenericRecord.class);
        when(record.getType()).thenReturn("K01");

        Exchange exchange = new DefaultExchange(camelContext);
        exchange.getIn().setBody(record);

        boolean result = k2Converter.isTypeOfFeld(exchange);

        assertEquals(false, result);
    }

    @Test
    public void isTypeOfKopf_shouldReturnTrueWhenTypeStartsWithK() {
        GenericRecord record = Mockito.mock(GenericRecord.class);
        when(record.getType()).thenReturn("K01");

        Exchange exchange = new DefaultExchange(camelContext);
        exchange.getIn().setBody(record);

        boolean result = k2Converter.isTypeOfKopf(exchange);

        assertEquals(true, result);
    }

    @Test
    public void isTypeOfKopf_shouldReturnFalseWhenTypeDoesNotStartWithK() {
        GenericRecord record = Mockito.mock(GenericRecord.class);
        when(record.getType()).thenReturn("F01");

        Exchange exchange = new DefaultExchange(camelContext);
        exchange.getIn().setBody(record);

        boolean result = k2Converter.isTypeOfKopf(exchange);

        assertEquals(false, result);
    }

    @Test
    public void isTypeOfInsert_shouldReturnTrueWhenTypeStartsWithI() {
        GenericRecord record = Mockito.mock(GenericRecord.class);
        when(record.getType()).thenReturn("I01");

        Exchange exchange = new DefaultExchange(camelContext);
        exchange.getIn().setBody(record);

        boolean result = k2Converter.isTypeOfInsert(exchange);

        assertEquals(true, result);
    }

    @Test
    public void isTypeOfInsert_shouldReturnFalseWhenTypeDoesNotStartWithI() {
        GenericRecord record = Mockito.mock(GenericRecord.class);
        when(record.getType()).thenReturn("F01");

        Exchange exchange = new DefaultExchange(camelContext);
        exchange.getIn().setBody(record);

        boolean result = k2Converter.isTypeOfInsert(exchange);

        assertEquals(false, result);
    }
}