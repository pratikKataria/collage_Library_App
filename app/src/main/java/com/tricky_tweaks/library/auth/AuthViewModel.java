package com.tricky_tweaks.library.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tricky_tweaks.library.model.Student;
import com.tricky_tweaks.library.utils.LogMessage;

public class AuthViewModel extends ViewModel {
    private AuthRepository authRepository;
    LiveData<Student> authenticatedStudentLiveData;
    LiveData<Student> createdStudentLiveData;
    MutableLiveData<Boolean> loadingStateLiveDataMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> errorWhileLoadingMutableLiveData = new MutableLiveData<>();

    public AuthViewModel() {
        LogMessage.logErrorMessage("constructor invoked");
        authRepository = new AuthRepository() {
            @Override
            public void loading(boolean isLoading) {
                loadingStateLiveDataMutableLiveData.setValue(isLoading);
            }

            @Override
            public void error(String message) {
                errorWhileLoadingMutableLiveData.setValue(message);
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

    LiveData<Boolean> getIsLoading() {
        return loadingStateLiveDataMutableLiveData;
    }

    LiveData<String> getErrorLoading() {
        return errorWhileLoadingMutableLiveData;
    }

}
