package com.learning.TestMessageMaster;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ViewMessagesActivity extends AppCompatActivity {
    TextView messageList;
    Button homeButton;
    EditText searchInput;
    Spinner sortSpinner;

    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

        messageList = findViewById(R.id.messageList);
        homeButton = findViewById(R.id.homeButton);
        searchInput = findViewById(R.id.searchInput);
        sortSpinner = findViewById(R.id.sortSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = s.toString().toLowerCase();
                updateMessageList();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateMessageList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        searchQuery = getIntent().getStringExtra("description");

        if (searchQuery != null) {
            searchInput.setText(searchQuery);
        } else {
            searchQuery = "";
        }

        updateMessageList();
    }

    private void updateMessageList() {
        List<Message> messages = MessageManager.getInstance().getMessage();
        List<Message> filteredMessages = messages.stream()
                .filter(m -> m.getRecipient().toLowerCase().contains(searchQuery) ||
                        m.getMessage().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        switch (sortSpinner.getSelectedItemPosition()) {
            case 0: // Recipient (A to Z)
                Collections.sort(filteredMessages, (m1, m2) -> m1.getRecipient().compareTo(m2.getRecipient()));
                break;
            case 1: // Recipient (Z to A)
                Collections.sort(filteredMessages, (m1, m2) -> m2.getRecipient().compareTo(m1.getRecipient()));
                break;
            case 2: // Date (Earliest first)
                Collections.sort(filteredMessages, (m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime()));
                break;
            case 3: // Date (Latest first)
                Collections.sort(filteredMessages, (m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()));
                break;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.ENGLISH);
        StringBuilder messageListText = new StringBuilder();
        for (Message message : filteredMessages) {
            String formattedDateTime = message.getDateTime().format(formatter);
            messageListText.append("Recipient: " + message.getRecipient() + "\n" +
                    "Message: " + message.getMessage() + "\n" +
                    "Date: " + formattedDateTime + "\n\n");
        }

        messageList.setText(messageListText.toString());
    }
}
