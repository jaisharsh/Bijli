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

public class ProfileActivity extends AppCompatActivity {
    DatabaseReference reference;

    TextView CNO;
    TextView CNA;
    TextView MNO;
    TextView EID;
    TextView ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent in = getIntent();
        String E = in.getExtras().getString("Extras");
        String Em = E.replace(".", ",");
        CNO = (TextView)findViewById(R.id.cno);
        CNA = (TextView)findViewById(R.id.cna);
        MNO = (TextView)findViewById(R.id.mno);
        EID = (TextView)findViewById(R.id.eid);
        ADD = (TextView)findViewById(R.id.add);


        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(Em).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@Nullable Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        Toast.makeText(ProfileActivity.this,"Successfully Read",Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String consumerNo = String.valueOf(dataSnapshot.child("consumerNo").getValue());
                        String userName = String.valueOf(dataSnapshot.child("userName").getValue());
                        String phoneNo = String.valueOf(dataSnapshot.child("phoneNo").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String address = String.valueOf(dataSnapshot.child("address").getValue());
                        CNO.setText(consumerNo);
                        CNA.setText(userName);
                        MNO.setText(phoneNo);
                        EID.setText(email);
                        ADD.setText(address);


                    }else {

                        Toast.makeText(ProfileActivity.this,"User Doesn't Exist",Toast.LENGTH_SHORT).show();

                    }


                }else {

                    Toast.makeText(ProfileActivity.this,"Failed to read",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
