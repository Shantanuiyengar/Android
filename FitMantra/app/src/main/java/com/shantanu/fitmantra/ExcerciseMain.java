package com.shantanu.fitmantra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExcerciseMain extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_main);
        findViewById(R.id.weights).setOnClickListener(this);
        findViewById(R.id.pushups).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.weights:
                startActivity(new Intent(this,Weights.class));
                break;
            case R.id.pushups:
                startActivity(new Intent(this,PushUps.class));
                break;
        }
    }
}
