package com.example.aegisapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class AllReports extends Fragment {
    ListView listView;
    //DatabaseReference sb;
    private  FirebaseFirestore db;
    ReportList arrayAdapter;
    TextView status;
    List<Report> reportList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_reports, container, false);
        listView = view.findViewById(R.id.list_view);
        //sb = FirebaseDatabase.getInstance().getReference("SB");
        arrayAdapter = new ReportList(getActivity(),reportList);
        db = FirebaseFirestore.getInstance();
        //sb.orderByChild("sbid");
        status = view.findViewById(R.id.case_status);
        //

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String email = user.getEmail();



                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        Report report = reportList.get(position);
                        showUpdateDialog(report.getSbid(), report.getSb_number(), report.getSb_issue(), report.getCustomer_issue(),
                                report.getCurrent_date(), report.getSb_status());

                        return false;
                    }
                });


        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }





    @Override
    public void onStart() {
        super.onStart();

        db.collection("Sb Issues").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                reportList.clear();
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Report report = documentSnapshot.toObject(Report.class);
                    reportList.add(report);
                }
                ReportList arrayAdapter = new ReportList(getActivity(),reportList);
                listView.setAdapter(arrayAdapter);
            }
        });

        db.collection("Sb Issues").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for(DocumentSnapshot d : list){
                        Report report = d.toObject(Report.class);
                        reportList.add(report);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }

            }
        });



        /*sb.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reportList.clear();
                for(DataSnapshot reportsnapshot : dataSnapshot.getChildren()){
                    Report report = reportsnapshot.getValue(Report.class);
                    reportList.add(report);
                }
                if (getActivity()!=null){
                    ReportList arrayAdapter = new ReportList(getActivity(),reportList);
                    listView.setAdapter(arrayAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }
    private void showUpdateDialog(final String sb_id, final String snum, final String sissue, final String cissue, final String cdate, final String stat ){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.status_update, null);
        builder.setView(view);

        final Spinner spinner = view.findViewById(R.id.spinner_status);
        final  TextView txtnum = view.findViewById(R.id.txtnum);
        txtnum.setText(snum );

        final Button update = view.findViewById(R.id.btn_update);
        builder.setTitle("close case "+snum);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scase = spinner.getSelectedItem().toString();
                updateCase(sb_id, snum, sissue, cissue, cdate,scase);

                alertDialog.dismiss();


            }
        });



    }
    private void updateCase(String id, String sbnumber, String issue_name, String customer_issue, String time, String stat){
        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SB").child(id);
        //Report report = new Report(id, sbnumber, issue_name, customer_issue,time, stat);
        //databaseReference.setValue(report);
        String view = String.valueOf(stat);

        Toast.makeText(getActivity(), "Complete", Toast.LENGTH_SHORT).show();
    }
    //search_menu


}
