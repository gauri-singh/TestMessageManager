package com.learning.TestMessageMaster;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private static MessageManager instance;
    private final List<Message> messages;
    private Context context;

    private MessageManager(Context context) {
        messages = new ArrayList<>();
        this.context = context;
    }

    public static synchronized MessageManager getInstance(Context context) {
        if (instance == null) {
            instance = new MessageManager(context);
        }
        return instance;
    }

    public void addMessage(Message message) {
        messages.add(0, message);
        // After adding the message, update the widget with the latest message.
        MessageWidgetProvider.updateTextWidgets(context, message.getMessage(), message.getRecipient());
    }

    public List<Message> getMessage() {
        return messages;
    }
}
