package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class _4NearestCenter extends AppCompatActivity {
    public ArrayList<Center> availableCenters;
    public ArrayList<String> availableCentersNames;
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    private ProgressDialog createNewDialog;
    public DrawerLayout drawerMenuForUser;
    public AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_nearest_center);
        drawerMenuForUser=findViewById(R.id.drawerMainScreenForUser);
        availableItemsList=findViewById(R.id.listItes1);
        availableCenters=new ArrayList<Center>();
        availableCentersNames=new ArrayList<String>();
        dialog= new AlertDialog.Builder(_4NearestCenter.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Oppps !")
                .setMessage("There aren't any Nearest Center")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        updateScreenData();
    }
    public float getDistance(LatLng my_latlong) {
        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("Two");
        l2.setLatitude(MainScreen.lat);
        l2.setLongitude(MainScreen.lon);

        float distance = l1.distanceTo(l2)/1000;
        return distance;
    }
    private void updateScreenData() {
        availableCenters.clear();
        availableCentersNames.clear();
        createNewDialog = new ProgressDialog(_4NearestCenter.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(_4NearestCenter.this);
        FirebaseDatabase.getInstance().getReference().child("Center").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot child: snapshot.getChildren()) {
                        Center fetchedItem=child.getValue(Center.class);
                        LatLng cenLoc=new LatLng(fetchedItem.Latitude,fetchedItem.Longitude);

                            availableCenters.add(fetchedItem);
                            availableCentersNames.add(fetchedItem.Name);


                    }
                    if(availableCenters.size()>0) {
                        availableItemsListAdapter = new ArrayAdapter
                                (getApplicationContext(), R.layout.aidlist, R.id.nameTextLabel, availableCentersNames) {
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView distance = view.findViewById(R.id.distance);
                                LatLng cenLoc = new LatLng(availableCenters.get(position).Latitude, availableCenters.get(position).Longitude);
                                distance.setText(getDistance(cenLoc) + " Km");

                                CircleImageView imageView = view.findViewById(R.id.ProfileImage);
                                if (!availableCenters
                                        .get(position).Image.equals("")) {
                                    Glide.
                                            with(getApplicationContext()).load(availableCenters
                                            .get(position).Image).into(imageView);
                                } else {
                                    imageView.setImageResource(R.drawable.build);
                                }

                                Button getDir = view.findViewById(R.id.buti);
                                getDir.setText("Get Directions");
                                getDir.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Get Direction For Selected Order
                                        String uri = String.format(Locale.getDefault(), "http://maps.google.com/maps?q=loc:%f,%f"
                                                , availableCenters.get(position).Latitude
                                                , availableCenters.get(position).Longitude);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                        startActivity(intent);
                                    }
                                });
                                return view;
                            }
                        };
                        availableItemsList.setAdapter(availableItemsListAdapter);

                        createNewDialog.dismiss();
                        availableItemsListAdapter.notifyDataSetChanged();
                    }else{
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
        Intent moving=new Intent(getApplicationContext(),_11FirstAisDetails.class);
        startActivity(moving);
    }
    public void BackToHome(View view){
        Intent moving=new Intent(getApplicationContext(),MainScreen.class);
        startActivity(moving);
    }


    public void Center(View view){

        Intent moving=new Intent(getApplicationContext(),_4NearestCenter.class);
        startActivity(moving);

    }

    public void Numbers(View view){
        Intent moving=new Intent(getApplicationContext(),_13Numbers.class);
        startActivity(moving);
    }
}