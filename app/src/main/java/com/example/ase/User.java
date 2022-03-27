package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String Name;
    public String Email;
    public String PhoneNumber;
    public double Latitude;
    public double Longitude;
    public String SSN;
    public boolean IsAccountActivate;
    public String ProfileImagePath;
    public User(){

    }


    public User(String name, String email, String phoneNumber, double latitude, double longitude, String SSN, boolean isAccountActivate,String ProfileImagePath) {
        this.Name = name;
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.SSN = SSN;
        this.IsAccountActivate = isAccountActivate;
        this.ProfileImagePath=ProfileImagePath;
    }
}
