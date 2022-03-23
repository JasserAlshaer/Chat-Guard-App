package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class FirstAid {
    public String statusName;
    public String statusDescription;
    public String ImagePath;
    public List<AidSteps> stepsList;

    public FirstAid() {
    }

    public FirstAid(String statusName, String statusDescription, String imagePath, List<AidSteps> stepsList) {
        this.statusName = statusName;
        this.statusDescription = statusDescription;
        ImagePath = imagePath;
        this.stepsList = stepsList;
    }
}
