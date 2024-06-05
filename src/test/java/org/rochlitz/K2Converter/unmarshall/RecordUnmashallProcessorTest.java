package org.rochlitz.K2Converter.unmarshall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor.RECORD_DELIMITER;

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
        String input =  "00K\r\n"
            + "01FAM_L\r\n"
            + "02GES\r\n"
            + "0320130401\r\n"
            + "04\r\n"
            + "05ABDATA PHARMA-DATEN-SERVICE\r\n"
            + "06Fertigarzneimittel\r\n"
            + "071045\r\n"
            + "0817";
        DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
        String record = input.split(RECORD_DELIMITER)[0];

        exchange.getIn().setBody(record);

        processor.process(exchange);

        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);
        assertEquals("K", genericRecord.getType().trim());
        assertEquals("FAM_L", genericRecord.getFields().get(0));
        assertEquals("GES", genericRecord.getFields().get(1));
        assertEquals("20130401", genericRecord.getFields().get(2));
        assertEquals("", genericRecord.getFields().get(3));
        assertEquals("ABDATA PHARMA-DATEN-SERVICE", genericRecord.getFields().get(4));
        assertEquals("Fertigarzneimittel", genericRecord.getFields().get(5));
        assertEquals("1045", genericRecord.getFields().get(6));
        assertEquals("17", genericRecord.getFields().get(7));
    }
}