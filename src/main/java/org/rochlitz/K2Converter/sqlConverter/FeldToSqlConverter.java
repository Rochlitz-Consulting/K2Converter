package org.rochlitz.K2Converter.sqlConverter;

import static org.rochlitz.K2Converter.sqlConverter.SqlConverter.addFieldName;
import static org.rochlitz.K2Converter.sqlConverter.SqlConverter.addFieldType;
import static org.rochlitz.K2Converter.sqlConverter.SqlConverter.addPrimaryKey;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.NOT_NULL;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.NULL;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.SEMICOLON;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.Context;
import org.rochlitz.K2Converter.toTypeConverter.FeldRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeldToSqlConverter implements Processor
{
//TODO extract to converter class
    private static final Logger LOG = LoggerFactory.getLogger(FeldToSqlConverter.class);

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {
        String tableName = Context.getTableName();
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

        LOG.info("Generated SQL: " + sql);

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
