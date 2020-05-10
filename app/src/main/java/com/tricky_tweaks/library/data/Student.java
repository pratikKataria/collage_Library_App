package com.tricky_tweaks.library.data;

public class Student {
    private String name;
    private String email;
    private String password;

    public Student() {}

    /**
     * for register email and password
     * @param email
     * @param password
     */
    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * for uploading name to database
     * @param name
     */
    public Student(String name) {
        this.name = name;
    }

    /**
     * retrieving all the three data form database
     * @param name
     * @param email
     * @param password
     */
    public Student(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
