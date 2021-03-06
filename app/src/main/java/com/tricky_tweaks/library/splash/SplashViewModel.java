package com.tricky_tweaks.library.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.tricky_tweaks.library.model.Student;

public class SplashViewModel extends ViewModel {
    private SplashRepository splashRepository;
    MutableLiveData<Boolean> isEnrollmentNumberExist = new MutableLiveData<>();
    private MutableLiveData<Student> isStudentAuthenticatedLiveData = new MutableLiveData<>();

    public SplashViewModel() {
        splashRepository = new SplashRepository();
    }

    void checkIfUserIsAuthenticated() {
        isStudentAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    void checkIfEnrollmentNumberPresent() {
       splashRepository.checkEnrollmentNumberInFirebaseStore(isStudentAuthenticatedLiveData.getValue().getUid(), data -> isEnrollmentNumberExist.setValue((Boolean) data));
    }

    LiveData<Boolean> getIsEnrollmentNumberExist() {
        return isEnrollmentNumberExist;
    }

    LiveData<Student> getIsStudentAuthenticatedLiveData() {
        return isStudentAuthenticatedLiveData;
    }
}
