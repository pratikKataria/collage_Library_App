package com.tricky_tweaks.library.scanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
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
    private FragmentScannerBinding fragmentScannerBinding;
    //custom api barcode-reader class for reading qr codes in fragment
    private BarcodeReaderFragment barcodeReaderFragment;
    //keep track of if scanner is scanning or not
    private boolean isScanning = true;
    private ScannerViewModel scannerViewModel;

    public ScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentScannerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scanner, container, false);

        initViewModel();

        initScanner();

        fragmentScannerBinding.mbScanner.setOnClickListener(n -> scanOnceMore());

        return fragmentScannerBinding.getRoot();
    }

    //initialize view model for fragment using ViewModelProvider
    private void initViewModel() {
        scannerViewModel = new ViewModelProvider(getActivity()).get(ScannerViewModel.class);
        fragmentScannerBinding.setScannerViewModel(scannerViewModel);
        scannerViewModel.createList();
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
    }

    //start it invoked in button click for second time
    private void scanOnceMore() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (isScanning) fragmentTransaction.remove(barcodeReaderFragment);
        else fragmentTransaction.replace(R.id.scannerFragment, barcodeReaderFragment);
        isScanning = !isScanning;
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
        //on Scan completed close the scanner
        if (isScanning) {
            getChildFragmentManager()
                    .beginTransaction()
                    .remove(getChildFragmentManager().findFragmentById(R.id.scannerFragment))
                    .commit();
        }

        if (barcode.rawValue.equals(Constants.IQRCode.ENTER)) {
            scannerViewModel.updateScannedValueWhenEnter();
        } else if (barcode.rawValue.equals(Constants.IQRCode.EXIT)) {
            scannerViewModel.updateScannedValueWhenExist();
        } else {
            Toast.makeText(getActivity(), "no action specified", Toast.LENGTH_SHORT).show();
        }
        
    }
}
