package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11_first_ais_details);
        availableAidNames=new ArrayList<String>();

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
}