package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class _12AssignedMissoin extends AppCompatActivity implements LocationListener {
    public ArrayList<Report> availableReports;
    public ArrayList<String> availableReportsNames;
    public ArrayList<String> availableReportsId;
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    private ProgressDialog createNewDialog;
    public static double lat,lon;
    public LocationManager myLocationManager;
    public CountDownTimer countDownTimer;
    public AlertDialog.Builder dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_12_assigned_missoin);
        availableItemsList=findViewById(R.id.assigend);
        availableReports=new ArrayList<Report>();
        availableReportsNames=new ArrayList<String>();
        availableReportsId=new ArrayList<String>();

        dialog= new AlertDialog.Builder(_12AssignedMissoin.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Oppps !")
                .setMessage("There aren't any Assigned Mission")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        if(ContextCompat.checkSelfPermission(_12AssignedMissoin.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(_12AssignedMissoin.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }else{
            getLocation();
        }


        long time =1000*60*60;
        long counter=1000*40;
        countDownTimer=new CountDownTimer(time,counter){
            @Override
            public void onTick(long millisUntilFinished) {
               getLocation();
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();


        updateScreenData();
    }
    private void updateScreenData() {
        createNewDialog = new ProgressDialog(_12AssignedMissoin.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Report");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        Report fetchedItem=child.getValue(Report.class);
                        if(!fetchedItem.IsCompleted && fetchedItem.CarId.equals(_6Login.vehicleId)
                           && fetchedItem.Status.equals("Under Process")){
                            availableReports.add(fetchedItem);
                            availableReportsNames.add(fetchedItem.ReportType);
                            availableReportsId.add(child.getKey());
                        }

                    }

                }
                if(availableReports.size()>0) {
                availableItemsListAdapter=new ArrayAdapter
                        (getApplicationContext(),R.layout.orderitemlistdesign,R.id.orderforLabel,availableReportsNames){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        TextView distance=view.findViewById(R.id.destance);
                        LatLng report=new LatLng(availableReports.get(position).Latitude,availableReports.get(position).Longitude);
                        distance.setText("Distance :  "+getDistance(report)+"");


                        Button getDir=view.findViewById(R.id.getDirectionsButton);
                        getDir.setText("Get Directions");
                        getDir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Go To Available Cars

                                // Get Direction For Selected Order
                                String uri = String.format(Locale.getDefault(), "http://maps.google.com/maps?q=loc:%f,%f"
                                        , availableReports.get(position).Latitude
                                        ,availableReports.get(position).Longitude);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);
                            }
                        });
                        Button finishMission=view.findViewById(R.id.finishOrderButtonByDelivery);
                        finishMission.setText("Finish Mission");
                        finishMission.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FinishDealingWithReport(availableReportsId.get(position));
                            }
                        });

                        return view;
                    }
                };
                availableItemsList.setAdapter(availableItemsListAdapter);
                createNewDialog.dismiss();
                availableItemsListAdapter.notifyDataSetChanged();}else{
                    dialog.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Null", "onCancelled", databaseError.toException());
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            myLocationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,_12AssignedMissoin.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference().child("Vehicle").child(_6Login.vehicleId);

        lat=location.getLatitude();
        lon=location.getLongitude();

        DbRef.child("Latitude").setValue(lat);
        DbRef.child("Longitude").setValue(lon);


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
    public String getDistance(LatLng reportLoc) {
        Location l1 = new Location("One");
        l1.setLatitude(lat);
        l1.setLongitude(lon);

        Location l2 = new Location("Two");
        l2.setLatitude(reportLoc.latitude);
        l2.setLongitude(reportLoc.longitude);

        float distance = l1.distanceTo(l2);

        if(distance>1000){
            distance=distance/1000;
            return distance+" Km";
        }
        return distance+"M";
    }

    public void onMenuClicked(View view) {
        Intent moving=new Intent(getApplicationContext(),_6Login.class);
        startActivity(moving);
    }

    public  void  FinishDealingWithReport(String Id){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Report");
        database.child(Id).child("IsCompleted").setValue(true);
        database.child(Id).child("Status").setValue("Completed");
        FirebaseDatabase.getInstance().getReference().child("Vehicle").child(_6Login.vehicleId).child("IsAvailable").setValue(true);
        updateScreenData();
    }
}