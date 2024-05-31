package org.rochlitz.K2Converter.out;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlToFileWriter implements Processor
{

    private static final Logger LOG = LoggerFactory.getLogger(SqlToFileWriter.class);

    public void process(Exchange exchange) throws ClassNotFoundException //TODO add   catch
    {

        String fileName = exchange.getContext().resolvePropertyPlaceholders("{{SQL_FILE_PATH}}");
        String sql = exchange.getIn().getBody(String.class);
        //TODO add SQL validation
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(sql);
        } catch (Exception e) {
            LOG.error("Error writing SQL to file", e);
        }
    }

}
