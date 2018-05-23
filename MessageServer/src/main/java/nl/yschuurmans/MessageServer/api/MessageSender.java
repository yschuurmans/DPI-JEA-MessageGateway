package nl.yschuurmans.MessageServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.yschuurmans.MessageServer.api.ws.ConsumerWebsocket;
import nl.yschuurmans.MessageServer.domain.Message;
import nl.yschuurmans.MessageServer.kafka.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/messages")
public class MessageSender {

    @Autowired
    Sender sender;

    @Autowired
    ConsumerWebsocket ws;

    @PostMapping("/send/{topic}")
    public String sendMessageToKafka(@PathVariable String topic, @RequestBody String body) {
        sender.send(topic, body);
        return "Test";
    }

    @PostMapping("/broadcast/{topic}")
    public String broadcastKafkaMessage(@PathVariable String topic, @RequestBody String body) {
        sender.send("TestMessage");
        return "Test";
    }

    @RequestMapping("/sendKafkaTest")
    public String testKafkaSendMessage() {
        sender.send("TestMessage");
        return "Test";
    }

    @RequestMapping("/send/{topic}")
    public String testSendMessage(@PathVariable String topic) {
        try {
            ws.SendMessage(topic, "Test");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Test";
    }

    @RequestMapping(value = "/test")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("test");
    }
}
