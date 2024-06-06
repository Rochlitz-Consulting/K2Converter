package org.rochlitz.K2Converter.toTypeConverter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class EndConverterProcessor implements Processor
{
    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {

        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        EndRecord endRecord = new EndRecord();
        endRecord.setDRecordCount(Integer.valueOf(genericRecord.getFields().get(0)));
        endRecord.setIRecordCount(Integer.valueOf(genericRecord.getFields().get(1)));
        endRecord.setURecordCount(Integer.valueOf(genericRecord.getFields().get(2)));
        endRecord.setTotalRecordCount(Integer.valueOf(genericRecord.getFields().get(3)));

        exchange.getIn().setBody(endRecord);
    }
}