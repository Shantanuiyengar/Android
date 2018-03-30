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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CrceReg extends AppCompatActivity implements View.OnClickListener{
    EditText e1,e2,e3;
    String DB_URL = "jdbc:mysql://";
    String user = "";
    String pass = "";
    String pass1,pass2,user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crce_reg);
        Button b = (Button)findViewById(R.id.Rechargeft);
        e1 = (EditText)findViewById(R.id.idft);
        e2 = (EditText)findViewById(R.id.pass1ft);
        e3 = (EditText)findViewById(R.id.pass2ft);
        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
                findViewById(R.id.Rechargeft).setEnabled(false);
                RadioGroup radioButtonGroup = (RadioGroup) findViewById(R.id.radioGrpft);
                int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                View radioButton = radioButtonGroup.findViewById(radioButtonID);
                int idx = radioButtonGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) radioButtonGroup.getChildAt(idx);
                String selectedtext = r.getText().toString();
                RegCres obj = new RegCres();
                obj.execute(selectedtext);
                finish();
                startActivity(new Intent(this,Registration.class));
    }
    private class RegCres extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(CrceReg.this);
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
                user1 = e1.getText().toString();
                pass1 = e2.getText().toString();
                pass2 = e3.getText().toString();
                if(pass1.equals(pass2))
                {
                    st.executeUpdate("insert into Payments values('"+user1+"','"+pass1+"','"+params[0]+"')");
                    System.out.println("*****\nQuery Executed\n*****");
                    st.executeUpdate("Insert into Recharge values ('"+params[0]+"','"+user1+"')");
                    createNotification("Successfull","Your recharge of "+ params[0] + " for "+user1+" was successful");
                }
                else
                {
                    createNotification("Error","Recharge UNsuccessful");
                }
                con.close();
            } catch (Exception e) {
                createNotification("Error","Duplicate User");
                e.printStackTrace();
            }
            return null;
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
