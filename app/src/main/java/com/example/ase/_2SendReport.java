package com.example.ase;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

public class _2SendReport extends AppCompatActivity {
    public ImageView reportImage;
    public BootstrapButton uploadImage,sendReport;
    public BootstrapEditText Notes;
    public  String notes,type;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_send_report);
        // initiate a Switch
        sendReport=findViewById(R.id.continueRegistrationButton3);
        Notes=findViewById(R.id.ItemsDescription);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data.getData()!=null ){
            try {
                imageUri=data.getData();
                reportImage.setImageURI(imageUri);

            }catch (Exception exception){

            }
        }else {
            Toast.makeText(this, "Smothing Worng !", Toast.LENGTH_SHORT).show();
        }

    }
    public String getFileExtention(Uri muri){
        ContentResolver mContentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(mContentResolver.getType(muri));

    }
    public  void SendingReports(View view){
        notes=Notes.getText().toString();
        Toast.makeText(this, "Will Implemented!", Toast.LENGTH_SHORT).show();
        }

    }