package org.rochlitz.K2Converter.toTypeConverter;

import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.INSERT_INTO;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.INSERT_VALUES;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.SEMICOLON;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.rochlitz.K2Converter.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertToSqlConverter implements org.apache.camel.Processor
{
    private static final Logger LOG = LoggerFactory.getLogger(InsertToSqlConverter.class);

    public void process(Exchange exchange) throws ClassNotFoundException
    //TODO add   catch
    {
        InsertRecord insertRecord = exchange.getIn().getBody(InsertRecord.class);

        String tableName = Context.getTableName();
        List<FeldRecord> tableInfo = Context.getTableInfo();

        String columns = tableInfo
            .stream()
            .map(feldRecord -> feldRecord.getFieldName())
            .collect(Collectors.joining(", "));

//        StringBuffer values = new StringBuffer();
        String values = insertRecord
            .getColumns()
            .stream()
            .map(value -> "'" + value + "'")
            .collect(Collectors.joining(", "))
            .toString();


        StringBuffer sql = new StringBuffer();
        sql.append(String.format(INSERT_INTO, tableName, columns) );//TODO configure
        sql.append(String.format(INSERT_VALUES, values) + SEMICOLON);//TODO configure

//        sql.append(String.format(USE, "LAIENINFO") + SEMICOLON);//TODO configure

        LOG.info("Generated SQL: " + sql);
        exchange.getIn().setBody(sql);
    }

}
