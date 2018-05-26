package nl.yschuurmans.MessageServer.api;

import nl.yschuurmans.MessageServer.api.ws.ConsumerWebsocket;
import nl.yschuurmans.MessageServer.kafka.sender.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestApi {


    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    ConsumerWebsocket ws;


    @PostMapping("/broadcast/{topic}")
    public String broadcastTestKafkaMessage(@PathVariable String topic, @RequestBody String body) {
        kafkaSender.send("TestMessage");
        return "Test";
    }

    @RequestMapping("/sendKafkaTest")
    public String testKafkaSendMessage() {
        kafkaSender.send("TestMessage");
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
}
