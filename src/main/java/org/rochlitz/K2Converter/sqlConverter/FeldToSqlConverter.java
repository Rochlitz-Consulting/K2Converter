package org.rochlitz.K2Converter.sqlConverter;

import static java.lang.Math.abs;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.BOOLEAN;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.CREATE_TABLE_IF_NOT_EXISTS_ADD_PRIMARY_KEY;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.DATE;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.INT;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.SEMICOLON;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.TEXT;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.VARCHAR;

import java.time.LocalDateTime;

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
        }

//TODO add NULL

        sql.append(SEMICOLON);

        LOG.info("Generated SQL: " + sql);

        exchange.getIn().setBody(sql);
    }

    private static void addPrimaryKey(StringBuffer sql, String tableName, FeldRecord feldRecord)
    {
        sql.append(
            String.format(
                CREATE_TABLE_IF_NOT_EXISTS_ADD_PRIMARY_KEY, tableName,
            feldRecord.getFieldName()
            )
        );
    }

    private static void addFieldType(FeldRecord feldRecord, StringBuffer sql)
    {
        final int toleranceBuffer = 10;//TODO configure

        if(String.class == feldRecord.getDataType() ){
            if(feldRecord.getBytes() >= 5000 ){
                sql.append(
                    String.format(
                        TEXT
                    )
                );
            }else
            {

                sql.append(
                    String.format(
                        VARCHAR,
                        feldRecord.getBytes()+ toleranceBuffer
                    )
                );
            }
        }

        if(Boolean.class == feldRecord.getDataType() ){
            sql.append(
                String.format(
                    BOOLEAN,
                    feldRecord.getBytes()
                )
            );
        }
        //SMALLINT: For smaller integer ranges, typically -32,768 to 32,767., INT 2,147,483,647.
        if(Integer.class == feldRecord.getDataType() ){
            //2,147,483,647
            String intSize = "SMALLINT";
            if(abs(feldRecord.getBytes()) > 32000){
                intSize = "INT";
            }

            if(abs(feldRecord.getBytes()) >  2147483647){
                intSize = "BIGINT";
            }

            sql.append(
                String.format(
                    INT,
                    feldRecord.getBytes()+toleranceBuffer
                )
            );
        }

        if(LocalDateTime.class == feldRecord.getDataType() ){  //TODO add type
            sql.append(DATE);
        }
    }

    private static void addFieldName(StringBuffer sql, String tableName, FeldRecord feldRecord)
    {
        sql.append(

            String.format(
                ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN,
                tableName,
                feldRecord.getFieldName()
            )
        );
    }

}
