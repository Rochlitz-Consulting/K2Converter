package org.rochlitz.K2Converter;

import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.ALTER_TABLE_S_ADD_CONSTRAINT_S_PRIMARY_KEY;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN;
import static org.rochlitz.K2Converter.sqlConverter.SqlTemplates.CREATE_TABLE_IF_NOT_EXISTS;
import static org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor.CRLF;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.rochlitz.K2Converter.objectConverter.FeldConverterProcessor;
import org.rochlitz.K2Converter.sqlConverter.FeldToSqlConverter;
import org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class K2Converter extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(K2Converter.class);

    ThreadLocal<String> tableName = new ThreadLocal<>();

    @Override
    public void configure() throws Exception {
        BindyCsvDataFormat bindyAscii = new BindyCsvDataFormat(AsciiRecord.class);

        from("file:/home/andre/IdeaProjects/K2Converter/src/test/resources/GES010413?fileName=FAM_L.GES&noop=true") //TODO read folder
            .split(body().tokenize(CRLF + "00"))
            .process(new RecordUnmashallProcessor())
            .choice()
            .when(this::isTypeOfKopf)
            .process(this::convertKopfToSQL)
            .when(this::isTypeOfFeld)
            .process(new FeldConverterProcessor())
            .process(new FeldToSqlConverter())
            .to("log:processed");
    }//TODO Camel RouteMetrics: add monitoring CPU, Memory


    private void convertKopfToSQL(Exchange exchange)
    {
        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        ThreadLocalContext.setTableName(genericRecord.getFieldValue(1));
        String sql = String.format(CREATE_TABLE_IF_NOT_EXISTS, tableName);
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


    private boolean isTypeOfFeld(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("F");
    }

    private boolean isTypeOfKopf(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("K");
    }



    private static void logK(Exchange exchange)
    {
        //-------------------------- logging -----------------------
        Message message = exchange.getIn();
        String body = message.getBody(String.class);

        LOG.info(" ascc -----------------------");
        LOG.info("body: " + body);

        AsciiRecord record = exchange.getIn().getBody(AsciiRecord.class);

        LOG.info("____________________ Unmarshalled record: " + record.toString());
    }

    private void unmarshalCsv(Exchange exchange) {
        try {
            org.apache.camel.spi.DataFormat dataFormat = new BindyCsvDataFormat(AsciiRecord.class);
            Message in = exchange.getIn();
            Object body = in.getBody();
            LOG.debug("Body before unmarshalling: {}", body);

            dataFormat.unmarshal(exchange, in.getBody(java.io.InputStream.class));

            AsciiRecord record = in.getBody(AsciiRecord.class);
            if (record == null) {
                LOG.error("Unmarshalled record is null");
            } else {
                LOG.debug("Unmarshalled record: {}", record);
            }
        } catch (Exception e) {
            LOG.error("Error during unmarshalling", e);
        }
    }

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new K2Converter());
        context.start();
        Thread.sleep(5000);
        context.stop();
    }
}
