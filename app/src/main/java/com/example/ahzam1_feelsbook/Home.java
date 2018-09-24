package com.example.ahzam1_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void viewHistory(View view){
        Intent intent = new Intent(this, ViewHistory.class);
        startActivity(intent);
    }
    public void viewTotals(View view){
        Intent intent = new Intent(this, ViewTotals.class);
        startActivity(intent);
    }
}
