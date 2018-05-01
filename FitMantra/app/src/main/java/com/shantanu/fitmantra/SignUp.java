package com.shantanu.fitmantra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2,e3;
    String DB_URL = "jdbc:mysql://fitness.c1u1zgabggen.us-east-1.rds.amazonaws.com/fitness";
    String user = "fitness";
    String pass = "fitness1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        e1 = findViewById(R.id.spassword);
        e2 = findViewById(R.id.spassword2);
        e3 = findViewById(R.id.susername);
        if((e1.getText().toString()).equals(e2.getText().toString()))
        {
            InsertUser iu = new InsertUser();
            iu.execute();

        }
        else
            Toast.makeText(this,"Password don't match, Enter matching password",Toast.LENGTH_LONG).show();
    }

    private class InsertUser extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(SignUp.this);

        @Override
        protected void onPreExecute() {
            // what to do before background task
            dialog.setTitle("Loading...");
            dialog.setMessage("CREATING USER");
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
                String user1 = e3.getText().toString();
                String pass1 = e2.getText().toString();
                st.execute("Insert into USERS values('"+user1+"','"+pass1+"')");
                con.close();
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
            return null;
        }

    }
}