package com.example.bijli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;
    TextView textViewForgotPassword, textViewRegister;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextUserName = (EditText) findViewById(R.id.editTextSignInUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextSignInPassword);

        textViewForgotPassword = (TextView) findViewById(R.id.txtSignInForgotPassword);
        textViewRegister = (TextView) findViewById(R.id.txtSignInRegister);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

    }

    public void txtSignInForgotPasswordClicked(View v) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void txtSignInRegisterClicked(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void buttonSignInScrSignInClicked(View v) {

        String username = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            editTextUserName.setError("Please Enter a Valid Email");
            editTextUserName.requestFocus();
        }

        if(editTextPassword.length() < 6) {
            editTextPassword.setError("Please Enter Password containing atleast 6 characters");
            editTextPassword.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LogInActivity.this, "User has Successfully Signed In",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LogInActivity.this, ContentActivity.class);
                    String p = username;
                    intent.putExtra("Extra",p);
                    startActivity(intent);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LogInActivity.this,"Invalid Email/Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}