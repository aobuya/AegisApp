package com.example.aegisapp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ReportList extends ArrayAdapter<Report> {

    private Activity activity;
    private List<Report> reportList;


    public ReportList(Activity activity, List<Report> reportList){
        super(activity, R.layout.list_layout, reportList);
        this.activity = activity;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item, null, true);

        TextView sbnum = listItemView.findViewById(R.id.txtnum);
        TextView number = listItemView.findViewById(R.id.txtrid);
        TextView issues = listItemView.findViewById(R.id.txtcus);
        TextView time = listItemView.findViewById(R.id.time_stamp);
        TextView status = listItemView.findViewById(R.id.case_status);
        //status.setBackgroundColor();
        Report report = reportList.get(position);

        number.setText(report.getCustomer_issue());
        issues.setText(report.getSb_issue());
        sbnum.setText(report.getSb_number());
        time.setText(report.getCurrent_date());
        status.setText(report.getSb_status());

        //change color of status
        String color_text = status.getText().toString();
        if(color_text == "Open"){
            status.setTextColor(Color.GREEN);
        }
        else if(color_text == "Closed"){
            status.setTextColor(Color.RED);
        }


        return listItemView;
    }
}
