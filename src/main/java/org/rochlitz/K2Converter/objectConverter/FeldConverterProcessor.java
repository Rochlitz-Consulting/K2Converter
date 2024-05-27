package org.rochlitz.K2Converter.objectConverter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.FeldRecord;
import org.rochlitz.K2Converter.GenericRecord;

public class FeldConverterProcessor implements Processor
{
    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {

        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        FeldRecord feldRecord = new FeldRecord();
        feldRecord.setId(genericRecord.getFieldValue(1));
        feldRecord.setFieldName(genericRecord.getFieldValue(2));
        feldRecord.convertAndSetPrimaryKey(genericRecord.getFieldValue(3));
        feldRecord.convertAndSetNullable(genericRecord.getFieldValue(4));
        feldRecord.convertAndSetLength(genericRecord.getFieldValue(5),genericRecord.getFieldValue(6));
        feldRecord.convertAndSetDataType(genericRecord.getFieldValue(7));

        exchange.getIn().setBody(feldRecord);

    }
}
