package com.example.aegisapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyReports extends Fragment {
    //
    Button singout;
    TextView uname, number;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView imageView;
    FirebaseAuth.AuthStateListener authStateListener;
    TextView rec, call_center, websites, link_facebook, link_twitter, reports_daily;
    Report report;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_reports, container, false);
        singout = view.findViewById(R.id.sign_out);
        uname = view.findViewById(R.id.txt_name);
        //Textviews
        rec = view.findViewById(R.id.rec_friend);
        call_center = view.findViewById(R.id.call_center);
        websites = view.findViewById(R.id.websites);
        link_facebook = view.findViewById(R.id.facebookln);
        link_twitter = view.findViewById(R.id.twitter);
        imageView = view.findViewById(R.id.image_rewards);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promo();
            }
        });


        //number = view.findViewById(R.id.txt_num);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String sid =  firebaseFirestore.collection("Sb Issues").document().getId();
        String uid= firebaseAuth.getCurrentUser().getUid();


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
                    info = getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
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
                    info = getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    if(info.applicationInfo.enabled)
                        startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("facebook://SafeBoda254/941483249387520")));
                    else
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SafeBoda254/")));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            uname.setText(email);




         // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            //String uid = user.getUid();
        }
        
        singout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), LoggerActivity.class));
                getActivity().finishAffinity();


            }
        });
        return view;
    }

    private void promo() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Congrats use promo code X3C")
                .show();

    }


}
