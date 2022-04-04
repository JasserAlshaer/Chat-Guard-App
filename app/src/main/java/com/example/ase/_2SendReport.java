package com.example.ase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;

public class _2SendReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_send_report);
        // initiate a Switch
        Switch simpleSwitch =findViewById(R.id.simpleSwitch);
        // check current state of a Switch (true or false).
        Boolean switchState = simpleSwitch.isChecked();
    }
}