package org.rochlitz.K2Converter.toTypeConverter;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.rochlitz.K2Converter.Context;

public class InsertConverterProcessor implements org.apache.camel.Processor
{

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {
        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        InsertRecord insertRecord = new InsertRecord();
        List<String> fields = genericRecord.getFields();
        insertRecord.setId(fields.get(0));

        List<String> columns = new ArrayList<>(genericRecord.getFields());
        columns.remove(0);
        insertRecord.setColumns(columns);

        exchange.getIn().setBody(insertRecord);
    }
}
