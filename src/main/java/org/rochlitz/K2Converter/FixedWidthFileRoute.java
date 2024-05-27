package org.rochlitz.K2Converter;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.dataformat.bindy.fixed.BindyFixedLengthDataFormat;
import org.apache.camel.spi.DataFormat;
public class FixedWidthFileRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        DataFormat bindyK = new BindyFixedLengthDataFormat(KRecord.class);
        DataFormat bindyF = new BindyFixedLengthDataFormat(FeldRecord.class);

        from("file:/home/andre/IdeaProjects/K2Converter/src/test/resources/GES010413?fileName=FAM_L.GES&noop=true") //TODO read all files
            .split(body().tokenize("00"))
            .choice()
            .when(body().startsWith("K"))
            .unmarshal(bindyK)
            .process(exchange -> {
                KRecord record = exchange.getIn().getBody(KRecord.class);
                System.out.println("Processed KRecord: " + record);
            })
            .when(body().startsWith("F"))
            .unmarshal(bindyF)
            .process(exchange -> {
                FeldRecord record = exchange.getIn().getBody(FeldRecord.class);
                System.out.println("Processed FRecord: " + record);
            })
            .otherwise()
            .log("Unknown record type")
            .end();
    }

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new FixedWidthFileRoute());
        context.start();

        // Keep main thread alive for a while to let Camel route finish processing
        Thread.sleep(5000);

        context.stop();
    }
}

