package com.example.aegisapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Button login, reset;
    EditText email;
    EditText password;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    TextView verify_user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);email = findViewById(R.id.emailtxt1);
        reset = findViewById(R.id.btn_reset_password);
        password = findViewById(R.id.pass11);
        login = findViewById(R.id.login11);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        verify_user_email = findViewById(R.id.verify_email);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ResetPassword.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    private void logIn() {
        String email_user = email.getText().toString();
        String pass_user = password.getText().toString();

        if(TextUtils.isEmpty(email_user)){
            email.setText("please fill out this field");
        }
        if (TextUtils.isEmpty(pass_user)) {
            password.setText("please fill this field");
        }

        firebaseAuth.signInWithEmailAndPassword(email_user, pass_user)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();

                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                password.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(Login.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }

                            //String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            //String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            /*switch (errorCode) {
                                case "ERROR_INVALID_CUSTOM_TOKEN":
                                    Toast.makeText(LoggerActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                    Toast.makeText(LoggerActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_CREDENTIAL":
                                    Toast.makeText(LoggerActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(LoggerActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                    email.setError("The email address is badly formatted.");
                                    email.requestFocus();
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    Toast.makeText(LoggerActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                    password.setError("password is incorrect ");
                                    password.requestFocus();
                                    password.setText("");
                                    break;

                                case "ERROR_USER_MISMATCH":
                                    Toast.makeText(LoggerActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_REQUIRES_RECENT_LOGIN":
                                    Toast.makeText(LoggerActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    Toast.makeText(LoggerActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(LoggerActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                    email.setError("The email address is already in use by another account.");
                                    email.requestFocus();
                                    break;

                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                    Toast.makeText(LoggerActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_DISABLED":
                                    Toast.makeText(LoggerActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_TOKEN_EXPIRED":
                                    Toast.makeText(LoggerActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_NOT_FOUND":
                                    Toast.makeText(LoggerActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_USER_TOKEN":
                                    Toast.makeText(LoggerActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    Toast.makeText(LoggerActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    Toast.makeText(LoggerActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                    break;                         }*/


                        } else {
                            if (user.isEmailVerified()){
                                Intent intent = new Intent(Login.this, ReporterAcitivity.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                                finish();

                            }
                            else{
                                verify_user_email.setText("Email not verified, Click here to verify");
                                verify_user_email.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            //Log.d("Tag", "Email sent.");
                                                            Toast.makeText(Login.this, "Email sent", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                });
                            }


                        }
                    }
                });
    }
}

