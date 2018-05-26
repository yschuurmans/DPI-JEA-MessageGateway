package nl.yschuurmans.MessageServer.domain;

public class Message {
    private String target;
    private long messageId;
    private String message;


    public Message(String target, long messageId, String message) {
        this.target = target;
        this.messageId = messageId;
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
