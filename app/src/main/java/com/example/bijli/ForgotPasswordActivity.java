package com.example.bijli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editTextEmail;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = (EditText) findViewById(R.id.editTextForgetPasswordEmail);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
    }

    public void forgetPasswordResetBtnClicked(View v) {

        resetPassword();

    }

    private void resetPassword() {

        String txtEmail = editTextEmail.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
            editTextEmail.setError("Please Enter a Valid Email");
            editTextEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

//        For sending email for reseting password
        mAuth.sendPasswordResetEmail(txtEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please Check Your Email to Reset Password", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(ForgotPasswordActivity.this, "Failed to Reset Password", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}