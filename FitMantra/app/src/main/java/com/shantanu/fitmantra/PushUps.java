package com.shantanu.fitmantra;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class PushUps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_push_ups);
        VideoView view = (VideoView)findViewById(R.id.videoView1);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.pushups;
        view.setVideoURI(Uri.parse(path));
        view.start();
    }
}
