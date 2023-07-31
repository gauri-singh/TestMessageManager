package com.learning.TestMessageMaster;

import java.time.LocalDateTime;

public class Message {
    private String recipient;
    private String message;
    private LocalDateTime dateTime;

    public Message(String recipient, String message, LocalDateTime dateTime) {
        this.recipient = recipient;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
