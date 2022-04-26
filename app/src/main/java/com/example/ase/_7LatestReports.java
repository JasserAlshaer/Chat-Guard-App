package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class _7LatestReports extends AppCompatActivity {
    public ArrayList<Report> availableReports;
    public ArrayList<String> availableReportsNames;
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    private ProgressDialog createNewDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_latest_reports);
        availableItemsList=findViewById(R.id.latest);
        availableReports=new ArrayList<Report>();
        availableReportsNames=new ArrayList<String>();

        updateScreenData();
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
    private void updateScreenData() {
        availableReports.clear();
        availableReportsNames.clear();
        createNewDialog = new ProgressDialog(_7LatestReports.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(_7LatestReports.this);
        FirebaseDatabase.getInstance().getReference().child("Report").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    Report fetchedItem=child.getValue(Report.class);
                    if(fetchedItem.IsCompleted==false){
                        availableReports.add(fetchedItem);
                        availableReportsNames.add(fetchedItem.ReportType);
                    }

                }
                availableItemsListAdapter=new ArrayAdapter
                        (getApplicationContext(),R.layout.aidlist,R.id.nameTextLabel,availableReportsNames){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        TextView distance=view.findViewById(R.id.distance);
                        LatLng cenLoc=new LatLng(availableReports.get(position).Latitude,availableReports.get(position).Longitude);
                        distance.setText("Distance :  "+getDistance(cenLoc,new LatLng(_6Login.currentCenter.Latitude,_6Login.currentCenter.Longitude))+"");

                        CircleImageView imageView=view.findViewById(R.id.ProfileImage);
                        Glide.
                                with(getApplicationContext()).load(availableReports
                                .get(position).ImagePath).into(imageView);
                        Button getDir=view.findViewById(R.id.buti);
                        getDir.setText("Get Directions");
                        getDir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Go To Available Cars

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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}