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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CouncilMain extends AppCompatActivity implements View.OnClickListener {
    TextView t1;EditText e1,e2;int GameCost;
    String[] glist1 = new String[]{"Select Game"};
    Spinner s1;
    String DB_URL = "jdbc:mysql://cres2018.c1u1zgabggen.us-east-1.rds.amazonaws.com/cres2018";
    String user = "";
    String pass = "";
    int part = 0;
    String userlogin = null;
    List<String> glist2 = null;
    ArrayAdapter<String> spinnerArrayAdapter1 = null;
    ArrayAdapter<String> spinnerArrayAdapter2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_council_main);
        userlogin = getIntent().getExtras().getString("user");
        t1 = (TextView)findViewById(R.id.textView5);
        t1.setText("Council:- "+userlogin);
        e1 = (EditText)findViewById(R.id.cmuser1);
        e2 = (EditText)findViewById(R.id.cmpass1);
        s1 = (Spinner)findViewById(R.id.cmsp);
        // Initializing an ArrayAdapter
        glist2 = new ArrayList<>(Arrays.asList(glist1));
        spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this,R.layout.spinner_item,glist2);
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_item);
        s1.setAdapter(spinnerArrayAdapter1);
        AddInSpinner(userlogin);
        Button b = (Button)findViewById(R.id.cmsubmit);
        b.setOnClickListener(this);
        Button b1 = (Button)findViewById(R.id.cmreport);
        b1.setOnClickListener(this);

    }
    private void AddInSpinner(String user)
    {
        switch (user)
        {
            case "ASME":
                glist2.add("Technical Quiz");
                glist2.add("Suspension Bridge");
                part = 5;
                break;
            case "IEEE":
                glist2.add("Project Competition (Members)");
                glist2.add("Project Competition (Non-Members)");
                part = 20;
                break;
            case "WIE":
                glist2.add("Cansat Workshop (ind)");
                glist2.add("Cansat Workshop (Team of 5)");
                break;
            case "CSI":
                glist2.add("Counterstrike");//part in gameselect
                glist2.add("Game of codes");
                break;
            case "SME":
                glist2.add("Lathe Wars");
                glist2.add("CAD Wars");
                part = 5;
                break;
            case "ITSA":
                glist2.add("Technical Debate (CRCE)");
                glist2.add("Technical Debate (NON-CRCE)");
                glist2.add("Housie Coding");
                part = 5;
                break;
            case "SAE":
                glist2.add("Angry Birds");
                glist2.add("Taboo");
                part = 5;
                break;
            case "CODELABS":
                glist2.add("Treasure Hunt");
                glist2.add("Algoholic (Ind)");
                glist2.add("Algoholic (Team)");
                break;
            case "MOZILLA":
                glist2.add("PHP workshop");
                glist2.add("Typing contest");//part in gameselect
                break;
            case "IIIE":
                glist2.add("Rube Goldberg Machine design (Ind.)");
                glist2.add("Rube Goldberg Machine design (Team)");
                glist2.add("Tech Talk");
                break;

            case "NSS":
                glist2.add("Paper Bag Making Machine");
                part = 5;
                break;
            case "ECELL":
                glist2.add("Home Automation");
                glist2.add("Gesture Robotics");
                part = 5;
                break;
            case "TEDX":
                glist2.add("Python Seminar");
                glist2.add("Minute to minute game");//part in gameselect
                break;
            case "LITCLUB":
                glist2.add("Squiggly Movie Plot");
                glist2.add("Word Blitz");
                part = 3;
                break;
            case "STUCO":
                glist2.add("RoboSumo");
                part = 5;
                break;
            case "PCELL":
                glist2.add("Defuse the Bomb");
                part = 5;

        }spinnerArrayAdapter1.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cmsubmit:
                findViewById(R.id.cmsubmit).setEnabled(false);
                CMSUBMIT cm = new CMSUBMIT();
                cm.execute(e1.getText().toString(), e2.getText().toString(), s1.getSelectedItem().toString());
                break;
            case R.id.cmreport:
                ProgressDialog dialog = new ProgressDialog(CouncilMain.this);
                onPreExecute(dialog);
                startActivity(new Intent(this,Report.class).putExtra("user",userlogin));

                onPostExecute(dialog);
                break;
        }
    }

    private class CMSUBMIT extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(CouncilMain.this);
        @Override
        protected void onPreExecute() {
            // what to do before background task
            dialog.setTitle("Loading...");
            dialog.setMessage("Please wait.");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }
        ResultSet rs;
        Statement st;
        @Override
        public String doInBackground(String... params) {
           String passcheck = null;
            int mbal = 0, newbal = 0,cpart=0,newpart=0;
            String uyear=null,ubranch=null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DB_URL, user, pass);
                if (con == null) System.out.println("Con error");
                st = con.createStatement();
                System.out.println("Select bal,pass from Payments where user = '" + params[0] + "'");
                rs = st.executeQuery("Select bal,pass from Payments where user = '" + params[0] + "'");
                while(rs.next())
                {
                    mbal = Integer.parseInt(rs.getString(1));
                    passcheck = rs.getString(2);
                }
                GameCost = CostOfGame();
                newbal = mbal - GameCost;
                System.out.println(newbal);
                if(params[1].equals(passcheck))
                {   System.out.println("Password correct");
                    if(params[0].length()== 4) {
                        System.out.println(params[0].length());
                        if (newbal >= 0)
                        {   System.out.println("Update Payments Set bal = '" + newbal + "' where user = '"+params[0]+"'");
                            st.executeUpdate("Update Payments Set bal = '" + newbal + "' where user = '"+params[0]+"'");
                            System.out.println("Insert into EVENTS values('"+params[0]+"','"+params[2]+"','"+userlogin+"','"+GameCost+"')");
                            st.executeUpdate("Insert into EVENTS values('"+params[0]+"','"+params[2]+"','"+userlogin+"','"+GameCost+"')");
                            System.out.println("Select Year,Branch from CRCEDATA where rollno = '" + params[0] + "'");
                            rs = st.executeQuery("Select Year,Branch from CRCEDATA where rollno = '" + params[0] + "'");
                            System.out.println("Select Year,Branch from CRCEDATA where rollno = '" + params[0] + "'");
                            while (rs.next()) {
                                uyear = rs.getString(1);
                                ubranch = rs.getString(2);
                            }
                            System.out.println("Select tpoints from POINTS where class = '" + uyear + "' and branch = '" + ubranch + "'");
                            rs = st.executeQuery("Select tpoints from POINTS where class = '" + uyear + "' and branch = '" + ubranch + "'");
                            while (rs.next()) {
                                cpart = Integer.parseInt(rs.getString(1));
                            }
                            newpart = cpart + part;
                            System.out.println("Update POINTS Set tpoints = '" + newpart + "' where class = '" + uyear + "' and branch = '" + ubranch + "'");
                            st.executeUpdate("Update POINTS Set tpoints = '" + newpart + "' where class = '" + uyear + "' and branch = '" + ubranch + "'");
                            System.out.println("*****\nQuery Executed\n*****");
                            newpart = 0;
                            if(GameCost!=0)
                                createNotification("Successfull", "Payment of "+GameCost+" was successful");
                            else
                                createNotification("Error","Please select game!");
                        }
                        else
                        {
                            createNotification("Unsuccessful", params[0] + " has low balance");
                        }
                    }
                    else
                    {
                        if (newbal >= 0)
                        {   System.out.println("Insert into EVENTS values('"+params[0]+"','"+params[2]+"','"+userlogin+"','"+GameCost+"')");
                            st.executeUpdate("Insert into EVENTS values('"+params[0]+"','"+params[2]+"','"+userlogin+"','"+GameCost+"')");
                            System.out.println("Update Payments Set bal = '" + newbal + "' where user = '" + params[0] + "'");
                            st.executeUpdate("Update Payments Set bal = '" + newbal + "' where user = '" + params[0] + "'");
                            if(GameCost!=0)
                            createNotification("Successfull", "Payment of "+GameCost+" was successful");
                            else
                                createNotification("Error","Please select game!");
                        }
                        else
                        {
                            createNotification("Unsuccessful", params[0] + " has low balance");
                        }
                    }
                }
                else
                {
                    createNotification("WRONG PASSWORD","The password entered is wrong");
                }
                con.close();
            } catch (Exception e) {
            }

            return null;
        }

    }
    public int CostOfGame()
    {
        String game = s1.getSelectedItem().toString();
        switch(game)
        {
            case "Technical Quiz":
                GameCost = 30;
                break;
            case "Suspension Bridge":
                GameCost = 40;
                break;
            case "Project Competition (Members)":
                GameCost = 200;
                break;
            case "Project Competition (Non-Members)":
                GameCost = 300;
                break;
            case "Cansat Workshop (ind)":
                GameCost = 1200;
                part = 5;
                break;
            case "Cansat Workshop (Team of 5)":
                GameCost = 5000;
                part = 25;
                break;
            case "Counterstrike":
                GameCost = 20;
                part = 3;
                break;
            case "Game of codes":
                GameCost = 40;
                part = 5;
                break;
            case "Lathe Wars":
                GameCost = 100;
                break;
            case "CAD Wars":
                GameCost = 40;
                break;
            case "Technical Debate (CRCE)":
                GameCost = 0;
                break;
            case "Technical Debate (NON-CRCE)":
                GameCost = 300;
                break;
            case "Housie Coding":
                GameCost = 60;
                break;
            case "Angry Birds":
                GameCost = 30;
                break;
            case "Taboo":
                GameCost = 30;
                break;
            case "Treasure Hunt":
                part=5;
                GameCost = 40;
                break;
            case "Algoholic (Ind)":
                part = 5;
                GameCost = 40;
                break;
            case "Algoholic (Team)":
                part=10;
                GameCost = 70;
                break;
            case "PHP workshop":
                GameCost = 250;
                part = 5;
                break;
            case "Typing contest":
                GameCost = 10;
                part = 3;
                break;
            case "Rube Goldberg Machine design (Ind.)":
                GameCost = 30;
                part = 3;
                break;
            case "Rube Goldberg Machine design (Team)":
                GameCost = 40;
                part = 3;
                break;
            case "Tech Talk":
                GameCost = 50;
                part = 5;
                break;
            case "Paper Bag Making Machine":
                GameCost = 30;
                break;
            case "Home Automation":
                GameCost = 5400;
                break;
            case "Gesture Robotics":
                GameCost = 4200;
                break;
            case "Python Seminar":
                GameCost = 175;
                part = 5;
                break;
            case "Minute to minute game":
                GameCost = 50;
                part = 3;
                break;
            case "Squiggly Movie Plot":
                GameCost = 10;
                break;
            case "Word Blitz":
                GameCost = 10;
                break;
            case "RoboSumo":
                GameCost = 500;
                break;
            case "Defuse the Bomb":
                GameCost = 50;
                break;
            case "Select Game":
                GameCost = 0;
                createNotification("Error","Please Select a game!");
                break;
       }
        return GameCost;
    }
        private void createNotification(String contentTitle, String contentText) {
            Context mContext =this;
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
            finish();
            startActivity(new Intent(this,CouncilMain.class).putExtra("user",userlogin));
        }
    public void onPreExecute(ProgressDialog dialog) {// what to do before background task
        dialog.setTitle("Loading...");
        dialog.setMessage("Please wait.");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }
    public void onPostExecute(ProgressDialog dialog) {
        dialog.setMessage("Please wait.,\n If returned back, press anywhere on screen");
        dialog.setCancelable(true);
        dialog.show();
    }
}