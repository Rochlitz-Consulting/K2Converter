package org.rochlitz.K2Converter.unmarshall;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.toTypeConverter.GenericRecord;

public class RecordUnmashallProcessorTest {

    private RecordUnmashallProcessor processor;

    @BeforeEach
    public void setUp() {
        processor = new RecordUnmashallProcessor();
    }

    @Test
    public void testProcess() {
        String record = "00K\n"
            + "01FAM_L\n"
            + "02GES\n"
            + "0320130401\n"
            + "04\n"
            + "05ABDATA PHARMA-DATEN-SERVICE\n"
            + "06Fertigarzneimittel\n"
            + "071045\n"
            + "0817";
        DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setBody(record);

        processor.process(exchange);

        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);
        assertEquals("K", genericRecord.getType().trim());
        assertEquals("FirstField", genericRecord.getFields().get(0));
        assertEquals("SecondField", genericRecord.getFields().get(1));
        assertEquals("ThirdField", genericRecord.getFields().get(2));
    }
}