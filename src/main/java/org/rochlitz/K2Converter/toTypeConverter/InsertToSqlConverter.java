package org.rochlitz.K2Converter.toTypeConverter;

import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.CREATE_SCHEMA_IF_NOT_EXISTS_S;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.SEMICOLON;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.USE;

import org.apache.camel.Exchange;
import org.rochlitz.K2Converter.ThreadLocalContext;
import org.rochlitz.K2Converter.sqlConverter.KopfToSqlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertToSqlConverter implements org.apache.camel.Processor
{
    private static final Logger LOG = LoggerFactory.getLogger(InsertToSqlConverter.class);

    public void process(Exchange exchange) throws ClassNotFoundException
    //TODO add   catch
    {
        InsertRecord genericRecord = exchange.getIn().getBody(InsertRecord.class);

        StringBuffer sql = new StringBuffer();
        sql.append(String.format(CREATE_SCHEMA_IF_NOT_EXISTS_S, "LAIENINFO") + SEMICOLON);//TODO configure
        sql.append(String.format(USE, "LAIENINFO") + SEMICOLON);//TODO configure

        LOG.info("Generated SQL: " + sql);
        exchange.getIn().setBody(sql);
    }

}
