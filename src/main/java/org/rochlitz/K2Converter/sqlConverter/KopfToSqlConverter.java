package org.rochlitz.K2Converter.sqlConverter;

import static org.rochlitz.K2Converter.Configuration.DB_SCHMEA_NAME;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.CREATE_SCHEMA;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.SEMICOLON;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.USE;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.RouteContext;
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

//        String dbName = exchange.getContext().resolvePropertyPlaceholders("{{"+DB_SCHMEA_NAME+"}}");
        final String dbName = System.getProperty(DB_SCHMEA_NAME);

        StringBuffer sql = new StringBuffer();
        sql.append(String.format(CREATE_SCHEMA, dbName) + SEMICOLON);
        sql.append(String.format(USE, dbName) + SEMICOLON);

        LOG.debug("Generated SQL: " + sql);
        RouteContext.setTableName(genericRecord.getTableName());
        exchange.getIn().setBody(sql);
    }
}
