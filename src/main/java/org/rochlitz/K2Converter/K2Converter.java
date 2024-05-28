package org.rochlitz.K2Converter;

import static org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor.CRLF;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.rochlitz.K2Converter.toTypeConverter.FeldConverterProcessor;
import org.rochlitz.K2Converter.toTypeConverter.GenericRecord;
import org.rochlitz.K2Converter.toTypeConverter.InsertConverterProcessor;
import org.rochlitz.K2Converter.toTypeConverter.InsertToSqlConverter;
import org.rochlitz.K2Converter.out.SqlToFileWriter;
import org.rochlitz.K2Converter.sqlConverter.FeldToSqlConverter;
import org.rochlitz.K2Converter.sqlConverter.KopfToSqlConverter;
import org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class K2Converter extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(K2Converter.class);

    @Override
    public void configure() throws Exception {

        from("file:/home/andre/IdeaProjects/K2Converter/src/test/resources/GES010413?fileName=FAM_L.GES&noop=true") //TODO read folder
            .split(body().tokenize(CRLF + "00"))
            .process(new RecordUnmashallProcessor())
            .choice()
            .when(this::isTypeOfKopf)//TODO convert to Kopf Type
            .process(new KopfToSqlConverter())
            .process(new SqlToFileWriter())
            .when(this::isTypeOfFeld)
            .process(new FeldConverterProcessor())
            .process(new FeldToSqlConverter())
            .process(new SqlToFileWriter())
            .when(this::isTypeOfInsert)
            .process(new InsertConverterProcessor())
            .process(new InsertToSqlConverter())
            .to("log:processed");
    }//TODO Camel RouteMetrics: add monitoring CPU, Memory



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

    private boolean isTypeOfInsert(Exchange exchange)
    {
        GenericRecord body = exchange.getIn().getBody(GenericRecord.class);
        return body.getType().startsWith("I");
    }

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new K2Converter());
        context.start();
        Thread.sleep(5000);
        context.stop();
    }
}
