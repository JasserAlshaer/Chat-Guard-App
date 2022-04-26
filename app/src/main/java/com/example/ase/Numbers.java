package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Numbers {
    public String organizationName;
    public String phone;
    public String ImagePath;

    public Numbers() {
    }

    public Numbers(String organizationName, String phone, String imagePath) {
        this.organizationName = organizationName;
        this.phone = phone;
        ImagePath = imagePath;
    }
}
