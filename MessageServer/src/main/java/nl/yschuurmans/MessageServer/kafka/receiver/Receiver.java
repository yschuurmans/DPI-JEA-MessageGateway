package nl.yschuurmans.MessageServer.kafka.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.yschuurmans.MessageServer.api.ws.ConsumerWebsocket;
import nl.yschuurmans.MessageServer.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Receiver {

    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    ConsumerWebsocket ws;

    @KafkaListener(topics = "${app.topic.message}")
    public void listen(@Payload String message) {

        try {
            Message msgContent = new ObjectMapper().readValue(message, Message.class);

            LOG.info("received message='{}' in topic='{}'", msgContent.getMessage(), msgContent.getTopic());

            ws.SendMessage(msgContent.getTopic(), msgContent.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
