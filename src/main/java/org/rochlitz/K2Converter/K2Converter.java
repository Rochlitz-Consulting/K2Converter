package org.rochlitz.K2Converter;

import static org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor.RECORD_DELIMITER;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.rochlitz.K2Converter.out.SqlToFileWriter;
import org.rochlitz.K2Converter.sqlConverter.FeldToSqlConverter;
import org.rochlitz.K2Converter.sqlConverter.InsertToSqlConverter;
import org.rochlitz.K2Converter.sqlConverter.KopfToSqlConverter;
import org.rochlitz.K2Converter.toTypeConverter.EndConverterProcessor;
import org.rochlitz.K2Converter.toTypeConverter.FeldConverterProcessor;
import org.rochlitz.K2Converter.toTypeConverter.GenericRecord;
import org.rochlitz.K2Converter.toTypeConverter.InsertConverterProcessor;
import org.rochlitz.K2Converter.toTypeConverter.KopfConverterProcessor;
import org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class K2Converter extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(K2Converter.class);
    public static final String ABDA_DIR_PATH = "ABDA_DIR_PATH";
    public static final String SQL_FILE_PATH = "SQL_FILE_PATH";

    @Override
    public void configure()  {

        String abdaDirPath = getInpuPath();
        RecordCountAggregationStrategy recordCountAggregationStrategy = new RecordCountAggregationStrategy();


        from("file:"+ abdaDirPath +"?noop=true")
            .log("Processing file: ${header.CamelFileName}")
            .split(splitRecords())
            .aggregate(constant(true), recordCountAggregationStrategy)
            .completionTimeout(1000) // Timeout for aggregation completion
            .process(exchange -> {
                Integer recordCount = exchange.getIn().getHeader("recordCount", Integer.class);
                LOGGER.info("Total records processed: " + recordCount);
            })
            .process(new RecordUnmashallProcessor())
            .choice()
            .when(this::isTypeOfKopf)//TODO convert to Kopf Type
            .process(new KopfConverterProcessor())
            .process(new KopfToSqlConverter())
            .process(new SqlToFileWriter())
            .when(this::isTypeOfFeld)
            .process(new FeldConverterProcessor())
            .process(new FeldToSqlConverter())
            .process(new SqlToFileWriter())
            .when(this::isTypeOfInsert)
            .process(new InsertConverterProcessor())
            .process(new InsertToSqlConverter())
            .process(new SqlToFileWriter())
            .when(this::isTypeOfEnd)
            .process(new EndConverterProcessor())
            .process(this::getStatistic)
            .to("log:processed")
            .to("file:data/outbox");
        //TODO log statistic at the end

//TODO add E record

    }//TODO Camel RouteMetrics: add monitoring CPU, Memory

    private void getStatistic(Exchange exchange)
    {
        LOGGER.info("Completed conversion with {} records.", Context.getCountInserts());
    }

    private static String getInpuPath()
    {
        String abdaDirPath = System.getenv(ABDA_DIR_PATH);
        if(abdaDirPath == null)
            abdaDirPath = (String) System.getProperties().get(ABDA_DIR_PATH);
        return abdaDirPath;
    }

    public  Expression splitRecords() {

        return body().tokenize(RECORD_DELIMITER);
    }


    boolean isTypeOfFeld(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("F");
    }

    boolean isTypeOfKopf(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("K");
    }

    boolean isTypeOfEnd(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("E");
    }

    boolean isTypeOfInsert(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("I");
    }

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new K2Converter());
        context.getPropertiesComponent().setLocation("classpath:k2.properties");
        context.start();
        Thread.sleep(5000);
        context.stop();

    }
}
