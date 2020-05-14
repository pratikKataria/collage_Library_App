package com.tricky_tweaks.library.scanner;

import androidx.lifecycle.MutableLiveData;

import com.tricky_tweaks.library.model.Student;

import java.util.ArrayList;

public class ScannerRepository {

    public MutableLiveData<ArrayList<Student>> getScannedListFromFirebaseFake() {
        MutableLiveData<ArrayList<Student>> listOfStudent = new MutableLiveData<>();

        ArrayList<Student> list = new ArrayList<>();
        Student student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        student = new Student("name ", "email ", "203221", "dasfiads");
        list.add(student);
        listOfStudent.setValue(list);
        return listOfStudent;
    }

}
