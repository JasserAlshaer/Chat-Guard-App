package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Center {
    public String Name;
    public String AdminId;
    public String Phone;
    public double Latitude;
    public double Longitude;


    public Center(String name, String adminId, String phone, double latitude, double longitude) {
        Name = name;
        AdminId = adminId;
        Phone = phone;
        Latitude = latitude;
        Longitude = longitude;
    }

    public Center() {
    }
}
