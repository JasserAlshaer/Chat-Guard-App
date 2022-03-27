package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Report {

    public String ReportType;
    public boolean IsTrackingLiveLocation;
    public double Latitude;
    public double Longitude;
    public String UserId;
    public String ImagePath;
    public String Notes;

    public Report() {
    }

    public Report(String reportType, boolean isTrackingLiveLocation, double latitude, double longitude, String userId, String imagePath, String notes) {
        this.ReportType = reportType;
        this.IsTrackingLiveLocation = isTrackingLiveLocation;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.UserId = userId;
        this.ImagePath = imagePath;
        this.Notes = notes;
    }
}
