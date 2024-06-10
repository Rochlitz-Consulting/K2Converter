package org.rochlitz.K2Converter.type.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.RouteContext;
import org.rochlitz.K2Converter.type.record.GenericRecord;
import org.rochlitz.K2Converter.type.record.types.KopfRecord;

public class KopfConverterProcessor implements Processor
{

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public void process(Exchange exchange) throws ClassNotFoundException
    {

        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        KopfRecord kopfRecord = new KopfRecord();
        kopfRecord.setTableName(genericRecord.getFields().get(0));
        kopfRecord.setIsFull(convertAndSetIsUpdate(genericRecord.getFields().get(1)));


        if(!genericRecord.getFields().get(2).isEmpty())
        {
            LocalDateTime currentDeliveryValidityDate = parseDate(genericRecord.getFields().get(2));
            kopfRecord.setCurrentDeliveryValidityDate(currentDeliveryValidityDate);
        }

        if(!genericRecord.getFields().get(3).isEmpty())
        {
            LocalDateTime previousDeliveryValidityDate = parseDate(genericRecord.getFields().get(3));
            kopfRecord.setPreviousDeliveryValidityDate(previousDeliveryValidityDate);
        }


        kopfRecord.setDataSource(genericRecord.getFields().get(4));
        kopfRecord.setFilenameLong(genericRecord.getFields().get(5));
        kopfRecord.setCountKRecords(Integer.parseInt(genericRecord.getFields().get(6)));

        RouteContext.resetTableInfo();//reset table info
        RouteContext.resetCountInserts();//reset table info

        RouteContext.setKopfInfo(kopfRecord);
        exchange.getIn().setBody(kopfRecord);
    }

    public LocalDateTime parseDate(String fieldValue)
    {

        if (fieldValue.isEmpty())
        {
            return null;
        }
        return LocalDate
            .parse(fieldValue, formatter)
            .atTime(LocalTime.of(0, 0));
    }


    public boolean convertAndSetIsUpdate(String fieldValue) {
        return "GES".equals(fieldValue);// "UPD".equals(fieldValue); for update
    }

}
