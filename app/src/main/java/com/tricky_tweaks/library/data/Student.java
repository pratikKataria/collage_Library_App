package com.tricky_tweaks.library.data;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String email;
    private String enrollmentNo;
    private String uid;
    public transient boolean isAuthenticated;
    public transient boolean isNew;
    public transient boolean isCreated;

    public Student() {}

    public Student(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public Student(String name, String email, String enrollmentNo, String uid) {
        this.name = name;
        this.email = email;
        this.enrollmentNo = enrollmentNo;
        this.uid = uid;
    }

    public Student(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
