package com.example.ase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class _10FinishTheMission extends AppCompatActivity {
    public static String reportId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10_finish_the_mission);

        Intent data=getIntent();
        reportId =data.getStringExtra("RId");



    }
    public void onMenuClicked(View view) {
        Intent moving=new Intent(getApplicationContext(),_12AssignedMissoin.class);
        startActivity(moving);
    }
}