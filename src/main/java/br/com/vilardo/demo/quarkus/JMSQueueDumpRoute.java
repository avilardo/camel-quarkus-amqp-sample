package br.com.vilardo.demo.quarkus;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class JMSQueueDumpRoute extends RouteBuilder{
    
    private static String QUEUE_ID = "{{route.jms.queue.id}}";
    private static String QUEUE_URI = "{{route.jms.queue.uri}}";

    @Override
    public void configure() throws Exception {
        

        from(QUEUE_URI)
            .routeId(QUEUE_ID)
            .log(LoggingLevel.INFO, "Teste \n ${body}");
    }
}
