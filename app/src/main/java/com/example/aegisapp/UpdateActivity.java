package com.example.aegisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    TextView uNumber, uIssue, ucIssue, uStatus;
    String usbid, usb_number, usb_issue, ucustomer_issue, ucurrent_date, usb_status, uid;
    private Spinner spinner;
    private FirebaseFirestore db;
    private Report report;
    private SbAdapter adapter;
    private List<Report> reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        report = (Report) getIntent().getSerializableExtra("Report");
        db = FirebaseFirestore.getInstance();

        uNumber = findViewById(R.id.uNumber);
        uIssue = findViewById(R.id.sb_note);
        ucIssue = findViewById(R.id.c_note);
        uStatus = findViewById(R.id.status);
        adapter = new SbAdapter(this, reportList);

        Button update = findViewById(R.id.button_report);
        spinner = findViewById(R.id.spinnerUp);

         //getvalues to string here add to document
        usbid = report.getSbid();
        usb_number = report.getSb_number();
        usb_issue = report.getSb_issue();
        ucustomer_issue = report.getCustomer_issue();
        ucurrent_date = report.getCurrent_date();
        usb_status = report.getSb_status();
        uid = report.getId();

        uNumber.setText(usb_number);
        uIssue.setText(usb_issue);
        ucIssue.setText(ucustomer_issue);
        uStatus.setText(usb_status);

        //spinner.setSelection(Integer.parseInt(report.getSb_status()));
        /*update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSb();
            }
        });*/

    }
    /*private void updateSb() {

        String case1 = spinner.getSelectedItem().toString();
        //Report report1 = new Report(usbid, usb_number, usb_issue, ucustomer_issue, ucurrent_date, case1);
        uStatus.setText(case1);
        //db.collection("Sb Issues").document(report.getId()).set(report1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateActivity.this, "Case changed", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                finish();
                //startActivity(new Intent(UpdateActivity.this, Reports.class));
            }
        });*/

    }

