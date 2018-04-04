package com.example.shant.changecolor;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView t;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = findViewById(R.id.textView);
    }
    public void buttonClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.select_color, null);
        final Button c1 = alertLayout.findViewById(R.id.color1);
        final Button c2 = alertLayout.findViewById(R.id.color2);
        final Button c3 = alertLayout.findViewById(R.id.color3);
        final Button c4 = alertLayout.findViewById(R.id.color4);
        final Button c5 = alertLayout.findViewById(R.id.color5);
        final Button c6 = alertLayout.findViewById(R.id.color6);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);

       AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        dialog = alert.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.color1:
                System.out.println("changing color");
                t.setBackgroundColor(Color.RED);
                break;
            case R.id.color2:
                System.out.println("changing color");
                t.setBackgroundColor(Color.GREEN);
                break;
            case R.id.color3:
                System.out.println("changing color");
                t.setBackgroundColor(Color.YELLOW);
                break;
            case R.id.color4:
                System.out.println("changing color");
                t.setBackgroundColor(Color.WHITE);
                break;
            case R.id.color5:
                System.out.println("changing color");
                t.setBackgroundColor(Color.BLUE);
                break;
            case R.id.color6:
                System.out.println("changing color");
                t.setBackgroundColor(Color.MAGENTA);
                break;
        }
        dialog.dismiss();
}
}
