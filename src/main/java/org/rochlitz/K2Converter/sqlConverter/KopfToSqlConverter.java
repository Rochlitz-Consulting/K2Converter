package org.rochlitz.K2Converter.sqlConverter;

import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.CREATE_SCHEMA_S;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.SEMICOLON;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.USE;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.Context;
import org.rochlitz.K2Converter.toTypeConverter.KopfRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KopfToSqlConverter implements Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(KopfToSqlConverter.class);

    public void process(Exchange exchange) throws ClassNotFoundException
    //TODO add   catch
    {
        KopfRecord genericRecord = exchange.getIn().getBody(KopfRecord.class);

        final String dbName = exchange.getContext().resolvePropertyPlaceholders("{{DB}}");

        StringBuffer sql = new StringBuffer();
        sql.append(String.format(CREATE_SCHEMA_S, dbName) + SEMICOLON);
        sql.append(String.format(USE, dbName) + SEMICOLON);

        LOG.info("Generated SQL: " + sql);
        Context.setTableName(genericRecord.getFilename());
        exchange.getIn().setBody(sql);
    }
}
