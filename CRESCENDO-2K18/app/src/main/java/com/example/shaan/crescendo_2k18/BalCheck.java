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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BalCheck extends AppCompatActivity implements View.OnClickListener {
    EditText e1;
    Button b;
    String DB_URL = "jdbc:mysql://";
    String user = "";
    String pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bal_check);
        e1 = (EditText)findViewById(R.id.bcuser);
        b = (Button)findViewById(R.id.bcbutton);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        findViewById(R.id.bcbutton).setEnabled(false);
        Balch obj = new Balch();
        obj.execute();
        finish();
        startActivity(new Intent(this,Registration.class));
    }
    private class Balch extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(BalCheck.this);
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
                String user1 = e1.getText().toString();
                ResultSet rs = st.executeQuery("Select bal from Payments where user = '"+user1+"'");
                while(rs.next())
                {
                    bal = Integer.parseInt(rs.getString(1));
                }
                    createNotification("Successful","Balance for "+user1+" is "+bal);
                con.close();
            } catch (Exception e) {
                createNotification("Error","No Connection");
                e.printStackTrace();
            }
            return null;
        }

    }
    private void createNotification(String contentTitle, String contentText) {
        Context mContext=this;
        int NOTIFICATION_ID = 1;
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

