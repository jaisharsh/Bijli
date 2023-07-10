package com.example.bijli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ElectriciansActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricians);
        Intent in = getIntent();
    }

    public void onElectrician1clicked(View view) {
        Intent intent = new Intent(ElectriciansActivity.this, ElectricianProfileActivity.class);
        intent.putExtra("Extras","Electrician1");
        startActivity(intent);
    }

    public void onElectrician2clicked(View view) {
        Intent intent = new Intent(ElectriciansActivity.this, ElectricianProfileActivity.class);
        intent.putExtra("Extras","Electrician2");
        startActivity(intent);
    }

    public void onElectrician3clicked(View view) {
        Intent intent = new Intent(ElectriciansActivity.this, ElectricianProfileActivity.class);
        intent.putExtra("Extras","Electrician3");
        startActivity(intent);
    }

    public void onElectrician4clicked(View view) {
        Intent intent = new Intent(ElectriciansActivity.this, ElectricianProfileActivity.class);
        intent.putExtra("Extras","Electrician4");
        startActivity(intent);
    }
}