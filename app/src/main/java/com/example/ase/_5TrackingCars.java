package com.example.ase;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ase.databinding.Activity5TrackingCarsBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class _5TrackingCars extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Activity5TrackingCarsBinding binding;
    public LocationManager myLocationManager;
    public static double lat,lon;
    public static vehicle currentCars;
    public CountDownTimer trackingOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity5TrackingCarsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GetCarInfo(_9ActiveRepports.selectedReportToTrack.CarId);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(_9ActiveRepports.selectedReportToTrack.IsTrackingLiveLocation){
            if(ContextCompat.checkSelfPermission(_5TrackingCars.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(_5TrackingCars.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
            }else{
                getLocation();
                GetCarInfo(_9ActiveRepports.selectedReportToTrack.CarId);
            }
        }else{
            lat=_9ActiveRepports.selectedReportToTrack.Latitude;
            lon=_9ActiveRepports.selectedReportToTrack.Longitude;
            GetCarInfo(_9ActiveRepports.selectedReportToTrack.CarId);
        }

        long time =1000*60*30;
        long counter=1000*40;
        trackingOrder=new CountDownTimer(time,counter){

            @Override
            public void onTick(long millisUntilFinished) {
                if(_9ActiveRepports.selectedReportToTrack.IsTrackingLiveLocation){
                    getLocation();
                    GetCarInfo(_9ActiveRepports.selectedReportToTrack.CarId);
                }else{
                    lat=_9ActiveRepports.selectedReportToTrack.Latitude;
                    lon=_9ActiveRepports.selectedReportToTrack.Longitude;
                    GetCarInfo(_9ActiveRepports.selectedReportToTrack.CarId);
                }
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng userOrReportLocation = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(userOrReportLocation).title("User")
                .icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        getLocation();
        // Add a marker in Sydney and move the camera
       /* if(_9ActiveRepports.selectedReportToTrack.IsTrackingLiveLocation){
            getLocation();
        }else{
            LatLng userOrReportLocation = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(userOrReportLocation).title("User")
                    .icon(BitmapDescriptorFactory.
                            defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            LatLng CarLocation = new LatLng(currentCars.Latitude, currentCars.Longitude);
            mMap.addMarker(new MarkerOptions().position(CarLocation).title("Car").icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userOrReportLocation,15));
        }*/
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            myLocationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,_5TrackingCars.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        mMap.clear();
        if(_9ActiveRepports.selectedReportToTrack.IsTrackingLiveLocation){
            lat=location.getLatitude();
            lon=location.getLongitude();
        }else{
            lat=_9ActiveRepports.selectedReportToTrack.Latitude;
            lon=_9ActiveRepports.selectedReportToTrack.Longitude;
        }


        LatLng userOrReportLocation = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(userOrReportLocation).title("User")
                .icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        LatLng CarLocation = new LatLng(currentCars.Latitude, currentCars.Longitude);
        mMap.addMarker(new MarkerOptions().position(CarLocation).title("Car").icon(BitmapDescriptorFactory.
                defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userOrReportLocation,15));
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

    public void GetCarInfo(String CarId){
        FirebaseApp.initializeApp(_5TrackingCars.this);
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference().child("Vehicle");


        DbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot car : snapshot.getChildren()) {
                    vehicle fetchedItem = car.getValue(vehicle.class);
                    if (car.getKey().equals(CarId)) {
                        currentCars=fetchedItem;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(_5TrackingCars.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}