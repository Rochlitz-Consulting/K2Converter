package org.rochlitz.K2Converter.type.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.rochlitz.K2Converter.RouteContext;
import org.rochlitz.K2Converter.type.record.GenericRecord;
import org.rochlitz.K2Converter.type.record.types.InsertRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertConverterProcessor implements org.apache.camel.Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(InsertConverterProcessor.class);

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {
        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        InsertRecord insertRecord = new InsertRecord();
        List<String> fields = genericRecord.getFields();

        List<String> columns = new ArrayList<>(genericRecord.getFields());
        insertRecord.setValues(columns);
//TODO remove 0 value if empty field

        RouteContext.incrementCountInserts();
        exchange.getIn().setBody(insertRecord);

    }
}
