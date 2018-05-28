package nl.yschuurmans.MessageServer;

import nl.yschuurmans.MessageServer.kafka.sender.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.awt.*;

@SpringBootApplication
public class MessageServerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServerApplication.class);

    public static boolean toMove = false;

    private static KafkaSender kafkaSender;

    public static void main(String[] args) {
        kafkaSender = new KafkaSender();
        //SpringApplication.run(MessageServerApplication.class, args);

        SpringApplicationBuilder builder = new SpringApplicationBuilder(MessageServerApplication.class);
        builder.headless(false).run(args);

        LOG.info("Started Application");
    }
} 
