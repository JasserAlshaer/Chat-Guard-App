package com.example.ase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class _2SendReport extends AppCompatActivity {
    public ImageView reportImage;
    public BootstrapButton uploadImage,sendReport;
    public BootstrapEditText Notes;
    public Spinner reportType;
    public  String notes,type;
    public Uri imageUri;

    public ArrayAdapter spinnerAdapter;
    public Switch simpleSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_send_report);
        // initiate a Switch
        simpleSwitch =findViewById(R.id.simpleSwitch);
        uploadImage=findViewById(R.id.uploadProfileImage3);
        sendReport=findViewById(R.id.continueRegistrationButton3);
        Notes=findViewById(R.id.ItemsDescription);
        reportType=findViewById(R.id.spn_trainType2);
        String [] categorySpinnerItems=getResources().getStringArray(R.array.type);
        spinnerAdapter =
                new ArrayAdapter(_2SendReport.this,android.R.layout.simple_spinner_item,categorySpinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportType.setAdapter(spinnerAdapter);
        reportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=categorySpinnerItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = "";
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageFromGallery=new Intent();
                getImageFromGallery.setType("image/*");
                getImageFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(getImageFromGallery,1);
            }
        });

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
        FirebaseStorage mfirebaseStorage=FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = mfirebaseStorage.getReference();
        if(imageUri==null){
            Report aseReport=new Report(type,simpleSwitch.isChecked(),MainScreen.lat,MainScreen.lon
                    ,_0CheckAccount.userId,"",notes,false,"");

            FirebaseDatabase.getInstance().getReference().child("Report").push().setValue(aseReport)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Intent backToHome=new Intent(getApplicationContext(),MainScreen.class);
                            startActivity(backToHome);
                        }
                    });
            Toast.makeText(getApplicationContext(), "Created Successfully Operation Success ", Toast.LENGTH_SHORT).show();
        }else{
            StorageReference fileUploadingReference = storageRef.child(System.currentTimeMillis()+"."+getFileExtention(imageUri));
            fileUploadingReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileUploadingReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(type.equals("")){
                                Toast.makeText(getApplicationContext(), "Please Enter Valid Report Type", Toast.LENGTH_SHORT).show();
                            }else{
                                //After Image Is getting Then Create object then Upload data to firebase
                                Report aseReport=new Report(type,simpleSwitch.isChecked(),MainScreen.lat,MainScreen.lon
                                        ,_0CheckAccount.userId,uri.toString(),notes,false,"");

                                FirebaseDatabase.getInstance().getReference().child("Report").push().setValue(aseReport)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                Intent backToHome=new Intent(getApplicationContext(),MainScreen.class);
                                                startActivity(backToHome);
                                            }
                                        });
                                Toast.makeText(getApplicationContext(), "Created Successfully Operation Success ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(_2SendReport.this, "Upload Operation Failed ", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}