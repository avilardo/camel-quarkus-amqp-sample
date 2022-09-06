package br.com.vilardo.demo.quarkus;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class JMSQueueDumpRoute extends RouteBuilder{
    
    private static String QUEUE_ID = "JMS-QUEUE";
    private static String QUEUE_URI = "${route.jms.queue}";

    @Override
    public void configure() throws Exception {
        

        from("jms://queue:camel.test::sub.camel.test?transacted=true&testConnectionOnStartup=true")
            .routeId(QUEUE_ID)
            .log(LoggingLevel.INFO, "Teste \n ${body}");
    }
}
