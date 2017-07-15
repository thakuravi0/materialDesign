package com.sivanta.loginapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText input_fname,input_lname,input_email,input_password;
    TextView link_login;
    Button btn_signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        input_fname= (EditText) findViewById(R.id.input_fname);
        input_lname= (EditText) findViewById(R.id.input_lname);
        input_email= (EditText) findViewById(R.id.input_email);
        link_login= (TextView) findViewById(R.id.link_login);
        input_password= (EditText) findViewById(R.id.input_password);
        btn_signup= (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btn_signup.setEnabled(false);
        final ProgressDialog progressDialog=new ProgressDialog(SignUpActivity.this,R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account.....");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onSignupSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    public void onSignupSuccess() {
        btn_signup.setEnabled(true);
        SharedPreferences sharedPreferences=getSharedPreferences("mydata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("fname",input_fname.getText().toString());
        editor.putString("lname",input_lname.getText().toString());
        editor.putString("email",input_email.getText().toString());
        editor.putString("password",input_password.getText().toString());
        editor.commit();
        Toast.makeText(this,"Data has successfully saved",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btn_signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

       String fname = input_fname.getText().toString();
       String lname = input_lname.getText().toString();
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (fname.isEmpty() || fname.length() < 3) {
            input_fname.setError("at least 3 characters");
            valid = false;
        } else {
            input_fname.setError(null);
        }
        if (lname.isEmpty() || lname.length() < 3) {
            input_lname.setError("at least 3 characters");
            valid = false;
        } else {
            input_lname.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("enter a valid email address");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }
}
