package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Report {

    public String ReportType;
    public String IsTrackingLiveLocation;
    public double Latitude;
    public double Longitude;
    public String UserId;
    public String ImagePath;
    public String Notes;

    public Report() {
    }

    public Report(String reportType, String isTrackingLiveLocation, double latitude, double longitude, String userId, String imagePath, String notes) {
        ReportType = reportType;
        IsTrackingLiveLocation = isTrackingLiveLocation;
        Latitude = latitude;
        Longitude = longitude;
        UserId = userId;
        ImagePath = imagePath;
        Notes = notes;
    }
}
