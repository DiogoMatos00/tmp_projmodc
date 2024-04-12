package org.Client;

public class Message {
    private User sender;
    private User recipient;
    private String content;

    public Message(User sender, User recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }
}
