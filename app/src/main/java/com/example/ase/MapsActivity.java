package com.example.ase;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapAlert;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.ase.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ase.databinding.ActivityMapsBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , LocationListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    public LocationManager myLocationManager;
    public static double lat,lon;
    public static vehicle currentCars;
    public CountDownTimer trackingOrder;
    public BootstrapEditText dist,time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GetCarInfo(_9ActiveRepports.selectedReportToTrack.CarId);
        dist=findViewById(R.id.dis);
        time=findViewById(R.id.deliv);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(_9ActiveRepports.selectedReportToTrack.IsTrackingLiveLocation){
            if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
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
                Toast.makeText(MapsActivity.this, "Check Map", Toast.LENGTH_SHORT).show();
                if(_9ActiveRepports.selectedReportToTrack.IsTrackingLiveLocation){
                    getLocation();
                }else{
                    lat=_9ActiveRepports.selectedReportToTrack.Latitude;
                    lon=_9ActiveRepports.selectedReportToTrack.Longitude;
                }
                GetCarInfo(_9ActiveRepports.selectedReportToTrack.CarId);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userOrReportLocation,17));
        getLocation();

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            myLocationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,MapsActivity.this);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userOrReportLocation,17));

        //show time
        time.setText("Time = "+(getDistance(userOrReportLocation,CarLocation)/13)/60+"Min");
        //show distance
        dist.setText("Distance = "+getDistance(userOrReportLocation,CarLocation)+" Meters");

    }
    public double getDistance(LatLng my_latlong,LatLng car) {
        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("Two");
        l2.setLatitude(car.latitude);
        l2.setLongitude(car.longitude);

        float distance = l1.distanceTo(l2)/1000;

        return distance;
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
        FirebaseApp.initializeApp(MapsActivity.this);
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

                Toast.makeText(MapsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Finish(View view) {
        finish();
    }
}