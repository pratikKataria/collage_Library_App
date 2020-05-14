package com.tricky_tweaks.library.scanner;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.tricky_tweaks.library.model.Student;

import java.util.ArrayList;

public class ScannerViewModel extends ViewModel {
    ScannerRepository repository;
    MutableLiveData<ArrayList<Student>> list = new MutableLiveData<>();

    public ScannerViewModel() {
        repository = new ScannerRepository();
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


}
