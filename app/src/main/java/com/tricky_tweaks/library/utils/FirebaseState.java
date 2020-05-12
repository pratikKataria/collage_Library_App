package com.tricky_tweaks.library.utils;

public interface FirebaseState {
    void loading(boolean isLoading);
    void error(String message);
    void success(boolean isSuccessful);
}
