package com.tricky_tweaks.library.scanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.databinding.FragmentScannerBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment {

    private FragmentScannerBinding fragmentScannerBinding;

    public ScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentScannerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scanner, container, false);
        initViewModel();
        return fragmentScannerBinding.getRoot();
    }

    private void initViewModel() {
        ScannerViewModel scannerViewModel = new ViewModelProvider(getActivity()).get(ScannerViewModel.class);
        fragmentScannerBinding.setScannerViewModel(scannerViewModel);
        scannerViewModel.createList();
    }

}
