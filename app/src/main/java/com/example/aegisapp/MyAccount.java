package com.example.aegisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyAccount extends AppCompatActivity {
    Button singout;
    TextView uname, number;
    FirebaseAuth firebaseAuth;

    TextView imageView;
    FirebaseAuth.AuthStateListener authStateListener;
    TextView rec, call_center, websites, link_facebook, link_twitter, reports_daily;
    Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        singout = findViewById(R.id.sign_out);
        uname = findViewById(R.id.txt_name);
        //Textviews
        rec = findViewById(R.id.rec_friend);
        call_center = findViewById(R.id.call_center);
        websites = findViewById(R.id.websites);
        link_facebook = findViewById(R.id.facebookln);
        link_twitter = findViewById(R.id.twitter);
        imageView = findViewById(R.id.image_rewards);
        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            uname.setText(email);

        }
        singout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(MyAccount.this, LoggerActivity.class));
                MyAccount.this.finishAffinity();


            }
        });
        websites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://safeboda.com")));
            }
        });
        call_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 0709105000")));
            }
        });

        link_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageInfo info = null;
                try {
                    info = MyAccount.this.getPackageManager().getPackageInfo("com.twitter.android", 0);
                    if(info.applicationInfo.enabled)
                        startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=961135641353490432")));
                    else
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/SafeBoda")));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        link_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageInfo info = null;
                try {
                    info = MyAccount.this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    if(info.applicationInfo.enabled)
                        startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("facebook://SafeBoda254/941483249387520")));
                    else
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SafeBoda254/")));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });



    }
}
