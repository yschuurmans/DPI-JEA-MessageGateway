package nl.yschuurmans.MessageGateway.api.ws;

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
public class ConsumerWebsocket extends TextWebSocketHandler {
    public ConsumerWebsocket() {
    }

    private static Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());
    private static Map<String, ArrayList<WebSocketSession>> subscribedTopics = Collections.synchronizedMap(new HashMap<String, ArrayList<WebSocketSession>>());
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerWebsocket.class);

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        LOG.warn("Received '{}'", message);
        SubscribeToTopic(session, message.getPayload());

    }

    public void SendMessage(String target, String envelope) throws IOException {
        if (!subscribedTopics.containsKey(target)) {
            LOG.warn("No subscribers found on topic: '{}', not sending message: '{}'", target, envelope);
            return;
        }
        for (WebSocketSession webSocketSession : subscribedTopics.get(target)) {
            if (webSocketSession != null && webSocketSession.isOpen()) {
                LOG.warn("Sending: '{}'  to: '{}'", envelope, target);
                webSocketSession.sendMessage(new TextMessage(envelope));
            }
        }
    }

    public void SubscribeToTopic(WebSocketSession socket, String topic) {
        LOG.warn("Subscribing '{}'", topic);
        if (!subscribedTopics.containsKey(topic))
            subscribedTopics.put(topic, new ArrayList<>());

        if (!subscribedTopics.get(topic).contains(socket))
            subscribedTopics.get(topic).add(socket);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (!sessions.contains(session))
            sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (sessions.contains(session))
            sessions.remove(session);
    }

}
