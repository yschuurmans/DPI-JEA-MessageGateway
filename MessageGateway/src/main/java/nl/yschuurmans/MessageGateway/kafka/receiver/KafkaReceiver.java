package nl.yschuurmans.MessageGateway.kafka.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.yschuurmans.MessageGateway.api.ws.ConsumerWebsocket;
import nl.yschuurmans.MessageGateway.domain.Envelope;
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
    public void listen(@Payload String payload) {

        try {
            Envelope envelope = new ObjectMapper().readValue(payload, Envelope.class);

            LOG.info("received message='{}' for target='{}'", envelope.getMessage(), envelope.getTarget());

            ws.SendMessage(envelope.getTarget(), payload);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
