package com.shantanu.fitmantra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CardioFitness extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_fitness);
        findViewById(R.id.gotocardio).setOnClickListener(this);
        findViewById(R.id.gotoexcercise).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.gotocardio:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.gotoexcercise:
                startActivity(new Intent(this,ExcerciseMain.class));
                break;

        }

    }
}
