package com.example.ase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainScreen extends AppCompatActivity  implements LocationListener {
    public DrawerLayout drawerMenuForUser;
    public static double lat,lon;
    public LocationManager myLocationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        drawerMenuForUser=findViewById(R.id.drawerMainScreenForUser);

        if(ContextCompat.checkSelfPermission(MainScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainScreen.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }else{
            getLocation();
        }


    }




    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            myLocationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,MainScreen.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Toast.makeText(MainScreen.this, location.getLatitude()+""+location.getLongitude(), Toast.LENGTH_SHORT).show();
        lat=location.getLatitude();
        lon=location.getLongitude();
        try {
            Geocoder myGeocoder=new Geocoder(MainScreen.this, Locale.getDefault());
            List<Address> addressList=myGeocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String currentAddress=addressList.get(0).getAddressLine(0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public void MoveToCreateReport(View view) {
        getLocation();
        Intent moving=new Intent(MainScreen.this,_2SendReport.class);
        startActivity(moving);
    }

    public void Logout(View view) {
        getLocation();
        Intent moving=new Intent(MainScreen.this,_0CheckAccount.class);
        startActivity(moving);
    }

    public void EditProfile(View view) {
        Intent moving=new Intent(MainScreen.this,_14UserProfile.class);
        startActivity(moving);
    }

    public void Tracking(View view) {
        Intent moving=new Intent(MainScreen.this,_15AboutUs.class);
        startActivity(moving);
    }

    public void onMenuClicked(View view) {
        openDrawer(drawerMenuForUser);
    }
    //This method do the opening drawer operation
    public static void openDrawer(DrawerLayout draw) {
        draw.openDrawer(GravityCompat.START);
    }
    //This method do the closing drawer operation
    public static void closeDrawer(DrawerLayout draw) {
        if(draw.isDrawerOpen(GravityCompat.START)){
            draw.closeDrawer(GravityCompat.START);
        }
    }
    public void MoveToFirstAds(View view){
        Intent moving=new Intent(MainScreen.this,_3FirstAid.class);
        startActivity(moving);
    }
    public void BackToHome(View view){
        Intent moving=new Intent(getApplicationContext(),MainScreen.class);
        startActivity(moving);
    }


    public void Center(View view){
        getLocation();
        Intent moving=new Intent(MainScreen.this,_4NearestCenter.class);
        startActivity(moving);

    }

    public void Numbers(View view){
        Intent moving=new Intent(MainScreen.this,_13Numbers.class);
        startActivity(moving);
    }
}