package com.example.shaan.crescendo_2k18;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class NonCrceReg extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2,e3,e4,e5;
    String s1,s2,s3,s4,s5,s6,s7;
    Spinner sp1,sp2;
    String DB_URL = "jdbc:mysql://";
    String user = "";
    String pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_crce_reg);
        e1 = (EditText)findViewById(R.id.ncname);
        e2 = (EditText)findViewById(R.id.nccolname);
        e3 = (EditText)findViewById(R.id.ncphone);
        e4 = (EditText)findViewById(R.id.ncuser);
        e5 = (EditText)findViewById(R.id.ncpass);
        sp1 = (Spinner)findViewById(R.id.ncS1);
        sp2 = (Spinner)findViewById(R.id.ncS2);
        Button b = (Button)findViewById(R.id.ncsubmit);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        findViewById(R.id.ncsubmit).setEnabled(false);
        NonRegCres n = new NonRegCres();
        n.execute();
        finish();
        startActivity(new Intent(this,Registration.class));

    }
    private class NonRegCres extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(NonCrceReg.this);
        @Override
        protected void onPreExecute() {
            // what to do before background task
            dialog.setTitle("Loading...");
            dialog.setMessage("Please wait.");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }
        @Override
        public String doInBackground(String... params) {
            String rt = "/";int bal=0,newbal=0;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DB_URL, user, pass);
                if (con == null) System.out.println("Con error");
                Statement st = con.createStatement();
                System.out.println("Connection Successful");
                s1 = e1.getText().toString();
                s2 = e2.getText().toString();
                s3 = e3.getText().toString();
                s4 = e4.getText().toString();
                s5 = e5.getText().toString();
                s6 = sp1.getSelectedItem().toString();
                s7 = sp2.getSelectedItem().toString();
                st.executeUpdate("insert into NONCRCEDATA values('"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"','"+s7+"')");
                System.out.println("*****\nQuery Executed\n*****");
                st.executeUpdate("insert into Payments values('"+s4+"','"+s5+"','0')");
                System.out.println("*****\nQuery Executed\n*****");
                createNotification(1,"Successful","NON-CRCE USER with USER ID "+s4+" was created");
                createNotification(2,"Successful","Please Recharge "+s4);
                con.close();
            } catch (Exception e) {
                createNotification(1,"Error","Duplicate User");
                e.printStackTrace();
            }
            return null;
        }

    }
    private void createNotification(int NOTIFICATION_ID ,String contentTitle, String contentText) {
        Context mContext=this;

        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Build the notification using Notification.Builder
        Notification.Builder builder = new Notification.Builder(mContext)
                .setSmallIcon(R.drawable.ic_message)
                .setAutoCancel(true)
                .setContentTitle(contentTitle)
                .setContentText(contentText);


        //Show the notification
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
