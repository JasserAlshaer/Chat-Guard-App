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
    public boolean IsCompleted;
    public String CarId;
    public String Status;

    public Report() {
    }

    public Report(String reportType, boolean isTrackingLiveLocation, double latitude, double longitude, String userId, String imagePath,
                  String notes, boolean isCompleted,String CarId,String Status) {
        ReportType = reportType;
        IsTrackingLiveLocation = isTrackingLiveLocation;
        Latitude = latitude;
        Longitude = longitude;
        UserId = userId;
        ImagePath = imagePath;
        Notes = notes;
        IsCompleted = isCompleted;
        this.CarId=CarId;
        this.Status=Status;
    }
}
