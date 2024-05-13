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
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class _14UserProfile extends AppCompatActivity {
    public ImageView userImage;
    public BootstrapButton uploadImage;
    public BootstrapEditText PhoneNumber;
    public  String phones;
    public Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_14_user_profile);
        uploadImage=findViewById(R.id.uploadProfileImage);
        userImage=findViewById(R.id.profile_image);

        PhoneNumber=findViewById(R.id.phone);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageFromGallery=new Intent();
                getImageFromGallery.setType("image/*");
                getImageFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(getImageFromGallery,1);
            }
        });
        //LoadMyAccountInfo();
    }

    public void UpdateProfile(View view) {
        phones=PhoneNumber.getText().toString();
        //Fetch User Info By Object Id
        Toast.makeText(_14UserProfile.this, "Not Implemented Yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data.getData()!=null ){
            try {
                imageUri=data.getData();
                userImage.setImageURI(imageUri);

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
}