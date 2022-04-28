package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class _3FirstAid extends AppCompatActivity {


    public ArrayList<FirstAid> availableAid;
    public ArrayList<String> availableAidNames;
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    private ProgressDialog createNewDialog;
    public static List<AidSteps> CurrentStepsList;
    public DrawerLayout drawerMenuForUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_first_aid);
        drawerMenuForUser=findViewById(R.id.drawerMainScreenForUser);
        availableItemsList=findViewById(R.id.listItes);
        availableAid=new ArrayList<FirstAid>();
        availableAidNames=new ArrayList<String>();

        updateScreenData();

    }
    private void updateScreenData() {
        availableAid.clear();
        availableAidNames.clear();
        createNewDialog = new ProgressDialog(_3FirstAid.this);
        createNewDialog.setMessage("Please Wait ... ");
        createNewDialog.show();
        FirebaseApp.initializeApp(_3FirstAid.this);
        FirebaseDatabase.getInstance().getReference().child("FirstAid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    FirstAid fetchedItem=child.getValue(FirstAid.class);
                    availableAid.add(fetchedItem);
                    availableAidNames.add(fetchedItem.statusName);
                }
                availableItemsListAdapter=new ArrayAdapter
                        (getApplicationContext(),R.layout.aidlist,R.id.nameTextLabel,availableAidNames){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        TextView distance=view.findViewById(R.id.distance);

                        distance.setText(availableAid.get(position).statusDescription);

                        CircleImageView imageView=view.findViewById(R.id.ProfileImage);
                        Glide.
                                with(getApplicationContext()).load(availableAid
                                .get(position).ImagePath).into(imageView);
                        Button getDir=view.findViewById(R.id.buti);
                        getDir.setText("Get Details");
                        getDir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Get Direction For Selected Order
                                CurrentStepsList=availableAid.get(position).stepsList;
                                Intent intent = new Intent(_3FirstAid.this,_11FirstAisDetails.class);
                                startActivity(intent);
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