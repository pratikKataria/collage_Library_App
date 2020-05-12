package com.tricky_tweaks.library.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tricky_tweaks.library.utils.SimpleTextWatcher;

public class ProfileViewModel extends ViewModel {

    private ProfileRepository profileRepository;
    private MutableLiveData<String> enrollmentNumberMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> firebaseSateMutableLiveData = new MutableLiveData<>();

    public ProfileViewModel() {
        profileRepository = new ProfileRepository() {
            @Override
            public void state(int iFirebaseState) {
                firebaseSateMutableLiveData.setValue(iFirebaseState);
            }
        };
    }


    public void updateEnrollmentNumber() {
        String enrollmentNumber = enrollmentNumberMutableLiveData.getValue();
        if (enrollmentNumber != null && !enrollmentNumber.isEmpty() && enrollmentNumber.length() > 8) {
             profileRepository.firebaseUpdateEnrollmentNumber(enrollmentNumber);
        }
    }

    public LiveData<String> getEnrollmentNumberLiveData() {
        return enrollmentNumberMutableLiveData;
    }

    public LiveData<Integer> getFirebaseStateLiveData() {
        return firebaseSateMutableLiveData;
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
