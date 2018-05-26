package nl.yschuurmans.MessageServer.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.yschuurmans.MessageServer.kafka.sender.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageSender {

    @Autowired
    KafkaSender kafkaSender;

    @PostMapping("/send/{topic}")
    public ResponseEntity sendMessageToKafka(@PathVariable String topic, @RequestBody String body) throws JsonProcessingException {
         kafkaSender.send(topic, body);
        return ResponseEntity.ok().build();
    }
}
