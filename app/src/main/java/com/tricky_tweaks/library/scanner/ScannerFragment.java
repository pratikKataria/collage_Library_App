package com.tricky_tweaks.library.scanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.databinding.FragmentScannerBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment {

    public ScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentScannerBinding fragmentScannerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scanner, container, false);

        return fragmentScannerBinding.getRoot();
    }
}
