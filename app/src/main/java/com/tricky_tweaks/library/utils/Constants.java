package com.tricky_tweaks.library.utils;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.FAILED;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.LOADING;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.SUCCESS;

public abstract class Constants {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            IConstants.STUDENTS,
            IConstants.BOOKS,
            IConstants.TAG,
            IConstants.APP_CONFIG,
            IConstants.LIBRARY_ENTRY,
            IConstants.STUDENTS_ONLINE,
            IConstants.DATE_PATTERN
    })


    public @interface IConstants {
        String STUDENTS =  "students";
        String BOOKS = "books";
        String LIBRARY_ENTRY = "libraryEntry";
        String STUDENTS_ONLINE ="studentsOnline";
        String DATE_PATTERN = "dd-MM-YYYY 'at' HH:mm:ss";
        String TAG = "LibraryApp";
        String APP_CONFIG = "configurations";
        String FIRST_RUN = "firstRun";
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            LOADING,
            SUCCESS,
            FAILED
    })

    public @interface IFirebaseState {
        int LOADING = 2;
        int SUCCESS = 1;
        int FAILED = -1;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            IQRCode.ENTER,
            IQRCode.EXIT
    })

    public @interface IQRCode {
        String ENTER = "enter";
        String EXIT = "exit";
    }
}
