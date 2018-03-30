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

public class ClearAcc extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2;
    Button b;
    String DB_URL = "jdbc:mysql://";
    String user = "";
    String pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_acc);
        e1 = (EditText)findViewById(R.id.CAuser);
        e2 = (EditText)findViewById(R.id.CApass);
        b = (Button)findViewById(R.id.CAsubmit);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        findViewById(R.id.CAsubmit).setEnabled(false);
        ClAcc obj = new ClAcc();
        obj.execute();
        finish();
        startActivity(new Intent(this,Registration.class));
    }
    private class ClAcc extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(ClearAcc.this);
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
            int bal=0;
            String user1,pass1,pass2="asfasgsdfaefasfdasdfasdfthad";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DB_URL, user, pass);
                if (con == null) System.out.println("Con error");
                Statement st = con.createStatement();
                System.out.println("Connection Successful");
                user1 = e1.getText().toString();
                pass1 = e2.getText().toString();
                ResultSet rs = st.executeQuery("Select pass from Payments where user='" + user1 + "'");
                while (rs.next()) {
                    pass2 = rs.getString(1);
                }
                if (pass1.equals(pass2))
                {
                    rs = st.executeQuery("Select bal from Payments where user = '" + user1 + "'");
                    while (rs.next())
                    {
                        bal = Integer.parseInt(rs.getString(1));
                    }
                    st.executeUpdate("Update Payments set bal='0' where user = '" + user1 + "'");
                    createNotification(1,"Successful", "Account for "+user1+" was cleared");
                    createNotification(2,"Refund","Return back "+bal+"/- to the user :- "+user1);
                }
                con.close();
            }catch (Exception e) {
                createNotification(1,"Error","No Connection");
                e.printStackTrace();
            }
            return null;
        }

    }
    private void createNotification(int NOTIFICATION_ID,String contentTitle, String contentText) {
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

