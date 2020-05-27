package com.example.aegisapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SbReports extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    List<Report> reportList;
    ReportList arrayAdapter;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_reports);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.list_reports);
        progressBar = findViewById(R.id.progressBar);
        arrayAdapter = new ReportList(SbReports.this, reportList);
        reportList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Sb Issues");

        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Report report = reportList.get(position);
                showUpdateDialog(report.getSbid(), report.getId(), report.getSb_number(), report.getSb_issue(), report.getCustomer_issue(),
                        report.getCurrent_date(), report.getSb_status(), report.getUmail());

                return false;
            }
        });*/
    }



    @Override
    protected void onStart() {
        super.onStart();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // for customer__databaseReference.orderByChild("id").equalTo(uid).addValueEventListener(new ValueEventListener()
        //for admin____
        databaseReference.orderByChild("id").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                reportList.clear();
                for (DataSnapshot reportsnapshot : dataSnapshot.getChildren()) {
                    Report report = reportsnapshot.getValue(Report.class);
                    reportList.add(report);
                }
                if (SbReports.this != null) {
                    ReportList arrayAdapter = new ReportList(SbReports.this, reportList);
                    listView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                int error = databaseError.getCode();
                Toast.makeText(SbReports.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void showUpdateDialog(final String ssbid, final String sid, final String ssb_number, final String ssb_issue, final String scustomer_issue, final String scurrent_date, String ssb_status, final String ssb_email)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.status_update, null);

        dialogBuilder.setView(dialogView);

        final Spinner spinner = dialogView.findViewById(R.id.spinner_status);
        final TextView textView = dialogView.findViewById(R.id.txtnum);
        final Button button = dialogView.findViewById(R.id.btn_update);

        dialogBuilder.setTitle("Updating " +ssb_number);
        textView.setText(ssb_number);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stat = spinner.getSelectedItem().toString();

                updateSb(ssbid, sid, ssb_number, ssb_issue, scustomer_issue, scurrent_date, stat, ssb_email);
                alertDialog.dismiss();
            }
        });
    }
    private  boolean  updateSb(String s_sbid,
                               String s_id,
                               String s_sb_number,
                               String s_sb_issue,
                               String s_customer_issue,
                               String s_current_date,
                               String s_sb_status,
                               String s_sb_email){
        DatabaseReference sbreference = FirebaseDatabase.getInstance().getReference("Sb Issues").child(s_sbid);
        Report report = new Report(s_sbid, s_id, s_sb_number, s_sb_issue, s_customer_issue, s_current_date, s_sb_status, s_sb_email);
        sbreference.setValue(report);
        Toast.makeText(SbReports.this, "Case changed", Toast.LENGTH_SHORT).show();
        return  true;
    }


    }






