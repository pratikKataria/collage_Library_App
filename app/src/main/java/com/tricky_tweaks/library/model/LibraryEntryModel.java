package com.tricky_tweaks.library.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class LibraryEntryModel {
    private String name;
    private String enrollmentNo;
    private String enterTime;
    private String exitTime;
    @ServerTimestamp
    private Date timestamp;

    public LibraryEntryModel(String name, String enrollmentNo, String enterTime, String exitTime, Date timestamp) {
        this.name = name;
        this.enrollmentNo = enrollmentNo;
        this.enterTime = enterTime;
        this.exitTime = exitTime;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
