package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class vehicle {

    public String PalateNumber;
    public boolean IsAvailable;
    public String ReportId;
    public String Type;
    public String DriverName;
    public String DriverPhone;
    public String CenterId;
    public double Latitude;
    public double Longitude;

    public vehicle() {

    }

    public vehicle(String palateNumber, boolean isAvailable, String reportId, String type, String driverName, String driverPhone, String centerId, double latitude, double longitude) {
        this.PalateNumber = palateNumber;
        this.IsAvailable = isAvailable;
        this. ReportId = reportId;
        this.Type = type;
        this.DriverName = driverName;
        this.DriverPhone = driverPhone;
        this.CenterId = centerId;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }
}
