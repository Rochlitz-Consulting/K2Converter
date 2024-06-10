package org.rochlitz.K2Converter.sql.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rochlitz.K2Converter.type.record.types.KopfRecord;

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
        KopfRecord record = new KopfRecord();//KopfRecord(tableName=FAM_L, isFull=true, currentDeliveryValidityDate=2013-04-01T00:00, previousDeliveryValidityDate=null, dataSource=ABDATA PHARMA-DATEN-SERVICE, filenameLong=Fertigarzneimittel, adbaFileNumber=null, countKRecords=1045)
        record.setTableName("tableName");
        exchange.getIn().setBody(record);

        converter.process(exchange);

        String sql = exchange.getIn().getBody(String.class);
        assertEquals("CREATE SCHEMA IF NOT EXISTS laien_info;USE laien_info;", sql);
    }

}