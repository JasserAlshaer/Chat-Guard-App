package com.example.ase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class AidSteps {
    public int serialNumber;
    public String stepDescription;
    public String stepName;
    public String ImagePath;

    public AidSteps() {
    }

    public AidSteps(int serialNumber, String stepDescription, String stepName, String imagePath) {
        this.serialNumber = serialNumber;
        this.stepDescription = stepDescription;
        this.stepName = stepName;
        this.ImagePath = imagePath;
    }
}
