package nl.yschuurmans.MessageServer.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageSender {

    @PostMapping("/send/{channel}")
    public String sendMessage() {
        return "Test";
    }

    @RequestMapping(value = "/test")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("test");
    }
}
