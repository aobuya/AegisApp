package com.example.aegisapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

//public class SbAdapter extends FirestoreRecyclerAdapter<Report, SbAdapter.SbHolder> {

public class SbAdapter extends RecyclerView.Adapter<SbAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Report> sb_reports;
    private List<Report> filtered_sb_reports;


    public SbAdapter(Context context , List<Report> sb_reports){
        this.context = context;
        this.sb_reports = sb_reports;
        //filtered_sb_reports = new ArrayList<>(sb_reports);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = sb_reports.get(position);
        holder.sbnum.setText(report.getSb_number());
        holder.issues.setText(report.getSb_issue());
        holder.number.setText(report.getCustomer_issue());
        holder.status.setText(report.getSb_status());
        holder.time.setText(report.getCurrent_date());

    }

    @Override
    public int getItemCount() {

        return sb_reports.size();
    }

    @Override
    public Filter getFilter() {
        return filtered_results;
    }
    private Filter filtered_results = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Report> filtered_list = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filtered_list.addAll(filtered_sb_reports);
            }else{
                String filter_pattern = constraint.toString().trim();
                for(Report report : filtered_sb_reports){
                    if(report.getSb_number().contains(filter_pattern)){
                        filtered_list.add(report);
                    }
                }
            }
            FilterResults results  = new FilterResults();
            results.values = filtered_list;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sb_reports.clear();
            sb_reports.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView sbnum;
        TextView number;
        TextView issues;
        TextView time;
        TextView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sbnum = itemView.findViewById(R.id.txtnum);
            number = itemView.findViewById(R.id.txtrid);
            issues = itemView.findViewById(R.id.txtcus);
            time = itemView.findViewById(R.id.time_stamp);
            status = itemView.findViewById(R.id.case_status);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Report report = sb_reports.get(getAdapterPosition());
            Intent intent = new Intent(context, UpdateActivity.class);
            //intent.putExtra("Report", report);
            context.startActivity(intent);
        }
    }


}

