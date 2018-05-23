package nl.yschuurmans.MessageServer;

import nl.yschuurmans.MessageServer.kafka.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageServerApplication {

	private static final Logger LOG = LoggerFactory.getLogger(MessageServerApplication.class);


    private static Sender sender;

	public static void main(String[] args) {
        sender = new Sender();
	    SpringApplication.run(MessageServerApplication.class, args);
		LOG.info("Started Application");
	}
} 
