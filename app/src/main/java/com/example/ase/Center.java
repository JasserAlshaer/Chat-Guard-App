package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Center {
    public String Name;
    public String AdminEmail;
    public String AdminPassword;
    public String Phone;
    public double Latitude;
    public double Longitude;
    public String Image;



    public Center() {
    }

    public Center(String name, String adminEmail, String adminPassword, String phone, double latitude, double longitude, String image) {
        Name = name;
        AdminEmail = adminEmail;
        AdminPassword = adminPassword;
        Phone = phone;
        Latitude = latitude;
        Longitude = longitude;
        Image = image;
    }
}
