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
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText e2;
    Spinner s;
    String DB_URL = "jdbc:mysql://";
    String user = "";
    String pass = "";
    String user1,pass1,pass2;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        s = (Spinner)findViewById(R.id.loginsp);
        e2 = (EditText)findViewById(R.id.passlogin);
        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        findViewById(R.id.button).setEnabled(false);
        SendFT obj = new SendFT();
        obj.execute();
    }

    private class SendFT extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(Login.this);
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
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DB_URL, user, pass);
                if (con == null) System.out.println("Con error");
                Statement st = con.createStatement();
                System.out.println("Connection Successful");
                user1 = s.getSelectedItem().toString();
                pass1 = e2.getText().toString();
                ResultSet rs = st.executeQuery("Select pass,type from USERS where id='"+user1+"'");
                while(rs.next()) {
                    pass2 = rs.getString(1);
                    type = Integer.parseInt(rs.getString(2));
                }if(pass1.equals(pass2))
                {
                    ActivityCall(type);
                    createNotification("Successful Login",""+user1+" Is logged in!!");
                }
                else
                {
                    createNotification("Error","Wrong password");
                }
                con.close();
                finish();
            } catch (Exception e) {
                ActivityCall(4);
                e.printStackTrace();
            }
            return null;
        }

    }
    public void ActivityCall(int type)
    {
        switch(type)
        {
            case 1:
                startActivity(new Intent(this, Registration.class));
                break;
            case 2:
                startActivity(new Intent(this, CouncilMain.class).putExtra("user",s.getSelectedItem().toString()));
                break;
            case 3:
                createNotification("Error","Wrong Password");
                finish();
                startActivity(new Intent(this, Login.class));
                break;
            case 4:
                createNotification("Error","No Connection");
                finish();
                startActivity(new Intent(this, Login.class));
                break;
        }
    }
    private void createNotification(String contentTitle, String contentText) {
        Context mContext=this;
        int NOTIFICATION_ID = 1;
        Notification mNotification;
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
