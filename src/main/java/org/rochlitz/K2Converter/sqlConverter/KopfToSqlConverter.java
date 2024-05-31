package org.rochlitz.K2Converter.sqlConverter;

import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.CREATE_SCHEMA_S;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.SEMICOLON;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.USE;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.toTypeConverter.GenericRecord;
import org.rochlitz.K2Converter.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KopfToSqlConverter implements Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(KopfToSqlConverter.class);

    public void process(Exchange exchange) throws ClassNotFoundException
    //TODO add   catch
    {
        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        final String dbName = exchange.getContext().resolvePropertyPlaceholders("{{DB}}");

        StringBuffer sql = new StringBuffer();
        sql.append(String.format(CREATE_SCHEMA_S, dbName) + SEMICOLON);//TODO configure
        sql.append(String.format(USE, dbName) + SEMICOLON);//TODO configure

        LOG.info("Generated SQL: " + sql);
        Context.setTableName(genericRecord.getFieldValue(1));
        exchange.getIn().setBody(sql);
    }
}
