package org.rochlitz.csv;

import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.DataFormat;

public class CSVDemo
{
    public static void main(String[] args) throws Exception
    {
        CamelContext context = new DefaultCamelContext();
        final DataFormat bindy = new BindyCsvDataFormat(User.class);
        context.addRoutes(new RouteBuilder()
        {
            @Override

            public void configure() throws Exception
            {

                from("file:/home/andre/IdeaProjects/K2Converter/src/main/resources/data?noop=true&include=.*\\.csv")
                    .split(
                        body().tokenize("\n"))
                    .log("Body: ${body}")
                    .unmarshal(bindy)
                    .process(exchange -> {
                            Message message = exchange.getIn();

                            String body = message.getBody(String.class);

                            System.out.println(" ***************************** body ****************************");
                            System.out.println("body: " + body);
                        }


                        )
                    .process(new SimpleProcessor())
                    .log("Body: ${body}");

                from("direct:start")
                    .marshal(bindy)
                    .log("Body: ${body}");

            }
        });

        context.start();

        ProducerTemplate template = context.createProducerTemplate();
        User user = new User();
        user.setName("John");
        user.setSurname("Smith");

        template.sendBody("direct:start", user);

        Thread.sleep(1000);

        context.stop();
    }

}   
    
