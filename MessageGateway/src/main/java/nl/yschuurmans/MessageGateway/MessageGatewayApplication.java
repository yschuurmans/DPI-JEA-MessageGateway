package nl.yschuurmans.MessageGateway;

import nl.yschuurmans.MessageGateway.kafka.sender.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MessageGatewayApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MessageGatewayApplication.class);

    public static boolean toMove = false;

    private static KafkaSender kafkaSender;

    public static void main(String[] args) {
        kafkaSender = new KafkaSender();
        //SpringApplication.run(MessageGatewayApplication.class, args);

        SpringApplicationBuilder builder = new SpringApplicationBuilder(MessageGatewayApplication.class);
        builder.headless(false).run(args);

        LOG.info("Started Application");
    }
} 
