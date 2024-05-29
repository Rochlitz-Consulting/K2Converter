package org.rochlitz.K2Converter.toTypeConverter;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;

public class InsertConverterProcessor implements org.apache.camel.Processor
{

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {
        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        InsertRecord insertRecord = new InsertRecord();
        List<String> fields = genericRecord.getFields();

        List<String> columns = new ArrayList<>(genericRecord.getFields());
        insertRecord.setColumns(columns);
//TODO remove 0 if empty field
        exchange.getIn().setBody(insertRecord);
    }
}
