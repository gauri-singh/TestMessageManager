package com.learning.TestMessageMaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class WebpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.videoPlayer);
        getLifecycle().addObserver(youTubePlayerView);
        // to go back to main page
        findViewById(R.id.backBtn).setOnClickListener(view -> startActivity(new Intent(WebpageActivity.this,MainActivity.class)));
        //getting video link from user:
        Intent i = getIntent();
        String linkId = i.getStringExtra("link");
        if(linkId.length()==0){
            linkId ="dQw4w9WgXcQ";
        }
        // adding youtube player listner
        String finalLinkId = linkId;
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

    }
}