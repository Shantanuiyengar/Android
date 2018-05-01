package com.shantanu.fitmantra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText t1, t2;
    String DB_URL = "jdbc:mysql://fitness.c1u1zgabggen.us-east-1.rds.amazonaws.com/fitness";
    String user = "fitness";
    String pass = "fitness1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        t1 = findViewById(R.id.susername);
        t2 = findViewById(R.id.password);
        Button b1 = findViewById(R.id.login);
        Button b2 = findViewById(R.id.signup);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                LoginCheck l = new LoginCheck();
                l.execute();
                break;
            case R.id.signup:
                startActivity(new Intent(this, SignUp.class));
        }
    }

    private class LoginCheck extends AsyncTask<String, String, String> {
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
                String user1 = t1.getText().toString();
                String pass1 = t2.getText().toString();
                ResultSet rs = st.executeQuery("Select pass from USERS where name ='" + user1 + "'");
                String pass2 = null;
                while (rs.next()) {
                    pass2 = rs.getString(1);
                }
                if (pass1.equals(pass2)) {
                    ActivityCall(1);
                    createNotification("" + user1 + " Is logged in!!");
                } else {
                    createNotification("Wrong password");
                }
                con.close();
                finish();
            } catch (Exception e) {
                ActivityCall(4);
                e.printStackTrace();
            }
            dialog.dismiss();
            return null;
        }

    }

    public void ActivityCall(int type) {
        switch (type) {
            case 1:
                startActivity(new Intent(Login.this, CardioFitness.class));
                break;
            case 2:
                createNotification("Wrong Password");
                finish();
                startActivity(new Intent(this, Login.class));
                break;
            case 3:
                createNotification("No Connection");
                finish();
                startActivity(new Intent(this, Login.class));
                break;
        }
    }
    private void createNotification(String contentText) {
        Toast.makeText(this,contentText,Toast.LENGTH_LONG).show();
    }

}
