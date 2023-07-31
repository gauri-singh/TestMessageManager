package com.learning.TestMessageMaster;

import java.util.ArrayList;
import java.util.List;




public class MessageManager {
    private static MessageManager instance;
    private final List<Message> messages;

    private MessageManager() {
        messages = new ArrayList<>();
    }

    public static synchronized MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public void addMessage(Message message) {
        messages.add(0, message);
    }

    public List<Message> getMessage() {
        return messages;
    }
}
