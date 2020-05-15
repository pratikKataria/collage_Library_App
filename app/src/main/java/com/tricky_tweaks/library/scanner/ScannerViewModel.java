package com.tricky_tweaks.library.scanner;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.tricky_tweaks.library.model.LibraryEntryModel;
import com.tricky_tweaks.library.model.Student;
import com.tricky_tweaks.library.utils.LogMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.tricky_tweaks.library.utils.Constants.IConstants.DATE_PATTERN;

public class ScannerViewModel extends ViewModel {
    private ScannerRepository repository;
    private MutableLiveData<ArrayList<Student>> list = new MutableLiveData<>();
    private MutableLiveData<Integer> firebaseStateMutableLiveData = new MutableLiveData<>();

    //initialize repository
    public ScannerViewModel() {
        repository = new ScannerRepository() {
            @Override
            public void state(int iFirebaseState) {
                firebaseStateMutableLiveData.setValue(iFirebaseState);
                LogMessage.eMess("loading " + iFirebaseState);
            }
        };
    }

    public void createList() {
        list = repository.getScannedListFromFirebaseFake();
    }

    public LiveData<ArrayList<Student>> getList() {
        return list;
    }

    public RecyclerView.Adapter getAdapter(Context context) {
        return new ScannedRecyclerViewAdapter(list.getValue(), context);
    }

    //this method is will create a document for student as soon as he/she scan the
    //qr code this will also make the student online in library
    void updateScannedValueWhenEnter() {
        @SuppressLint("SimpleDateFormat") String enterTimeString = new SimpleDateFormat(DATE_PATTERN).format(new Date());
        LibraryEntryModel libraryEntryModel = new LibraryEntryModel("pratik katariya", "0832CS171121", enterTimeString, " ", new Date());
        repository.updateScannedValueWhenEnterInFirestoreDatabase(libraryEntryModel);
    }

    //this method will update the field 'exitTime' in the document created above
    void updateScannedValueWhenExist() {
        @SuppressLint("SimpleDateFormat") String exitTimeString = new SimpleDateFormat(DATE_PATTERN).format(new Date());
        repository.updateScannedValueWhenExistInFirestoreDatabase(exitTimeString);
    }

    public LiveData<Integer> getFirebaseStateLiveData() {
        LogMessage.eMess("loadingget "+ firebaseStateMutableLiveData.getValue());
        return firebaseStateMutableLiveData;
    }
}
