package com.tricky_tweaks.library.scanner;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.transition.MaterialContainerTransform;
import com.notbytes.barcode_reader.BarcodeReaderFragment;
import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.databinding.FragmentScannerBinding;
import com.tricky_tweaks.library.utils.Constants;
import com.tricky_tweaks.library.utils.FragmentQRCodeReader;

import static com.tricky_tweaks.library.utils.Constants.IConstants.APP_CONFIG;
import static com.tricky_tweaks.library.utils.Constants.IQRCode.ENTER;
import static com.tricky_tweaks.library.utils.Constants.IQRCode.EXIT;
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
    private ObservableBoolean isScanning = new ObservableBoolean();
    private ScannerViewModel viewmodel;

    public ScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(new MaterialContainerTransform());
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

        getChildFragmentManager()
                .beginTransaction()
                .remove(getChildFragmentManager().findFragmentById(R.id.scannerFragment))
                .commit();

        scannerBinding.mbScanner.setOnClickListener(n -> scanOnceMore());

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
         * it is get using getChildFragmentManager
         * setting up the custom bar code listener
         */

    }

    //start it invoked in button click for second time
    private void scanOnceMore() {

        barcodeReaderFragment = new BarcodeReaderFragment();
        barcodeReaderFragment.setListener(ScannerFragment.this);
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

        //on Scan completed close the scanner
        if (isScanning.get()) {
            getChildFragmentManager()
                    .beginTransaction()
                    .remove(getChildFragmentManager().findFragmentById(R.id.scannerFragment))
                    .commit();
        }

        isScanning.set(false);

        /*
        * this works when if user scan the exist
        * code without scanning the enter code
        */
        boolean waitingForEnterCode = !getUpdatedPendingEntryEvent();
        if (barcode.rawValue.equals(EXIT) && waitingForEnterCode) {
            String message = "Its look like that you haven't scan the Enter Code " + new String(Character.toChars(0x1F622));
            showAlertDialog(message);
            return;
        }

        /*
        * this works when user scan the enter code again
        * without scanning the exit code
        */
        boolean waitingForExitCode = getUpdatedPendingEntryEvent();
        if (barcode.rawValue.equals(ENTER) && waitingForExitCode) {
            String message = "you have already scan the Enter code First scan a Exist " + new String(Character.toChars(0x1F622));
            showAlertDialog(message);
            return;
        }

        if (barcode.rawValue.equals(Constants.IQRCode.ENTER)) {
            viewmodel.updateScannedValueWhenEnter();
            updatePendingEntryEvent(true);
        } else if (barcode.rawValue.equals(EXIT)) {
            viewmodel.updateScannedValueWhenExist();
            updatePendingEntryEvent(false);
        } else {
            Toast.makeText(getActivity(), "no action specified", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * custom message you want to show to the user
     * @param message
     */
    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("QR Scanner")
                .setMessage(message)
                .setPositiveButton("OKAY", (dialog, which) -> {
                    dialog.dismiss();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void updatePendingEntryEvent(boolean isEntryPending) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_CONFIG, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ENTRY_EVENT", isEntryPending);
        editor.apply();
    }

    boolean getUpdatedPendingEntryEvent(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_CONFIG, 0);
        return sharedPreferences.getBoolean("ENTRY_EVENT", false);
    }
}
