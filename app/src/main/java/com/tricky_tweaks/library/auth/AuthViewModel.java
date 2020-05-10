package com.tricky_tweaks.library.auth;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tricky_tweaks.library.data.Student;

public class AuthViewModel  extends ViewModel {
    LiveData<Student> authenticatedStudentLiveData;
    LiveData<Student> createdStudentLiveData;

    void signInWithEmail() {

    }

    void createStudent(Student authStudent) {

    }

}
