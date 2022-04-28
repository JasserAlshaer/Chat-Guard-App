package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class vehicle {

    public String PalateNumber;
    public boolean IsAvailable;
    public String ReportId;
    public String Type;
    public String DriverName;
    public String DriverEmail;
    public String DriverPassword;
    public String DriverPhone;
    public String CenterId;
    public double Latitude;
    public double Longitude;

    public vehicle() {

    }


    public vehicle(String palateNumber, boolean isAvailable, String reportId, String type, String driverName, String driverEmail, String driverPassword, String driverPhone, String centerId, double latitude, double longitude) {
        PalateNumber = palateNumber;
        IsAvailable = isAvailable;
        ReportId = reportId;
        Type = type;
        DriverName = driverName;
        DriverEmail = driverEmail;
        DriverPassword = driverPassword;
        DriverPhone = driverPhone;
        CenterId = centerId;
        Latitude = latitude;
        Longitude = longitude;
    }
}
