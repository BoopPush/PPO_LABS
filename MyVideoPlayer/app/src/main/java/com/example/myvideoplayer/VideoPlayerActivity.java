package com.example.myvideoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class VideoPlayerActivity extends AppCompatActivity {

    private TextView videoNameTV,videoTimeTV;
    private ImageButton backIB,forwardIB,playpauseIB;
    private SeekBar videoSeekBar;
    private String videoName, videoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        videoName = getIntent().getStringExtra("videoName");
        videoPath = getIntent().getStringExtra("videoPath");
    }
}