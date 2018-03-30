package com.example.shaan.tdma;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView[] t = new TextView[15];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t[1] = (TextView)findViewById(R.id.st1);
        t[2] = (TextView)findViewById(R.id.st2);
        t[3] = (TextView)findViewById(R.id.st3);
        t[4] = (TextView)findViewById(R.id.st4);
        t[5] = (TextView)findViewById(R.id.st5);
        t[6] = (TextView)findViewById(R.id.st6);
        t[7] = (TextView)findViewById(R.id.st7);
        t[8] = (TextView)findViewById(R.id.st8);
        t[9] = (TextView)findViewById(R.id.st9);
        t[10] = (TextView)findViewById(R.id.st10);
        t[11] = (TextView)findViewById(R.id.st11);
        t[12] = (TextView)findViewById(R.id.st12);
        int i = 0;int delay = 0; int u = 1;
        for(int j = 1;j<=12;j++)
        {
            delay = new Random().nextInt(15);
            if(delay<10) {
                t[j].setBackgroundColor(Color.GREEN);
                t[j].setText(""+i+" Assigned to user: "+u);
                u++;
                i=i+417;
            }
            System.out.println(delay);
            if(delay>=10) {
                t[j].setBackgroundColor(Color.RED);
                i=i+10;
                t[j].setText(""+i+" Delay recieved of 10secs");
            }
        }
    }
}
