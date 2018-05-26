package nl.yschuurmans.MessageServer.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.yschuurmans.MessageServer.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topic.servermessage}")
    private String topic;

    public void send(String message) {
        LOG.info("sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }

    public void send(String customTopic, String message) throws JsonProcessingException {
        Message msg = new Message(message, customTopic);
        ObjectMapper mapper = new ObjectMapper();
        String msgJson = mapper.writeValueAsString(msg);
        LOG.info("sending message='{}' to topic='{}'", message, customTopic);
        kafkaTemplate.send(topic, msgJson);
    }
}
