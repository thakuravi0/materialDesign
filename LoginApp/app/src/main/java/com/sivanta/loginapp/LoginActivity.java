package com.sivanta.loginapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    public static final String Default="N/A";
    TextView editTextEmail,editTextPassword;
    Button loginbtn,signbtn;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail= (TextView) findViewById(R.id.editTextEmail);
        editTextPassword= (TextView) findViewById(R.id.editTextPassword);
        loginbtn= (Button) findViewById(R.id.loginbtn);
        signbtn= (Button) findViewById(R.id.signbtn);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
       // SharedPreferences sharedPreferences=getSharedPreferences("loginprefs", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences=getSharedPreferences("mydata", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        final String username=sharedPreferences.getString("email",Default);
        final String password =sharedPreferences.getString("password",Default);
        boolean saveLogin=sharedPreferences.getBoolean("saveLogin",false);
        if (saveLogin==true)
        {
            Log.d("box","box is checked");
            editTextEmail.setText(username);
            editTextPassword.setText(password);
        }
       else
        {
          //  Toast.makeText(this,"unchecked box",Toast.LENGTH_SHORT).show();
            Log.d("box","box is unchecked");
            editTextEmail.setText(sharedPreferences.getString("email",""));
            editTextPassword.setText("");
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // String username = "avi2191@gmail.com";
               // String password = "12345";
                String enemail=editTextEmail.getText().toString();
                String enpassword=editTextPassword.getText().toString();
                if (enemail.equals(username)&&enpassword.equals(password))
                {

                    if (checkBox.isChecked()==true)
                    {
                        editor.putBoolean("saveLogin", true);
                        editor.putString("email", username);
                        editor.putString("password", password);
                        editor.commit();
                    }
                    else
                    {
                        editor.clear();
                        editor.commit();
                    }

                    Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                    startActivity(intent);
                }
                else if ((!enemail.equals(username)) && enpassword.equals(password))
                {
                    editTextEmail.setError("invalid email id");
                }
                else if (enemail.equals(username) &&(!enpassword.equals(password)))
                {
                    editTextPassword.setError("invalid password");
                }
                else if ((!enemail.equals(username)) && (!enpassword.equals(password)))
                {
                    editTextEmail.setError("invalid email id");
                    editTextPassword.setError("invalid password");
                }
               else if (enemail.equals("")&&enpassword.equals(""))
                {
                    editTextEmail.setError("enter email id");
                    editTextPassword.setError("enter password");
                }
                else if (enemail.equals("")&& (!enpassword.equals("")))
                {
                    editTextEmail.setError("enter email id");

                }
                else if ((!enemail.equals(""))&& enpassword.equals(""))
                {
                   editTextPassword.setError("enter password");

                }
            }
        });
    }
    public void signUp(View view)
    {
        Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}
