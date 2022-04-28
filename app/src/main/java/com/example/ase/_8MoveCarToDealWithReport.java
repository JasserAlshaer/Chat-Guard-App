package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class _8MoveCarToDealWithReport extends AppCompatActivity {
    public ArrayList<vehicle> availableCars;
    public ArrayList<String> availableCarsDriverNames;
    public ArrayList<String> availableCarsId;
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    private ProgressDialog createNewDialog;
    public static String reportType="",Id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_move_car_to_deal_with_report);
        availableItemsList=findViewById(R.id.latest);
        availableCars=new ArrayList<vehicle>();
        availableCarsDriverNames=new ArrayList<String>();
        availableCarsId=new ArrayList<String>();

        Intent data=getIntent();
        reportType =data.getStringExtra("ReportType");
        Id=data.getStringExtra("Id");
        updateScreenData(reportType);
    }
    private void updateScreenData(String reportType) {

        availableCars.clear();
        availableCarsDriverNames.clear();
        createNewDialog = new ProgressDialog(_8MoveCarToDealWithReport.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(_8MoveCarToDealWithReport.this);
        FirebaseDatabase.getInstance().getReference().child("Vehicle").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    vehicle fetchedItem=child.getValue(vehicle.class);
                    if(fetchedItem.IsAvailable=true){
                        if(reportType=="Fire"){
                            if(fetchedItem.Type=="Fireلإruck") {
                                availableCars.add(fetchedItem);
                                availableCarsDriverNames.add(fetchedItem.DriverName);
                                availableCarsId.add(child.getKey());
                            }
                        }else if(reportType=="Criminal offense") {
                            if(fetchedItem.Type=="Poc") {
                                availableCars.add(fetchedItem);
                                availableCarsDriverNames.add(fetchedItem.DriverName);
                                availableCarsId.add(child.getKey());
                            }
                        }else{
                            availableCars.add(fetchedItem);
                            availableCarsDriverNames.add(fetchedItem.DriverName);
                            availableCarsId.add(child.getKey());
                        }
                    }


                }
                availableItemsListAdapter=new ArrayAdapter
                        (getApplicationContext(),R.layout.aidlist,R.id.nameTextLabel,availableCarsDriverNames){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        TextView distance=view.findViewById(R.id.distance);

                        CircleImageView imageView=view.findViewById(R.id.ProfileImage);
                        if(availableCars.get(position).Type=="Fireلإruck") {
                            imageView.setImageResource(R.drawable.fir);
                        }
                        else if(availableCars.get(position).Type=="Poc"){
                            imageView.setImageResource(R.drawable.poc);
                        }else if(availableCars.get(position).Type=="Amp"){
                            imageView.setImageResource(R.drawable.amp);
                        }else{
                            imageView.setImageResource(R.drawable.car);
                        }

                        Button getDir=view.findViewById(R.id.buti);
                        getDir.setText("Assign Report");
                        getDir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Go To Available Cars
                                AssignOrderCarAndUpdateInfo(Id,availableCarsId.get(position));
                                Intent latestOrder=new Intent(_8MoveCarToDealWithReport.this,_7LatestReports.class);

                                startActivity(latestOrder);
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
    public void AssignOrderCarAndUpdateInfo(String reportId,String carId){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = database.child("Vehicle");
        final Query applesQuery = ref.child(carId);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childnow: dataSnapshot.getChildren()) {
                        vehicle fetched=childnow.getValue(vehicle.class);
                        ref.child(carId).child("IsAvailable").setValue(false);
                        ref.child(carId).child("ReportId").setValue(reportId);

                        Toast.makeText(_8MoveCarToDealWithReport.this, "Done Success", Toast.LENGTH_SHORT).show();

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
    public void onMenuClicked(View view) {
        Intent moving=new Intent(getApplicationContext(),_7LatestReports.class);
        startActivity(moving);
    }
}