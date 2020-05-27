package com.example.aegisapp;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

public class Report {
    String sbid;
    String id;
    String sb_number;
    String sb_issue;
    String customer_issue;
    String current_date;
    String sb_status;
    String umail;


    public Report(){}

    public Report(String sbid, String id, String sb_number, String sb_issue, String customer_issue, String current_date, String sb_status, String umail) {
        this.sbid = sbid;
        this.id = id;
        this.sb_number = sb_number;
        this.sb_issue = sb_issue;
        this.customer_issue = customer_issue;
        this.current_date = current_date;
        this.sb_status = sb_status;
        this.umail = umail;
    }

    public String getSbid() {
        return sbid;
    }

    public String getId() {
        return id;
    }

    public String getSb_number() {
        return sb_number;
    }

    public String getSb_issue() {
        return sb_issue;
    }

    public String getCustomer_issue() {
        return customer_issue;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public String getSb_status() {
        return sb_status;
    }

    public String getUmail() {
        return umail;
    }
}
