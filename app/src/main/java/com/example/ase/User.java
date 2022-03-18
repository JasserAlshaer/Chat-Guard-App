package com.example.ase;

public class User {
    public String Name;
    public String Email;
    public String PhoneNumber;
    public double Latitude;
    public double Longitude;
    public String SSN;
    public boolean IsAccountActivate;

    public User(){

    }


    public User(String name, String email, String phoneNumber, double latitude, double longitude, String SSN, boolean isAccountActivate) {
        Name = name;
        Email = email;
        PhoneNumber = phoneNumber;
        Latitude = latitude;
        Longitude = longitude;
        this.SSN = SSN;
        IsAccountActivate = isAccountActivate;
    }
}
