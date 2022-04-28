package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class _11FirstAisDetails extends AppCompatActivity {
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    public ArrayList<String> availableAidNames;
    public DrawerLayout drawerMenuForUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11_first_ais_details);
        availableAidNames=new ArrayList<String>();
        drawerMenuForUser=findViewById(R.id.drawerMainScreenForUser);
        availableItemsList=findViewById(R.id.listItes125);
        for (AidSteps st:_3FirstAid.CurrentStepsList) {
            availableAidNames.add(st.stepName);
        }

        availableItemsListAdapter=new ArrayAdapter
                (getApplicationContext(),R.layout.aidlist,R.id.nameTextLabel,availableAidNames){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view= super.getView(position, convertView, parent);
                TextView name=view.findViewById(R.id.nameTextLabel);
                name.setText(_3FirstAid.CurrentStepsList.get(position).serialNumber+" - "
                        +_3FirstAid.CurrentStepsList.get(position).stepName);
                TextView distance=view.findViewById(R.id.distance);

                distance.setText(_3FirstAid.CurrentStepsList.get(position).stepDescription);

                CircleImageView imageView=view.findViewById(R.id.ProfileImage);
                Glide.
                        with(getApplicationContext()).load(_3FirstAid.CurrentStepsList
                        .get(position).ImagePath).into(imageView);
                Button getDir=view.findViewById(R.id.buti);
                getDir.setVisibility(View.INVISIBLE);
                return view;
            }
        };
        availableItemsList.setAdapter(availableItemsListAdapter);
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