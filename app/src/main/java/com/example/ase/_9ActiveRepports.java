package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class _9ActiveRepports extends AppCompatActivity {
    public ArrayList<Report> availableReports;
    public ArrayList<String> availableReportsNames;
    public ArrayList<String> availableReportsId;
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    private ProgressDialog createNewDialog;
    public static Report selectedReportToTrack;
    public AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_active_repports);

        availableItemsList=findViewById(R.id.availbleEvents);
        availableReports=new ArrayList<Report>();
        availableReportsNames=new ArrayList<String>();
        availableReportsId=new ArrayList<String>();

        dialog= new AlertDialog.Builder(_9ActiveRepports.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Oppps !")
                .setMessage("There aren't any Under Process Report")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        updateScreenData();
    }

    private void updateScreenData() {
        availableReports.clear();
        availableReportsNames.clear();
        createNewDialog = new ProgressDialog(_9ActiveRepports.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(_9ActiveRepports.this);
        FirebaseDatabase.getInstance().getReference().child("Report").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    Report fetchedItem=child.getValue(Report.class);

                    if(!fetchedItem.IsCompleted && _0CheckAccount.userId.equals(fetchedItem.UserId)
                       && fetchedItem.Status.equals("Under Process")){
                        availableReports.add(fetchedItem);
                        availableReportsNames.add(fetchedItem.ReportType);
                        availableReportsId.add(child.getKey());
                    }

                }
                if(availableReports.size()>0) {
                availableItemsListAdapter=new ArrayAdapter
                        (getApplicationContext(),R.layout.aidlist,R.id.nameTextLabel,availableReportsNames){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        TextView distance=view.findViewById(R.id.distance);


                        CircleImageView imageView=view.findViewById(R.id.ProfileImage);

                        imageView.setImageResource(R.drawable.report);
                        Button getDir=view.findViewById(R.id.buti);
                        getDir.setText("Track Emergency Car");
                        getDir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Go To Track Report On Map
                                selectedReportToTrack=availableReports.get(position);

                                Intent seeAvailableCars=new Intent(_9ActiveRepports.this,_5TrackingCars.class);

                                startActivity(seeAvailableCars);
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onMenuClicked(View view) {
        Intent moving=new Intent(getApplicationContext(),MainScreen.class);
        startActivity(moving);
    }
}