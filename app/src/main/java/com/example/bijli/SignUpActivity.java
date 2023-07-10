package com.example.bijli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextAddress;
    EditText editTextConsumerNo;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    EditText editTextPhoneNo;
    EditText editTextEmail;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextConsumerNo = (EditText)findViewById(R.id.editTextConsumerNo);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword= (EditText)findViewById(R.id.editTextConfirmPassword);
        editTextPhoneNo = (EditText)findViewById(R.id.editTextMobileNo);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

    }

    public void signupButtonSubmitted(View v) {

        String txtConsumerNo = editTextConsumerNo.getText().toString().trim();
        String txtAddress = editTextAddress.getText().toString().trim();
        String txtUserName = editTextUserName.getText().toString().trim();
        String txtPassword = editTextPassword.getText().toString().trim();
        String txtConfirmPassword = editTextConfirmPassword.getText().toString().trim();
        String txtPhoneNo = editTextPhoneNo.getText().toString().trim();
        String txtEmail = editTextEmail.getText().toString().trim();

        if (txtConsumerNo.isEmpty()) {
            editTextConsumerNo.setError("Please Enter Consumer Number");
            editTextConsumerNo.requestFocus();
        }

        else if (txtUserName.isEmpty()) {
            editTextUserName.setError("Please Enter UserName");
            editTextUserName.requestFocus();
        }

        else if (txtPhoneNo.isEmpty() || txtPhoneNo.length()!=10) {
            editTextPhoneNo.setError("Please Enter Mobile Number containing 10 digits");
            editTextPhoneNo.requestFocus();
        }

        else if (txtEmail.isEmpty()) {
            editTextEmail.setError("Please Enter Email ");
            editTextEmail.requestFocus();
        }

        else if (txtPassword.isEmpty() || txtPassword.length() < 6) {
            editTextPassword.setError("Please Enter Password Containing atleast six characters");
            editTextPassword.requestFocus();
        }

        else if (txtConfirmPassword.isEmpty() || txtConfirmPassword.length() < 6) {
            editTextConfirmPassword.setError("Please Enter Confirm Password Containing atleast six characters");
            editTextConfirmPassword.requestFocus();
        }

        else if (txtAddress.isEmpty()) {
            editTextAddress.setError("Please Enter Address ");
            editTextAddress.requestFocus();
        }

        else if (!txtConfirmPassword.equals(txtPassword)) {
            Toast.makeText(SignUpActivity.this, "Confirm Password is not matched with Password", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }

        else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                User user = new User(txtAddress, txtUserName, txtPassword, txtPhoneNo, txtEmail, txtConsumerNo);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(txtEmail.replace(".", ","))
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUpActivity.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(SignUpActivity.this, "User failed to Register", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Invalid Email/Email already in use", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }

    }
}