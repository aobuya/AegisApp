package com.example.aegisapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    Button login;
    Button log;
    EditText email_text;
    EditText pass_text;
    EditText pass2_text;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login1);
        log = findViewById(R.id.logger);
        email_text = findViewById(R.id.emailtxt);
        pass_text = findViewById(R.id.pass1);
        pass2_text = findViewById(R.id.pass2);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
            }
        };

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LoggerActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {

        String user_email = email_text.getText().toString().trim();
        String user_pass1 = pass_text.getText().toString().trim();
        String user_pass2 = pass2_text.getText().toString().trim();

        if (TextUtils.isEmpty(user_email)) {
            email_text.setError("Please enter your email");
        }
        if (TextUtils.isEmpty(user_pass1)) {
            pass_text.setError("enter password");
        }
        if (TextUtils.isEmpty(user_pass2)) {
            pass2_text.setError("enter password");
        }
        if (!user_pass1.equals(user_pass2)) {
            pass2_text.setError("Passwords do not match");
        } else {
            progressBar.setVisibility(View.VISIBLE);
            //signup

            firebaseAuth.createUserWithEmailAndPassword(user_email, user_pass1)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(LoginActivity.this, "Authentication complete", Toast.LENGTH_SHORT).show();


                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "" + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(LoginActivity.this, LoggerActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }
}
