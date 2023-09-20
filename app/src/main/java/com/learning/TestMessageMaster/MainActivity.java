package com.learning.TestMessageMaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button addMessageButton, viewMessagesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMessageButton = findViewById(R.id.addMessageButton);
        viewMessagesButton = findViewById(R.id.viewMessagesButton);
        //replaced new view onClick to lambda
        addMessageButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddMessagesActivity.class)));

        viewMessagesButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewMessagesActivity.class)));

        EditText inputLink = findViewById(R.id.inputLink);
        findViewById(R.id.viewWebsite).setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this,WebpageActivity.class);
            i.putExtra("link",inputLink.getText().toString());
            startActivity(i);
        });
    }
}
