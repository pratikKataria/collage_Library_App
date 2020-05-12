package com.tricky_tweaks.library.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tricky_tweaks.library.model.Student;
import com.tricky_tweaks.library.utils.LogMessage;

import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.FAILED;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.LOADING;

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
            public void state(int iFirebaseState) {
                if (iFirebaseState == FAILED) {
                    errorWhileLoadingMutableLiveData.setValue("Authentication failed");
                } else if (iFirebaseState == LOADING) {
                    loadingStateLiveDataMutableLiveData.setValue(true);
                }
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
