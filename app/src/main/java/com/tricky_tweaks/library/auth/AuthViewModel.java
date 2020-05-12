package com.tricky_tweaks.library.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tricky_tweaks.library.model.Student;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;
    LiveData<Student> authenticatedStudentLiveData;
    LiveData<Student> createdStudentLiveData;
    MutableLiveData<Integer> firebaseStateMutableLiveData = new MutableLiveData<>();

    public AuthViewModel() {
        authRepository = new AuthRepository() {
            @Override
            public void state(int iFirebaseState) {
                firebaseStateMutableLiveData.setValue(iFirebaseState);
            }
        };
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

    LiveData<Integer> getFirebaseMutableLiveData() {
        return firebaseStateMutableLiveData;
    }

}
