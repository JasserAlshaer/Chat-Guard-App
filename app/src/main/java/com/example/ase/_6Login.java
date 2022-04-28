package com.example.ase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class _6Login extends AppCompatActivity {
    public TextView emailLabel,passwordLabel,signupLabel;
    public BootstrapEditText emailField,passwordField;
    private FirebaseAuth mAuth;
    CheckBox admin_Login_Option,driverOption;
    public static vehicle currentVehicle;

    public  static Center currentCenter;
    public static String centerId="",vehicleId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6_login);
        DefineObject();
        currentCenter=new Center();
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
            //Continue Login Operation
            if(admin_Login_Option.isChecked()){
                FirebaseApp.initializeApp(_6Login.this);
                DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference().child("Center");

                final Query AccountInfoQuery = DbRef.orderByChild("AdminEmail").equalTo(userEmail);
                AccountInfoQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot center : snapshot.getChildren()) {
                            Center fetchedItem = center.getValue(Center.class);
                            if (fetchedItem.AdminEmail.equals(userEmail)
                            && fetchedItem.AdminPassword.equals(userPassword)
                            ) {
                                currentCenter=fetchedItem;
                                centerId=center.getKey();

                                Intent moveToCenterScreen=new Intent(_6Login.this,_7LatestReports.class);
                                startActivity(moveToCenterScreen);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(_6Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            else if(driverOption.isChecked()){
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference ref = database.child("Vehicle");


                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (DataSnapshot childnow: dataSnapshot.getChildren()) {
                                vehicle fetched=childnow.getValue(vehicle.class);
                                if (fetched.DriverEmail.equals(userEmail)
                                        && fetched.DriverPassword.equals(userPassword)
                                ) {
                                    currentVehicle=fetched;
                                    vehicleId=childnow.getKey();

                                    Intent moveToCenterScreen=new Intent(_6Login.this,_12AssignedMissoin.class);
                                    startActivity(moveToCenterScreen);
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Null", "onCancelled", databaseError.toException());
                    }
                });
            }
            else{
                mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        _0CheckAccount.sharedPreferences.edit().putString("Email",userEmail).apply();
                                        MoveToMainScreen();
                                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(getApplicationContext(), "Please Verify Email", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i("Data", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }

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
        Intent main=new Intent(_6Login.this,_0CheckAccount.class);
        startActivity(main);
    }
}