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
public class KafkaReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaReceiver.class);

    @Autowired
    ConsumerWebsocket ws;

    @KafkaListener(topics = "${app.topic.clientmessage}")
    public void listen(@Payload String message) {

        try {
            Message msgContent = new ObjectMapper().readValue(message, Message.class);

            LOG.info("received message='{}' in topic='{}'", msgContent.getMessage(), msgContent.getTarget());

            ws.SendMessage(msgContent.getTarget(), msgContent.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
