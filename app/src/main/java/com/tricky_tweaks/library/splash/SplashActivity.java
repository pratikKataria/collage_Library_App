package com.tricky_tweaks.library.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.tricky_tweaks.library.MainActivity;
import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.auth.AuthActivity;
import com.tricky_tweaks.library.profile.ProfileActivity;

public class SplashActivity extends AppCompatActivity {

    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_splash);

        initViewModel();
        checkAuth();
        checkEnrollmentNumber();
    }

    /* initializing view model for the activity */
    void initViewModel() {
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
    }

    /**
     * checking if student is authenticated in firebase Auth
     * if successful go to MainActivity else go to Login page
     */
    void checkAuth() {
        splashViewModel.checkIfUserIsAuthenticated();

        splashViewModel.getIsStudentAuthenticatedLiveData().observe(this, student -> {
            if (student.isAuthenticated) {
                splashViewModel.checkIfEnrollmentNumberPresent();
            } else {
                start(AuthActivity.class);
            }
        });
    }

    /**
     * if user is authenticate then check for enrollment number in firestore
     * if enrollment is present then go to main activity else go to the
     * enrollment number page
     */
    void checkEnrollmentNumber() {
        splashViewModel.getIsEnrollmentNumberExist().observe(this, isExist -> {
            if (isExist) {
                new Handler().postDelayed(() -> start(MainActivity.class), 500);
            } else {
                start(ProfileActivity.class);
            }
        });
    }

    /**
     * start accept the generic class which is destination to go
     * it only accept the class which extends the AppCompatActivity
     * @param token
     */
    void start(Class<? extends AppCompatActivity> token) {
        Intent intent = new Intent(SplashActivity.this, token);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
