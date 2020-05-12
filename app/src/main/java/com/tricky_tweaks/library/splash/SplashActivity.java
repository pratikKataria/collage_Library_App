package com.tricky_tweaks.library.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.tricky_tweaks.library.MainActivity;
import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.databinding.ActivitySplashBinding;
import com.tricky_tweaks.library.profile.ProfileActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        SplashViewModel splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        splashViewModel.checkIfUserIsAuthenticated();

        splashViewModel.getIsStudentAuthenticatedLiveData().observe(this, student -> {
            if (student.isAuthenticated) {
                splashViewModel.checkIfEnrollmentNumberPresent();
            }
        });

        splashViewModel.getIsEnrollmentNumberExist().observe(this, aBoolean -> {
            if (aBoolean) {
                start(MainActivity.class);
            } else {
                start(ProfileActivity.class);
            }
        });
    }

    void start(Class<? extends AppCompatActivity> token) {
        Intent intent = new Intent(SplashActivity.this, token);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
