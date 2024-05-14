package com.example.ase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.firebase.auth.FirebaseAuth;

public class _6Login extends AppCompatActivity {
    public TextView emailLabel,passwordLabel,signupLabel;
    public BootstrapEditText emailField,passwordField;
    private FirebaseAuth mAuth;
    CheckBox admin_Login_Option,driverOption;
    public static String centerId="",vehicleId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6_login);
        DefineObject();
        //currentCenter=new Center();
        admin_Login_Option=(CheckBox)findViewById(R.id.cheko);
        driverOption=(CheckBox)findViewById(R.id.driver);
    }

    public void OnSignupLabelClicked(View view) {
        Intent moveToCreateAccount=new Intent(_6Login.this,_1CreateAccount.class);
        startActivity(moveToCreateAccount);
    }

    public void OnLoginButtonClicked(View view) {
        //get the email and password then Authenticate user using firebase
        mAuth = FirebaseAuth.getInstance();
        String userEmail,userPassword;
        userEmail=emailField.getText().toString();
        userPassword=passwordField.getText().toString();
        if(userEmail.equals("")||userPassword.equals("")){
            Toast.makeText(getApplicationContext(), "Please Enter Email And SSN", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(_6Login.this, "Not Implemented Yet", Toast.LENGTH_SHORT).show();
        }
    }
    public void DefineObject(){
        emailField=findViewById(R.id.emailTextField);
        passwordField=findViewById(R.id.passwordTextField);
        emailLabel=findViewById(R.id.emailTextView);
        passwordLabel=findViewById(R.id.passwordTextView);
        signupLabel=findViewById(R.id.createAccountTextview);
    }
    public void MoveToMainScreen(){
        Intent main=new Intent(_6Login.this,MainScreen.class);
        startActivity(main);
    }
}