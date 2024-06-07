package org.rochlitz.K2Converter.out;

import static org.rochlitz.K2Converter.Configuration.SQL_FILE_PATH;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rochlitz.K2Converter.RouteContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlToFileWriter implements Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(SqlToFileWriter.class);

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {

//        String tableImportfileName = buildOutPutFilename();
        String tableImportfileName = System.getProperty(SQL_FILE_PATH);

        String sql = exchange.getIn().getBody(String.class);
        //TODO add SQL validation
        try (PrintWriter out = new PrintWriter(new FileWriter(tableImportfileName, true))) {
            out.println(sql);
        } catch (Exception e) {
            LOG.error("Error writing SQL to file", e);
        }
    }

    private static String buildOutPutFilename()
    {
        String tableName = RouteContext.getTableName();
        final String fileName = System.getProperty(SQL_FILE_PATH);
        StringTokenizer tokenizer = new StringTokenizer(fileName, ".");
        String tableImportfileName = tokenizer.nextToken() + "_" + tableName +"."+ tokenizer.nextToken();
        return tableImportfileName;
    }

}
