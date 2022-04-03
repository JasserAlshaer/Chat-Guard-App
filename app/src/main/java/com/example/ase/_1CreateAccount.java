package com.example.ase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class _1CreateAccount extends AppCompatActivity {
    public BootstrapEditText userName,userEmail,userPhone,userSSN;
    public FirebaseAuth mAuth;

    public DatabaseReference databaseReference;
    public ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_create_account);
        DefineAllScreenObject();

    }

    public void OnContinueSignUpButtonClicked(View view) {
        FirebaseApp.initializeApp(_1CreateAccount.this);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String name,email,phone,ssn;
        mAuth = FirebaseAuth.getInstance();
        name=userName.getText().toString();
        email=userEmail.getText().toString();
        phone=userPhone.getText().toString();
        ssn=userSSN.getText().toString();

        if(name.equals("")||email.equals("")||phone.equals("")||ssn.equals("")||ssn.length()<10){
            Toast.makeText(getApplicationContext(), "Please Fill The Required Data", Toast.LENGTH_SHORT).show();
        }else{
                showIndeterminateProgressDialog();
              User person=new User(name,email,phone,0.0,0.0,ssn,false,"");
              databaseReference.child("User").push().setValue(person);
              progressDialog.hide();
              Toast.makeText(getApplicationContext(), "Please Verify Your email", Toast.LENGTH_SHORT).show();
              finish();
            }
        }

    public void DefineAllScreenObject() {
        userName=findViewById(R.id.newUserNameField);
        userPhone=findViewById(R.id.newUserPhoneField);
        userEmail=findViewById(R.id.newUserEmailField);
        userSSN=findViewById(R.id.newUserPasswordField);
    }
    private void showIndeterminateProgressDialog()
    {

         progressDialog = new ProgressDialog(_1CreateAccount.this);

        // Set horizontal progress bar style.
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // Set progress dialog icon.
        progressDialog.setIcon(R.drawable.clock);
        // Set progress dialog title.
        progressDialog.setTitle("Waiting");
        // Whether progress dialog can be canceled or not.
        progressDialog.setCancelable(false);
        // When user touch area outside progress dialog whether the progress dialog will be canceled or not.
        progressDialog.setCanceledOnTouchOutside(false);


        // Set progress dialog message.
        progressDialog.setMessage("Waiting For Complete Register...");

        // Popup the progress dialog.
        progressDialog.show();

        // Create a new thread object.
    }
}