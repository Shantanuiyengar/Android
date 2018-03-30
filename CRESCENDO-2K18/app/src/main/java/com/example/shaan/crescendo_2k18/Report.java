package com.example.shaan.crescendo_2k18;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Report extends AppCompatActivity {
    String DB_URL = "jdbc:mysql://";
    String user = "";
    String pass = "";
    String userlogin = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        userlogin = getIntent().getExtras().getString("user");
        Send obj = new Send();
        obj.execute();
        obj.onPostExecute(null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public void Print(String s1, String s2, String s3, String s4,String s5) {
        try {
            TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
            View tableRow = LayoutInflater.from(this).inflate(R.layout.table, null, false);
            TextView t1 = (TextView) tableRow.findViewById(R.id.t1);
            TextView t2 = (TextView) tableRow.findViewById(R.id.t2);
            TextView t3 = (TextView) tableRow.findViewById(R.id.t3);
            TextView t4 = (TextView) tableRow.findViewById(R.id.t4);
            TextView t5 = (TextView) tableRow.findViewById(R.id.t5);
            t1.setText(s1);
            t2.setText(s2);
            t3.setText(s3);
            t4.setText(s4);
            t5.setText(s5);
            tableLayout.addView(tableRow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Send extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(Report.this);
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
        protected void onPostExecute(String result) {
            // what to do when background task is completed
            dialog.dismiss();
        }
        @Override
        public String doInBackground(String... params) {
            while (true) {
                String rt = "/";int count=0;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(DB_URL, user, pass);
                    if (con == null) System.out.println("Con error");
                    Statement st = con.createStatement();
                    System.out.println("Connection Successful");
                    st.executeUpdate("DELETE FROM EVENTS WHERE GAME = 'Select Game' ");
                    ResultSet rs = st.executeQuery("SELECT RollNo,Year,Branch,GAME,COST FROM CRCEDATA natural join EVENTS where EVENTS.User=CRCEDATA.Rollno and COUNCILNAME='"+userlogin+"';");
                    while (rs.next()) {
                       try{ Print(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5));}
                       catch (Exception e){
                           createNotificationf("Loading","Generating Report");
                       }
                        count++;
                    }
                    rs = st.executeQuery("SELECT User,Year,Branch,GAME,COST FROM NONCRCEDATA natural join EVENTS where EVENTS.User = NONCRCEDATA.User and COUNCILNAME='"+userlogin+"';");
                    while (rs.next()) {
                        try{ Print(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5));}
                        catch (Exception e){
                            createNotificationf("Loading","Generating Report");
                        }
                        count++;
                    }
                    System.out.println("*****\nQuery Executed\n*****");
                    createNotification("CouncilApp", "Total Players till now are "+count);
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    createNotificationf("Error","No Internet...Please check your connection and refresh again");
                }
                System.out.println(rt);
                return rt;
            }

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
                .setSmallIcon(android.R.drawable.btn_radio)
                .setAutoCancel(true)
                .setContentTitle(contentTitle)
                .setContentText(contentText);
        //Show the notification
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    private void createNotificationf(String contentTitle, String contentText) {
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
        Send obj = new Send();
        obj.execute();
        //Show the notification
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}