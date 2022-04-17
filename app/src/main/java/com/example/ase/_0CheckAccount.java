package com.example.ase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class _0CheckAccount extends AppCompatActivity {
    public static SharedPreferences sharedPreferences;
    public CircleImageView profileImage;
    public TextView welcomeText;
    public BootstrapButton continueButton,loginButton;
    public ProgressDialog progressDialog;
    public  static  User currentUser;

    String mail="",Id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0_check_account);
        progressDialog = new ProgressDialog(_0CheckAccount.this);
        DefineMirrorObject();
        sharedPreferences=this.getSharedPreferences("com.example.ase", Context.MODE_PRIVATE);

        //sharedPreferences.edit().putString("Email","mumail").apply();
        showIndeterminateProgressDialog();
        if(CheckIfAccountExists()){

            LoadMyAccountInfo();
        }else{
            MoveToLogin();
        }
    }

    public boolean CheckIfAccountExists(){

        mail=sharedPreferences.getString("Email","NoEmail");

        if(mail.equals("NoEmail")){
            //forward the user to login / create Account Screen

            return false;
        }else{
            Id=sharedPreferences.getString("Id","NoId");
            return true;
        }
    }
    public void MoveToLogin(){
        Intent moveToLoginScreen=new Intent(getApplicationContext(),_6Login.class);
        startActivity(moveToLoginScreen);
    }
    public void MoveToLogin(View view){
        Intent moveToLoginScreen=new Intent(getApplicationContext(),_6Login.class);
        startActivity(moveToLoginScreen);
    }

    public void LoadMyAccountInfo(){
        showIndeterminateProgressDialog();
        //Fetch User Info By Object Id
        FirebaseApp.initializeApp(_0CheckAccount.this);
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference().child("User");

        final Query AccountInfoQuery = DbRef.orderByChild("Email").equalTo(mail);
        AccountInfoQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()) {
                    User fetchedItem = user.getValue(User.class);
                    if (fetchedItem.Email.equals(mail)) {
                        currentUser=fetchedItem;
                        progressDialog.hide();
                        GetWelcomeMassageDependOnDate();
                        if(currentUser.ProfileImagePath.equals("")){
                            profileImage.setImageResource(R.drawable.man);
                        }else{
                            Glide.with(getApplicationContext()).load(currentUser.ProfileImagePath).into(profileImage);
                        }

                        continueButton.setText("Continue As "+currentUser.Name);

                    }
                }
                progressDialog.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.hide();
                Toast.makeText(_0CheckAccount.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void DefineMirrorObject(){
        profileImage=findViewById(R.id.userImage);
        welcomeText=findViewById(R.id.welcomeText);
        continueButton=findViewById(R.id.b1);
        loginButton=findViewById(R.id.b2);
    }

    public void ContinueToMyScreen(View view) {
        Intent moving=new Intent(_0CheckAccount.this,MainScreen.class);
        startActivity(moving);
    }
    private void showIndeterminateProgressDialog()
    {



                // Set horizontal progress bar style.
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                // Set progress dialog icon.
                progressDialog.setIcon(R.drawable.clock);
                // Set progress dialog title.
                progressDialog.setTitle("Waiting For Task Complete...");
                // Whether progress dialog can be canceled or not.
                progressDialog.setCancelable(false);
                // When user touch area outside progress dialog whether the progress dialog will be canceled or not.
                progressDialog.setCanceledOnTouchOutside(false);


                // Set progress dialog message.
                progressDialog.setMessage("This is a circle progress dialog.\r\nTask will be completed in 3 seconds.");

                // Popup the progress dialog.
                progressDialog.show();

                // Create a new thread object.
            }

    public void GetWelcomeMassageDependOnDate(){
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            welcomeText.setText("Good Morning");

        }else if(timeOfDay >= 12 && timeOfDay < 16){
            welcomeText.setText("Good Afternoon");

        }else if(timeOfDay >= 16 && timeOfDay < 21){
            welcomeText.setText("Good Evening");

        }else if(timeOfDay >= 21 && timeOfDay < 24){
            welcomeText.setText("Good Night");

        }
    }
}