package com.tricky_tweaks.library.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.tricky_tweaks.library.utils.SimpleTextWatcher;

public class ProfileViewModel extends ViewModel {

    private ProfileRepository profileRepository;
    private MutableLiveData<String> enrollmentNumberMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateSuccessful = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

    public ProfileViewModel() {
        profileRepository = new ProfileRepository() {
            @Override
            public void loading(boolean isLoading) {
                isLoadingMutableLiveData.setValue(isLoading);
            }
        };
    }


    public void updateEnrollmentNumber() {
        String enrollmentNumber = enrollmentNumberMutableLiveData.getValue();
        if (enrollmentNumber != null && !enrollmentNumber.isEmpty() && enrollmentNumber.length() > 8) {
             updateSuccessful = profileRepository.firebaseUpdateEnrollmentNumber(enrollmentNumber);
        }
    }

    public LiveData<String> getEnrollmentNumberLiveData() {
        return enrollmentNumberMutableLiveData;
    }

    public LiveData<Boolean> getUpdateStudentLiveData() {
        return updateSuccessful;
    }

    public LiveData<Boolean> getIsLoadingMutableLiveData() {
        return isLoadingMutableLiveData;
    }

    public SimpleTextWatcher getEnrollmentNumberMutableLiveData() {
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enrollmentNumberMutableLiveData.setValue(s.toString());
            }
        };
    }
}
