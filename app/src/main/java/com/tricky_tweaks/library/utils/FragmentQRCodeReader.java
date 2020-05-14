package com.tricky_tweaks.library.utils;

import android.util.SparseArray;

import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderFragment;

import java.util.List;

/**
 * created by pratik katariya
 * on fri, 15 may 2020
 * during covid-19
 * at 4:55 am
 * custom modified listener for the barcodeReaderListener
 *
 * modified form of Barcode-reader api
 * created to remove default methods with listener
 * which is no longer used
 */
public class FragmentQRCodeReader extends Fragment implements BarcodeReaderFragment.BarcodeReaderListener {


    @Override
    public void onScanned(Barcode barcode) {

    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }
}
