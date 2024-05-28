package org.rochlitz.K2Converter;

import static org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor.CRLF;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.rochlitz.K2Converter.objectConverter.FeldConverterProcessor;
import org.rochlitz.K2Converter.objectConverter.GenericRecord;
import org.rochlitz.K2Converter.out.SqlToFileWriter;
import org.rochlitz.K2Converter.sqlConverter.FeldToSqlConverter;
import org.rochlitz.K2Converter.sqlConverter.KopfToSqlConverter;
import org.rochlitz.K2Converter.unmarshall.RecordUnmashallProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class K2Converter extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(K2Converter.class);

    ThreadLocal<String> tableName = new ThreadLocal<>();

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





    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new K2Converter());
        context.start();
        Thread.sleep(5000);
        context.stop();
    }
}
