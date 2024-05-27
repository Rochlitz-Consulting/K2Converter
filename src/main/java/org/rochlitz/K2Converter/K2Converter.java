package org.rochlitz.K2Converter;

import static org.rochlitz.K2Converter.SqlTemplates.ALTER_TABLE_S_ADD_CONSTRAINT_S_PRIMARY_KEY;
import static org.rochlitz.K2Converter.SqlTemplates.ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN;
import static org.rochlitz.K2Converter.SqlTemplates.CREATE_TABLE_IF_NOT_EXISTS;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class K2Converter extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(K2Converter.class);
    public static String CRLF = "\r\n";
    private String tableName;

    @Override
    public void configure() throws Exception {
        BindyCsvDataFormat bindyAscii = new BindyCsvDataFormat(AsciiRecord.class);//TODO use unmashall()???

        from("file:/home/andre/IdeaProjects/K2Converter/src/test/resources/GES010413?fileName=kurz_FAM_L.GES&noop=true") //TODO read folder
            .split(body().tokenize(CRLF + "00"))
            .process(this::convertToGenericRecord)
            .choice()
            .when(this::isTypeOfKopf)
            .process(this::convertKopfToSQL)
            .when(this::isTypeOfFeld)
            .process(this::convertToFeldType)
            .process(this::convertFeldToSQL)
            .to("log:processed");
    }//TODO Camel RouteMetrics: add monitoring CPU, Memory

    private void convertKopfToSQL(Exchange exchange)
    {
        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        tableName = genericRecord.getFieldValue(1);
        String sql = String.format(CREATE_TABLE_IF_NOT_EXISTS, tableName);
        LOG.info("Generated SQL: " + sql);

        writeSqlToFile(sql, "abda.sql");//TODO configuration
    }

    private void convertToFeldType(Exchange exchange)
    {
        GenericRecord genericRecord = exchange.getIn().getBody(GenericRecord.class);

        FeldRecord feldRecord = new FeldRecord();
        feldRecord.setId(genericRecord.getFieldValue(1));
        feldRecord.setFieldName(genericRecord.getFieldValue(2));
        feldRecord.convertAndSetPrimaryKey(genericRecord.getFieldValue(3));

        exchange.getIn().setBody(feldRecord);
    }

    private void convertFeldToSQL(Exchange exchange)
    {
        FeldRecord feldRecord = exchange.getIn().getBody(FeldRecord.class);
        String sql;
        if(feldRecord.getPrimaryKey()){
            sql = String.format(ALTER_TABLE_S_ADD_CONSTRAINT_S_PRIMARY_KEY, tableName, feldRecord.getFieldName(), feldRecord.getFieldName());
        }else {
            sql = String.format(ALTER_TABLE_S_ADD_IF_NOT_EXISTS_COLUMN, tableName, feldRecord.getFieldName());
        }
        LOG.info("Generated SQL: " + sql);

        writeSqlToFile(sql, "abda.sql");//TODO configuration
    }

    private void convertToGenericRecord(Exchange exchange)
    {
        Message message = exchange.getIn();
        String record = message.getBody(String.class);
        GenericRecord genericRecord = map(record);
        message.setBody(genericRecord);
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

    private GenericRecord map(String record) {
        HashMap<Integer, String> fields = new HashMap();

        int i = 0;
        while (true) {
            boolean endReached=false;
            int beginIndex = record.indexOf(CRLF.concat( String.format("%02d", i + 1)));
            int endIndex = i < 9 ? record.indexOf(CRLF.concat(String.format("%02d", i + 2))) : record.length();
            if(beginIndex==-1){
                 break;
            }
            if(endIndex==-1){
                endReached=true;
            }
            if (endReached) {
                fields.put(i, record.substring(beginIndex));
            }else{
                fields.put(i, record.substring(beginIndex, endIndex));
            }
            i++;
        }
        log.info("/-------------------------");
        log.info("fields: " +fields);
        log.info("-------------------------/");


        String type = "K";

        //TODO use case
        if(!record.startsWith("00K")){
            type = record.substring(0, 1);
        }
        return new GenericRecord(type,fields);
    }

    private static void logAscii(Exchange exchange)
    {
        //-------------------------- logging -----------------------
        Message message = exchange.getIn();
        String body = message.getBody(String.class);

        LOG.info(" ascc -----------------------");
        LOG.info("body: " + body);

        AsciiRecord record = exchange.getIn().getBody(AsciiRecord.class);

        LOG.info("____________________ Unmarshalled record: " + record.toString());
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
