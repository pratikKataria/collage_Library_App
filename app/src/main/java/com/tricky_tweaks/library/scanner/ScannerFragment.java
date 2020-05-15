package com.tricky_tweaks.library.scanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderFragment;
import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.databinding.FragmentScannerBinding;
import com.tricky_tweaks.library.utils.Constants;
import com.tricky_tweaks.library.utils.FragmentQRCodeReader;

/**
 * created by pratik katariya
 * on fri, 15 may 2020
 * during covid-19
 * at 4:55 am
 */
public class ScannerFragment extends FragmentQRCodeReader {

    //default generated binding Class
    private FragmentScannerBinding scannerBinding;
    //custom api barcode-reader class for reading qr codes in fragment
    private BarcodeReaderFragment barcodeReaderFragment;
    //keep track of if scanner is scanning or not
    private ObservableBoolean isScanning = new ObservableBoolean(true);
    private ScannerViewModel viewmodel;

    public ScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //initialize view-model
        initViewModel();
        //setup binding
        scannerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scanner, container, false);
        //set lifecycle owner is important
        //without setting up the lifecycle owner view won't get
        //updated using live data
        scannerBinding.setLifecycleOwner(getActivity());
        //set viewmodel to the xml binding
        scannerBinding.setScannerViewModel(viewmodel);
        //set scanning to the xml binding
        scannerBinding.setIsScanning(isScanning);

        initScanner();

        return scannerBinding.getRoot();
    }

    //initialize view model for fragment using ViewModelProvider
    private void initViewModel() {
        viewmodel = new ViewModelProvider(getActivity()).get(ScannerViewModel.class);
        viewmodel.createList();
    }

    //initialize barcode reader fragment
    private void initScanner() {
        /*
         * since barcodeReaderFragment is the child fragment in the ScannerFragment therefore
         * it is get using getChildfFragmentManager
         * setting up the custom bar code listener
         */
        barcodeReaderFragment = (BarcodeReaderFragment) getChildFragmentManager().findFragmentById(R.id.scannerFragment);
        barcodeReaderFragment.setListener(this);
        scannerBinding.mbScanner.setOnClickListener(n -> scanOnceMore());
    }

    //start it invoked in button click for second time
    private void scanOnceMore() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (isScanning.get()) fragmentTransaction.remove(barcodeReaderFragment);
        else fragmentTransaction.replace(R.id.scannerFragment, barcodeReaderFragment);
        isScanning.set(!isScanning.get());
        fragmentTransaction.commit();
    }

    /**
     * this method is override form FragmentQRCodeReader
     * it contains the scan result of qr code
     * @param barcode
     */
    @Override
    public void onScanned(Barcode barcode) {
        super.onScanned(barcode);

        isScanning.set(false);

        //on Scan completed close the scanner
        if (!isScanning.get()) {
            getChildFragmentManager()
                    .beginTransaction()
                    .remove(getChildFragmentManager().findFragmentById(R.id.scannerFragment))
                    .commit();
        }

        if (barcode.rawValue.equals(Constants.IQRCode.ENTER)) {
            viewmodel.updateScannedValueWhenEnter();
        } else if (barcode.rawValue.equals(Constants.IQRCode.EXIT)) {
            viewmodel.updateScannedValueWhenExist();
        } else {
            Toast.makeText(getActivity(), "no action specified", Toast.LENGTH_SHORT).show();
        }
        
    }
}
