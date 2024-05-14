package com.example.ase;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class _7LatestReports extends AppCompatActivity {
    public ArrayList<Chats> availableReports;
    public ArrayList<String> availableReportsNames;
    public ArrayList<String> availableReportsId;
    public ListView availableItemsList;
    public ArrayAdapter availableItemsListAdapter;
    private ProgressDialog createNewDialog;
    public static double staticLatituide=0,staticLongtidue=0;
    public AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_latest_reports);
        availableItemsList=findViewById(R.id.latest);
        availableReports=new ArrayList<Chats>();
        availableReportsNames=new ArrayList<String>();
        availableReportsId=new ArrayList<String>();
        dialog= new AlertDialog.Builder(_7LatestReports.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Oppps !")
                .setMessage("There aren't any Latest Report")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent m=new Intent(_7LatestReports.this,_6Login.class);
                        startActivity(m);
                    }
                });
        //updateScreenData();
    }

    public void onMenuClicked(View view) {
        Intent moving=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(moving);
    }
}