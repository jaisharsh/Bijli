package com.example.bijli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ContentActivity extends AppCompatActivity {
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent in = getIntent();
        name = in.getExtras().getString("Extra");
    }

    public void onProfilegetstarted(View view) {
        Intent intent = new Intent(ContentActivity.this, ProfileActivity.class);
        intent.putExtra("Extras",name);
        startActivity(intent);
    }

    public void onComplaintgetstarted(View view) {
        Intent intent = new Intent(ContentActivity.this, ComplaintActivity.class);
        intent.putExtra("Extras",name);
        startActivity(intent);
    }

    public void onElectriciansgetstarted(View view) {
        Intent intent = new Intent(ContentActivity.this, ElectriciansActivity.class);
        startActivity(intent);
    }

    public void onAboutusgetstarted(View view) {
        Intent intent = new Intent(ContentActivity.this, AboutusActivity.class);
        startActivity(intent);
    }

}