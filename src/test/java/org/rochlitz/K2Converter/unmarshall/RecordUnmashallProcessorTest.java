package org.rochlitz.K2Converter.unmarshall;


import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.toTypeConverter.GenericRecord;
import org.apache.camel.impl.DefaultCamelContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordUnmashallProcessorTest {

    private RecordUnmashallProcessor processor;

    @BeforeEach
    public void setUp() {
        processor = new RecordUnmashallProcessor();
    }

    @Test
    public void testProcess() {
        String record = "\r\n00Ktable\r\n01First'Field\r\n02SecondField\r\n03ThirdField";
        DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setBody(record);

        processor.process(exchange);

        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);
        assertEquals("K", genericRecord.getType());
        assertEquals("FirstField", genericRecord.getFields().get(0));
        assertEquals("SecondField", genericRecord.getFields().get(1));
        assertEquals("ThirdField", genericRecord.getFields().get(2));
    }
}