package com.tricky_tweaks.library.utils;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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

}
