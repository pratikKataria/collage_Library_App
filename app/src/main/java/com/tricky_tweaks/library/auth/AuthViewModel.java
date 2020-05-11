package com.tricky_tweaks.library.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tricky_tweaks.library.data.Student;

public class AuthViewModel  extends ViewModel {
    private AuthRepository authRepository;
    LiveData<Student> authenticatedStudentLiveData;
    LiveData<Student> createdStudentLiveData;

    public AuthViewModel() {
        authRepository = new AuthRepository();
    }

    void signUpWithEmail(String emailCredentials, String passwordCredentials) {
        authenticatedStudentLiveData = authRepository.firebaseSignupWithEmail(emailCredentials, passwordCredentials);
    }

    void signInWithEmail(String emailCredentials, String passwordCredentials) {
        authenticatedStudentLiveData = authRepository.firebaseSigninWithEmail(emailCredentials, passwordCredentials);
    }

    void createStudent(Student authStudent) {
        createdStudentLiveData = authRepository.createStudentInFirestoreIfNotExist(authStudent);
    }
}
