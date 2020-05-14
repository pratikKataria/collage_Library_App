package com.tricky_tweaks.library.utils;

import androidx.annotation.IntDef;

import com.tricky_tweaks.library.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.util.Half.NaN;

public abstract class Destination {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            IDestination.TO_SCANNER
    })

    public @interface IDestination {
        int TO_SCANNER = R.id.action_mainFragment_to_scannerFragment;
        int TO_BOOK_ISSUE = NaN;
        int TO_REQUEST = NaN;
        int TO_BOOKS = NaN;
        int TO_LIBRARY_CARDS = NaN;
    }
}
