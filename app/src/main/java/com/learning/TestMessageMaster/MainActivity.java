package com.learning.TestMessageMaster;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

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
        findViewById(R.id.viewYTDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        // Inflate the custom youtube layout
        View customView = inflater.inflate(R.layout.youtube_dialog, null);

        YouTubePlayerView youTubePlayerView = customView.findViewById(R.id.video_dialog);
        //getLifecycle().addObserver(youTubePlayerView);
        // Initialize the YouTube player
        String finalLinkId = "dQw4w9WgXcQ";
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // loading the selected video into the YouTube Player
                youTubePlayer.loadVideo(finalLinkId, 0);
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                // this method is called if video has ended,
                super.onStateChange(youTubePlayer, state);
            }
        });

        builder.setView(customView);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.gravity = Gravity.TOP;
            window.setAttributes(layoutParams);
        }
        dialog.show();
    }
}
