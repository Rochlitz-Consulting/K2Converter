package org.rochlitz.K2Converter;

import static org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor.RECORD_DELIMITER;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.rochlitz.K2Converter.out.SqlToFileWriter;
import org.rochlitz.K2Converter.sqlConverter.FeldToSqlConverter;
import org.rochlitz.K2Converter.sqlConverter.InsertToSqlConverter;
import org.rochlitz.K2Converter.sqlConverter.KopfToSqlConverter;
import org.rochlitz.K2Converter.toTypeConverter.FeldConverterProcessor;
import org.rochlitz.K2Converter.toTypeConverter.GenericRecord;
import org.rochlitz.K2Converter.toTypeConverter.InsertConverterProcessor;
import org.rochlitz.K2Converter.toTypeConverter.KopfConverterProcessor;
import org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class K2Converter extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(K2Converter.class);


    @Override
    public void configure()  {

        final String abdaDirPath = System.getenv("ABDA_DIR_PATH");

        from("file:"+ abdaDirPath +"?noop=true")
            .log("Processing file: ${header.CamelFileName}")
            .split(splitRecords())
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
            .to("log:processed")
            .to("file:data/outbox");
        ;
        //TODO log statistic at the end

//TODO add E record

    }//TODO Camel RouteMetrics: add monitoring CPU, Memory


    public  Expression splitRecords() {

        return body().tokenize(RECORD_DELIMITER);
    }

    private static Processor getStatistic()
    {
        return exchange -> {
            LOG.info("Completed conversion with {} records.", Context.getCountInserts());
        };
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
