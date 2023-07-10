package com.example.bijli;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;

public class ElectricianProfileActivity extends AppCompatActivity {
    DatabaseReference reference;

    TextView name;
    TextView phone;
    TextView email;
    TextView address;
    TextView avail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician_profile);
        Intent in = getIntent();
        String E = in.getExtras().getString("Extras");
        name = (TextView)findViewById(R.id.name);
        phone = (TextView)findViewById(R.id.phone);
        email = (TextView)findViewById(R.id.email);
        address = (TextView)findViewById(R.id.address);
        avail = (TextView)findViewById(R.id.avail);


        reference = FirebaseDatabase.getInstance().getReference("Electricians");
        reference.child(E).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@Nullable Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        Toast.makeText(ElectricianProfileActivity.this,"Successfully Read",Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String userName = String.valueOf(dataSnapshot.child("Name").getValue());
                        String avai = String.valueOf(dataSnapshot.child("Available").getValue());
                        String phoneNo = String.valueOf(dataSnapshot.child("phoneNo").getValue());
                        String emailId = String.valueOf(dataSnapshot.child("email").getValue());
                        String add = String.valueOf(dataSnapshot.child("Address").getValue());
                        avail.setText(avai);
                        name.setText(userName);
                        phone.setText(phoneNo);
                        email.setText(emailId);
                        address.setText(add);


                    }else {

                        Toast.makeText(ElectricianProfileActivity.this,"User Doesn't Exist",Toast.LENGTH_SHORT).show();

                    }


                }else {

                    Toast.makeText(ElectricianProfileActivity.this,"Failed to read",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}