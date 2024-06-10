package org.rochlitz.K2Converter.sql.converter;

import static org.rochlitz.K2Converter.sql.converter.SqlConverter.addFieldName;
import static org.rochlitz.K2Converter.sql.converter.SqlConverter.addFieldType;
import static org.rochlitz.K2Converter.sql.converter.SqlConverter.addPrimaryKey;
import static org.rochlitz.K2Converter.sql.converter.SqlTemplates.NOT_NULL;
import static org.rochlitz.K2Converter.sql.converter.SqlTemplates.NULL;
import static org.rochlitz.K2Converter.sql.converter.SqlTemplates.SEMICOLON;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.RouteContext;
import org.rochlitz.K2Converter.type.record.types.FeldRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeldToSqlConverter implements Processor
{
//TODO extract to converter class
    private static final Logger LOG = LoggerFactory.getLogger(FeldToSqlConverter.class);

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {
        String tableName = RouteContext.getTableName();
        FeldRecord feldRecord = exchange.getIn().getBody(FeldRecord.class);
        StringBuffer sql = new StringBuffer();
        if(feldRecord.getPrimaryKey()){
            addPrimaryKey(sql, tableName, feldRecord);
        }else {
            addFieldName(sql, tableName, feldRecord);
            addFieldType(feldRecord, sql);
            addNullable(feldRecord, sql);
        }


        sql.append(SEMICOLON);

        LOG.debug("Generated SQL: " + sql);

        exchange.getIn().setBody(sql);
    }

    private void addNullable(FeldRecord feldRecord, StringBuffer sql)
    {
        if(feldRecord.getNullable()){
            sql.append(NULL);
        }else {
            sql.append(NOT_NULL);
        }
    }

}
