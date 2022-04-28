package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class _12AssignedMissoin extends AppCompatActivity {
    public ArrayList<Report> availableReports;
    public ArrayList<String> availableReportsNames;
    public ArrayList<String> availableReportsId;
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    private ProgressDialog createNewDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_12_assigned_missoin);
        availableItemsList=findViewById(R.id.assigend);
        availableReports=new ArrayList<Report>();
        availableReportsNames=new ArrayList<String>();
        availableReportsId=new ArrayList<String>();

        updateScreenData();
    }
    private void updateScreenData() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = database.child("Vehicle");
        final Query applesQuery = ref.child(_6Login.vehicleId);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childnow: dataSnapshot.getChildren()) {
                        vehicle fetched=childnow.getValue(vehicle.class);

                        final DatabaseReference ref = database.child("Report");
                        final Query applesQuery = ref.child(fetched.ReportId);

                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                                        Report fetchedItem=childnow.getValue(Report.class);
                                        if(fetchedItem.IsCompleted==false){
                                            availableReports.add(fetchedItem);
                                            availableReportsNames.add(fetchedItem.ReportType);
                                            availableReportsId.add(child.getKey());
                                        }

                                    }

                                }
                                availableItemsListAdapter=new ArrayAdapter
                                        (getApplicationContext(),R.layout.orderitemlistdesign,R.id.orderforLabel,availableReportsNames){
                                    @NonNull
                                    @Override
                                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                        View view= super.getView(position, convertView, parent);
                                        TextView distance=view.findViewById(R.id.destance);
                                        LatLng cenLoc=new LatLng(availableReports.get(position).Latitude,availableReports.get(position).Longitude);
                                        distance.setText("Distance :  "+getDistance(cenLoc,new LatLng(_6Login.currentCenter.Latitude,_6Login.currentCenter.Longitude))+"");


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
                                availableItemsListAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("Null", "onCancelled", databaseError.toException());
                            }
                        });

                        // this method used to change Activation Status  for DeliveryMan


                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Null", "onCancelled", databaseError.toException());
            }
        });

        // this method used to change Activation Status  for DeliveryMan


    }
    public String getDistance(LatLng my_latlong,LatLng reportLoc) {
        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("Two");
        l2.setLatitude(reportLoc.latitude);
        l2.setLongitude(reportLoc.longitude);

        float distance = l1.distanceTo(l2)/1000;
        return distance+" Km";
    }

    public void onMenuClicked(View view) {
        Intent moving=new Intent(getApplicationContext(),_6Login.class);
        startActivity(moving);
    }

    public  void  FinishDealingWithReport(String Id){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //update Report
        final DatabaseReference ref = database.child("Report");
        final Query applesQuery = ref.child(Id);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childnow: dataSnapshot.getChildren()) {
                        vehicle fetched=childnow.getValue(vehicle.class);
                        ref.child(Id).child("IsCompleted").setValue(true);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Null", "onCancelled", databaseError.toException());
            }
        });


        //Update Car Status

        final DatabaseReference ref2 = database.child("Vehicle");
        final Query applesQuery2 = ref2.child(_6Login.vehicleId);

        applesQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childnow: dataSnapshot.getChildren()) {
                        vehicle fetched=childnow.getValue(vehicle.class);
                        ref2.child(_6Login.vehicleId).child("IsAvailable").setValue(true);
                        Toast.makeText(_12AssignedMissoin.this, "Done", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Null", "onCancelled", databaseError.toException());
            }
        });

    }
}