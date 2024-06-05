package org.rochlitz.K2Converter.toTypeConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KopfConverterProcessorTest {

    private KopfConverterProcessor processor;
    private Exchange exchange;

    @BeforeEach
    public void setUp() {
        processor = new KopfConverterProcessor();
        exchange = new DefaultExchange(new DefaultCamelContext());
    }

    @Test
    public void process_shouldConvertGenericRecordToKopfRecord() throws ClassNotFoundException {
        GenericRecord record = new GenericRecord("K", Arrays.asList("filename", "GES", "20220101", "20211201", "dataSource", "filenameLong", "1"));
        exchange.getIn().setBody(record);

        processor.process(exchange);

        KopfRecord kopfRecord = exchange.getIn().getBody(KopfRecord.class);
        assertNotNull(kopfRecord);
        assertEquals("filename", kopfRecord.getTableName());
        assertEquals(true, kopfRecord.getIsFull());
        assertEquals(LocalDateTime.of(2022, 1, 1, 0, 0), kopfRecord.getCurrentDeliveryValidityDate());
        assertEquals(LocalDateTime.of(2021, 12, 1, 0, 0), kopfRecord.getPreviousDeliveryValidityDate());
        assertEquals("dataSource", kopfRecord.getDataSource());
        assertEquals("filenameLong", kopfRecord.getFilenameLong());
        assertEquals(1, kopfRecord.getCountKRecords());
    }

    @Test
    public void parseDate_shouldReturnCorrectLocalDateTime() {
        LocalDateTime result = processor.parseDate("20220101");

        assertEquals(LocalDateTime.of(2022, 1, 1, 0, 0), result);
    }

    @Test
    public void convertAndSetIsUpdate_shouldReturnTrueForGES() {
        boolean result = processor.convertAndSetIsUpdate("GES");

        assertEquals(true, result);
    }

    @Test
    public void convertAndSetIsUpdate_shouldReturnFalseForNonGES() {
        boolean result = processor.convertAndSetIsUpdate("UPD");

        assertEquals(false, result);
    }
}