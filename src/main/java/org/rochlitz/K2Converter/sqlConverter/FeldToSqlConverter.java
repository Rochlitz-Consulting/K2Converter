package org.rochlitz.K2Converter.sqlConverter;

import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.ALTER_TABLE_S_ADD_CONSTRAINT_S_PRIMARY_KEY;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.FeldRecord;
import org.rochlitz.K2Converter.ThreadLocalContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeldToSqlConverter implements Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(FeldToSqlConverter.class);

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {
        String tableName = ThreadLocalContext.getTableName();
        FeldRecord feldRecord = exchange.getIn().getBody(FeldRecord.class);
        String sql;
        if(feldRecord.getPrimaryKey()){
            sql = String.format(
                ALTER_TABLE_S_ADD_CONSTRAINT_S_PRIMARY_KEY, tableName,
                feldRecord.getFieldName(),
                feldRecord.getFieldName()
            );
        }else {
            sql = String.format(
                ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN,
                tableName,
                feldRecord.getFieldName()
            );
        }
        LOG.info("Generated SQL: " + sql);

        writeSqlToFile(sql, "abda.sql");//TODO configuration
    }

    private void writeSqlToFile(String sql, String fileName) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(sql);
        } catch (Exception e) {
            LOG.error("Error writing SQL to file", e);
        }
    }


}
