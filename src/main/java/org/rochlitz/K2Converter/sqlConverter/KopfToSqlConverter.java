package org.rochlitz.K2Converter.sqlConverter;

import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.ALTER_TABLE_S_ADD_CONSTRAINT_S_PRIMARY_KEY;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.CREATE_TABLE_IF_NOT_EXISTS;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.FeldRecord;
import org.rochlitz.K2Converter.GenericRecord;
import org.rochlitz.K2Converter.ThreadLocalContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KopfToSqlConverter implements Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(KopfToSqlConverter.class);

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {
        String tableName = ThreadLocalContext.getTableName();
        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        ThreadLocalContext.setTableName(genericRecord.getFieldValue(1));
        String sql = String.format(CREATE_TABLE_IF_NOT_EXISTS, tableName);
        LOG.info("Generated SQL: " + sql);

        exchange.getIn().setBody(sql);

    }

}
