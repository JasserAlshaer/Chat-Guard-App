package com.example.ase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class MainScreen extends AppCompatActivity {
    public DrawerLayout drawerMenuForUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        drawerMenuForUser=findViewById(R.id.drawerMainScreenForUser);
    }
    public void onMenuClicked(View view) {
        openDrawer(drawerMenuForUser);
    }
    //This method do the opening drawer operation
    private static void openDrawer(DrawerLayout draw) {
        draw.openDrawer(GravityCompat.START);
    }
    //This method do the closing drawer operation
    private static void closeDrawer(DrawerLayout draw) {
        if(draw.isDrawerOpen(GravityCompat.START)){
            draw.closeDrawer(GravityCompat.START);
        }
    }

    public void MoveToFirstAds(View view){

    }

    public void Center(View view){


    }

    public void Numbers(View view){

    }

}