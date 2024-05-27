package org.rochlitz.K2Converter;

import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.fixed.BindyFixedLengthDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

@Deprecated
public class RecordReader extends RouteBuilder
{

    @Override
    public void configure() throws Exception
    {

        BindyFixedLengthDataFormat typeKBindy = new BindyFixedLengthDataFormat(TypeKRecord.class);
        BindyFixedLengthDataFormat typeFBindy = new BindyFixedLengthDataFormat(TypeFRecord.class);

        from("file:/home/andre/IdeaProjects/K2Converter/src/test/resources/GES010413?fileName=kurz_FAM_L.GES&noop=true") //TODO read all files
            .split(body().tokenize("\r\n")) // Split using double CRLF
            .choice()
            .when(body().startsWith("00K"))
            .unmarshal(typeKBindy)
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                System.out.println("body: " + body);
                TypeKRecord record = exchange.getIn().getBody(TypeKRecord.class);
                System.out.println("Processed TypeKRecord: " + record);
            })
            .when(body().startsWith("00F"))
            .unmarshal(typeFBindy)
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                System.out.println("body: " + body);
                Message in = exchange.getIn();
                System.out.println("body: " + in.getBody());
                TypeFRecord record = in.getBody(TypeFRecord.class);
                System.out.println("Processed TypeFRecord: " + record);
            })
            .otherwise()
            .log("Unknown record type")
            .end();
    }

    public static void main(String[] args) throws Exception
    {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RecordReader());
        context.start();

        // Keep main thread alive for a while to let Camel route finish processing
        Thread.sleep(5000);

        context.stop();
    }
}
