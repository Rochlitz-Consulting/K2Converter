package org.rochlitz.K2Converter.sql.converter;

import static org.rochlitz.K2Converter.Configuration.DB_SCHMEA_NAME;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.RouteContext;
import org.rochlitz.K2Converter.type.record.types.KopfRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KopfToSqlConverter implements Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(KopfToSqlConverter.class);

    public void process(Exchange exchange) throws ClassNotFoundException
    //TODO add   catch
    {
        KopfRecord genericRecord = exchange.getIn().getBody(KopfRecord.class);

//        String dbName = exchange.getContext().resolvePropertyPlaceholders("{{"+DB_SCHMEA_NAME+"}}");
        final String dbName = System.getProperty(DB_SCHMEA_NAME);

        StringBuffer sql = new StringBuffer();
        sql.append(String.format(SqlTemplates.CREATE_SCHEMA, dbName) + SqlTemplates.SEMICOLON);
        sql.append(String.format(SqlTemplates.USE, dbName) + SqlTemplates.SEMICOLON);

        LOG.debug("Generated SQL: " + sql);
        RouteContext.setTableName(genericRecord.getTableName());
        exchange.getIn().setBody(sql);
    }
}
