package com.example.bijli;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintActivity extends AppCompatActivity {
    DatabaseReference reference;
    String consumerNo, userName, phoneNo, email, address;
    int flag = 0;
    EditText editTextcomplaint;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        editTextcomplaint = (EditText) findViewById(R.id.editTextcomplain);
        int permis = ContextCompat.checkSelfPermission(ComplaintActivity.this, Manifest.permission.SEND_SMS);
        Intent in = getIntent();
        String E = in.getExtras().getString("Extras");
        String Em = E.replace(".", ",");

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(Em).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        consumerNo = String.valueOf(dataSnapshot.child("consumerNo").getValue());
                        userName = String.valueOf(dataSnapshot.child("userName").getValue());
                        phoneNo = String.valueOf(dataSnapshot.child("phoneNo").getValue());
                        email = String.valueOf(dataSnapshot.child("email").getValue());
                        address = String.valueOf(dataSnapshot.child("address").getValue());
                    }
                    else {
                        Toast.makeText(ComplaintActivity.this,"User Doesn't Exist",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ComplaintActivity.this,"Failed to read",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        if(permis != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(ComplaintActivity.this, new String[]{Manifest.permission.SEND_SMS}, 123);
//        }


        btn = (Button) findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String complaint = editTextcomplaint.getText().toString().trim();
                if(permis == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    String complaintMessage = "Consumer No.: " + consumerNo + "\n" + "Name: " + userName + "\n" + "Phone No.: " + phoneNo + "\n" +"Email ID: " + email + "\n" + "Address: " + address + "\n" + "Complaint: " + complaint; // Get the message from user input
                    String phoneNumber = "+919110092377"; // Get the recipient's phone number from user input
                    int l = complaintMessage.length();
                    String complaintMessage1 = complaintMessage.substring(0, 159);
                    String complaintMessage2 = complaintMessage.substring(159, l);
                    smsManager.sendTextMessage(phoneNumber, null, complaintMessage1+"-", null, null);
                    smsManager.sendTextMessage(phoneNumber, null, "- "+ complaintMessage2, null, null);
                    Toast.makeText(ComplaintActivity.this, "Complaint Sent Successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    ActivityCompat.requestPermissions(ComplaintActivity.this, new String[]{Manifest.permission.SEND_SMS}, 123);
                    Toast.makeText(ComplaintActivity.this,"Please, try again",Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(ComplaintActivity.this, ContentActivity.class);
                startActivity(intent);
            }
        });
    }
}