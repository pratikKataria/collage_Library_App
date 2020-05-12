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
            IConstants.APP_CONFIG
    })


    public @interface IConstants {
        String STUDENTS =  "students";
        String BOOKS = "books";
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
        int LOADING = 0;
        int SUCCESS = 1;
        int FAILED = -1;
    }

}
