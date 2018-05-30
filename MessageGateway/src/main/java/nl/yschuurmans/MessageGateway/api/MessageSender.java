package nl.yschuurmans.MessageGateway.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.yschuurmans.MessageGateway.kafka.sender.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageSender {

    @Autowired
    KafkaSender kafkaSender;

    @PostMapping("/send")
    public ResponseEntity sendMessageToKafka(@RequestBody String body) throws JsonProcessingException {
         kafkaSender.send(body);
        return ResponseEntity.ok().build();
    }
}
