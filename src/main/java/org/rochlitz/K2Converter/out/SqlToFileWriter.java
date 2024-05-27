package org.rochlitz.K2Converter.out;

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

public class SqlToFileWriter implements Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(SqlToFileWriter.class);

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {
        String fileName = "abda.sql";//TODO configuration
        String sql = exchange.getIn().getBody(String.class);
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(sql);
        } catch (Exception e) {
            LOG.error("Error writing SQL to file", e);
        }
    }


}
