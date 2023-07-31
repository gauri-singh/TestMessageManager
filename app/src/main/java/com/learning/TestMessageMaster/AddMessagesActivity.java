//package com.learning.TestMessageMaster;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class AddMessagesActivity extends AppCompatActivity {
//    EditText messageInput;
//    Button saveMessageButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_messages);
//
//        messageInput = findViewById(R.id.messageInput);
//        saveMessageButton = findViewById(R.id.saveMessageButton);
//
//        // Define click behavior for the save button
//        View.OnClickListener saveMessage = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String messageText = messageInput.getText().toString();
//                if (!messageText.isEmpty()) {
//                    MessageManager.getInstance().addMessage(messageText);
//                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
//                    Intent i=new Intent(getApplicationContext(),ViewMessagesActivity.class);
//                    startActivity(i);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//
//        saveMessageButton.setOnClickListener(saveMessage);
//
//        // Check if the intent contains extra data
//        if(getIntent().getExtras() != null) {
//            String messageFromIntent = getIntent().getStringExtra("message");
//            if(messageFromIntent != null) {
//                messageInput.setText(messageFromIntent);
//                // Automatically click the save button
//
//                saveMessageButton.performClick();
//            }
//        }
//    }
//}
package com.learning.TestMessageMaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class AddMessagesActivity extends AppCompatActivity {
    EditText messageInput, recipientInput;
    Button sendMessageButton,homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_messages);

        messageInput = findViewById(R.id.messageInput);
        recipientInput = findViewById(R.id.recipientInput); // You need to add this in your layout
        sendMessageButton = findViewById(R.id.sendMessageButton);
        homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        // Define click behavior for the save button
        View.OnClickListener saveMessage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageInput.getText().toString();
                String recipientText = recipientInput.getText().toString();

                if (!messageText.isEmpty() && !recipientText.isEmpty()) {
                    MessageManager.getInstance().addMessage(new Message(recipientText, messageText, LocalDateTime.now()));

                    Toast.makeText(getApplicationContext(), "Sending now", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT, messageText);
                    startActivity(Intent.createChooser(i, "where do you want to message"));
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a message and recipient", Toast.LENGTH_SHORT).show();
                }
            }
        };

        sendMessageButton.setOnClickListener(saveMessage);

        // Check if the intent contains extra data
        if(getIntent().getExtras() != null) {
            String messageFromIntent = getIntent().getStringExtra("message");
            String recipientFromIntent = getIntent().getStringExtra("recipient");
            if(messageFromIntent != null && recipientFromIntent != null) {
                messageInput.setText(messageFromIntent);
                recipientInput.setText(recipientFromIntent);
                // Automatically click the save button
                sendMessageButton.performClick();
            }
        }
    }
}
