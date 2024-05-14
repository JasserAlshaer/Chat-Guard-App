package com.example.ase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity  {
    public DrawerLayout drawerMenuForUser;
    public static double lat,lon;
    public LocationManager myLocationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        drawerMenuForUser=findViewById(R.id.drawerMainScreenForUser);
    }
    public void MoveToCreateReport(View view) {
        Toast.makeText(MainScreen.this, "Not Implemented Yet", Toast.LENGTH_SHORT).show();
        Intent moving=new Intent(MainScreen.this,_2SendReport.class);
        startActivity(moving);
    }

    public void Logout(View view) {
        Toast.makeText(MainScreen.this, "Not Implemented Yet", Toast.LENGTH_SHORT).show();
    }

    public void EditProfile(View view) {
        Toast.makeText(MainScreen.this, "Not Implemented Yet", Toast.LENGTH_SHORT).show();
        Intent moving=new Intent(MainScreen.this,_14UserProfile.class);
        startActivity(moving);
    }

    public void Tracking(View view) {
        Toast.makeText(MainScreen.this, "Not Implemented Yet", Toast.LENGTH_SHORT).show();
    }

    public void onMenuClicked(View view) {
        openDrawer(drawerMenuForUser);
    }
    //This method do the opening drawer operation
    public static void openDrawer(DrawerLayout draw) {
        draw.openDrawer(GravityCompat.START);
    }

    public void MoveToFirstAds(View view){
    }
    public void BackToHome(View view){
        Toast.makeText(MainScreen.this, "Not Implemented Yet", Toast.LENGTH_SHORT).show();
    }


    public void Center(View view){
        Toast.makeText(MainScreen.this, "Not Implemented Yet", Toast.LENGTH_SHORT).show();

    }

    public void Numbers(View view){
        Toast.makeText(MainScreen.this, "Not Implemented Yet", Toast.LENGTH_SHORT).show();
    }
}