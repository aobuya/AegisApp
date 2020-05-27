package com.example.aegisapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.aegisapp.R;
import com.example.aegisapp.Report;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    Spinner spinner_sb, spinner_cus;
    Button button_report, sb_issue, cus_issue;
    EditText sb_number;
    //firebase database
    //DatabaseReference sb;
     //private FirebaseFirestore db;
    TextView sb_notes, cus_notes;
    Button sb_btn, cus_btn;
    TextView reports;
    ImageView img;
    TextView times;
    TextView txt_location;
    FirebaseAuth firebaseAuth;
    BottomAppBar bar;
    ImageView img_reports, profile;
    private  FirebaseFirestore db;
    DatabaseReference databaseReference;

    String[] listItems;
    String[] listItems1;
    boolean[] checkedItems;
    boolean[] u_checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<Integer> cUserItems = new ArrayList<>();
    ImageView float_image;
    Geocoder geocoder;
    List<Address> addresses;
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)    {
        // Inflate the layout for this fragment

        db = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        sb_number = view.findViewById(R.id.editText);
        button_report = view.findViewById(R.id.button_report);
        //spinner = view.findViewById(R.id.spinner2);
        //spinner_issues = view.findViewById(R.id.spinner_issues);
        reports = view.findViewById(R.id.txtreports);
        times = view.findViewById(R.id.textView4);
        //img = view.findViewById(R.id.user_profile_photo);
        //textview
        sb_notes = view.findViewById(R.id.sb_note);
        cus_notes = view.findViewById(R.id.c_note);
        //button
        sb_issue = view.findViewById(R.id.btView3);
        cus_issue = view.findViewById(R.id.btView2);

        float_image = view.findViewById(R.id.floating_action_button);
        //location_access







        databaseReference = FirebaseDatabase.getInstance().getReference("Sb Issues");

        firebaseAuth = FirebaseAuth.getInstance();


        //sb issue array
        listItems = getResources().getStringArray(R.array.sb_issue);
        //customer issue Array
        listItems1 = getResources().getStringArray(R.array.customer_issue);

        checkedItems = new boolean[listItems.length];
        u_checkedItems = new boolean[listItems1.length];


        sb_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));


                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";

                            }

                        }
                        sb_notes.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            sb_notes.setText("No Issue");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        cus_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems1, u_checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if(isChecked){
                            cUserItems.add(position);
                        }else{
                            cUserItems.remove((Integer.valueOf(position)));

                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < cUserItems.size(); i++) {
                            item = item + listItems[cUserItems.get(i)];
                            if (i != cUserItems.size() - 1)
                            {
                                item = item + ", ";
                            }
                        }
                        cus_notes.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < u_checkedItems.length; i++) {
                            u_checkedItems[i] = false;
                            cUserItems.clear();
                            cus_notes.setText("No Issue");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        //cus_notes


        button_report.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    //reportSb();
                    String sbnumber = sb_number.getText().toString().trim();
                    final String issue_name = sb_notes.getText().toString();
                    String customer_issue = cus_notes.getText().toString();
                    //String time = String.valueOf(Calendar.getInstance().getTime());
                    String time = String.valueOf(java.time.LocalDate.now());

                    String stat = "Open";

                    if(stat == "Open"){

                    }

                    if(!TextUtils.isEmpty(sbnumber) && issue_name!="" && customer_issue!="" ){

                        //String id = db.collection().getId();
                        //CollectionReference sb_issues = db.collection("Sb Issues");
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String utext = user.getEmail();
                        String id = user.getUid();

                        //FirebaseUser uid = firebaseAuth.getCurrentUser();
                        //
                        String sbid = databaseReference.push().getKey();
                        Report report = new Report(sbid, id, sbnumber, issue_name, customer_issue,time, stat, utext);
                        databaseReference.child(sbid).setValue(report);
                        Toast.makeText(getActivity(), "SB Reported", Toast.LENGTH_SHORT).show();
                        sb_number.setText("");
                        cus_notes.setText("No Issue");
                        sb_notes.setText("No Issue");
                        cUserItems.clear();



                        //collectionReference
                        /*Report report = new Report(id, sbnumber, issue_name, customer_issue,time, stat);
                        //sb.child(id).setValue(report);

                        sb_issues.add(report).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getActivity(), "SB Reported", Toast.LENGTH_SHORT).show();
                                sb_number.setText("");
                                cus_notes.setText("No Issue");
                                sb_notes.setText("No Issue");


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Toast.makeText(getActivity(), "SB Reported", Toast.LENGTH_SHORT).show();
                        //sb_number.setText("");*/


                    }else{
                        Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });



        return view;
    }


}
