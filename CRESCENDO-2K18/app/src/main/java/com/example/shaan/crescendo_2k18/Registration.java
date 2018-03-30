package com.example.shaan.crescendo_2k18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.*;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    Button b1,b2,b3,b4,b5;
    int hour=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        b5 = (Button)findViewById(R.id.button5);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        System.out.println(hour);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                findViewById(R.id.button1).setEnabled(false);
                startActivity(new Intent(this, CrceReg.class));
                break;
            case R.id.button2:
                findViewById(R.id.button2).setEnabled(false);
                startActivity(new Intent(this, NonCrceReg.class));
                break;
            case R.id.button3:
                findViewById(R.id.button3).setEnabled(false);
                startActivity(new Intent(this, NextRec.class));
                break;
            case R.id.button4:
                findViewById(R.id.button4).setEnabled(false);
                startActivity(new Intent(this, BalCheck.class));
                break;
            case R.id.button5:
                if (hour >= 15) {
                    startActivity(new Intent(this, ClearAcc.class));
                } else
                    Toast.makeText(this, "Available only after 3pm", Toast.LENGTH_LONG).show();
                break;
        }
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);

    }
}
