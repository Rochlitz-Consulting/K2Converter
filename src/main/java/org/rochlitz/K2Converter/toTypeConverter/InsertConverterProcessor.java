package org.rochlitz.K2Converter.toTypeConverter;

import java.util.stream.Collectors;

import org.apache.camel.Exchange;

public class InsertConverterProcessor implements org.apache.camel.Processor
{

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {
        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        InsertRecord feldRecord = new InsertRecord();
        feldRecord.setId(genericRecord.getFields().get(0));

        genericRecord.getFields().remove(0);
        String content = genericRecord.getFields().values().stream().collect(Collectors.joining());

        feldRecord.setContent(content);

        exchange.getIn().setBody(feldRecord);
    }
}
