package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class _13Numbers extends AppCompatActivity {
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    public ArrayList<Numbers> emergancyNumbers;
    public ArrayList<String> temp;
    public String staticPhone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_13_numbers);

        availableItemsList=findViewById(R.id.listItes129);
        emergancyNumbers=new ArrayList<Numbers>();
        temp=new ArrayList<String>();
        //Please Fill Data Static

        availableItemsListAdapter=new ArrayAdapter
                (getApplicationContext(),R.layout.aidlist,R.id.nameTextLabel,temp){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view= super.getView(position, convertView, parent);
                TextView name=view.findViewById(R.id.nameTextLabel);
                name.setText(emergancyNumbers.get(position).organizationName);
                TextView distance=view.findViewById(R.id.distance);

                distance.setText(emergancyNumbers.get(position).phone);

                CircleImageView imageView=view.findViewById(R.id.ProfileImage);
                Glide.
                        with(getApplicationContext()).load(emergancyNumbers.get(position).ImagePath)
                        .into(imageView);
                Button getDir=view.findViewById(R.id.buti);
                getDir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Direction For Selected Order
                        staticPhone=emergancyNumbers.get(position).phone;
                        callPhoneNumber(emergancyNumbers.get(position).phone);
                    }
                });
                return view;
            }
        };
        availableItemsList.setAdapter(availableItemsListAdapter);
    }
    public void callPhoneNumber(String phone) {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(_13Numbers.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber(staticPhone);
            }
        }
    }
}