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
        this.Name = name;
        this.AdminId = adminId;
        this.Phone = phone;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public Center() {
    }
}
