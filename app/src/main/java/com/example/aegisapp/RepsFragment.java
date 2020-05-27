package com.example.aegisapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RepsFragment extends Fragment {
    private FirebaseFirestore db;
    //private CollectionReference sbReports = db.collection("Sb Issues");
    private SbAdapter adapter;
    private RecyclerView recyclerView;
    private List<Report> reportList;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        //swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        reportList = new ArrayList<>();
        adapter = new SbAdapter(getActivity(), reportList);

        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        CollectionReference sbs  = db.collection("Sb Issues");
        Query orderedList = sbs.orderBy("current_date", Query.Direction.DESCENDING);

        orderedList.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressBar.setVisibility(View.GONE);
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list){
                        Report report = d.toObject(Report.class);
                        //report.setId(d.getId());
                        reportList.add(report);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

        });

        return view;
    }
}
