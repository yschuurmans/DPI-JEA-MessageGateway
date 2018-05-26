package nl.yschuurmans.MessageServer.api.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
public class ConsumerWebsocket  extends TextWebSocketHandler {
    public ConsumerWebsocket() {
    }

    private static Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());
    private static Map<String, ArrayList<WebSocketSession>> subscribedTopics = Collections.synchronizedMap(new HashMap<String, ArrayList<WebSocketSession>>());
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerWebsocket.class);

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        SubscribeToTopic(session, message.getPayload());

    }

    public void SendMessage(String topic, String message) throws IOException{
        if(!subscribedTopics.containsKey(topic)){
            LOG.warn("No subscribers found on topic: '{}', not sending message: '{}'", topic, message);
            return;
        }
        for(WebSocketSession webSocketSession : subscribedTopics.get(topic)) {
            if(webSocketSession != null && webSocketSession.isOpen() )
                webSocketSession.sendMessage(new TextMessage(message));
        }
    }

    public void SubscribeToTopic(WebSocketSession socket, String topic) {
        if(!subscribedTopics.containsKey(topic))
            subscribedTopics.put(topic,new ArrayList<>());

        if(!subscribedTopics.get(topic).contains(socket))
            subscribedTopics.get(topic).add(socket);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        if(!sessions.contains(session))
            sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if(sessions.contains(session))
            sessions.remove(session);
    }

}
