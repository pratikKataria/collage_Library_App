package com.tricky_tweaks.library.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.tricky_tweaks.library.MainActivity;
import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.model.LoginViewModel;
import com.tricky_tweaks.library.databinding.ActivityMainBinding;
import com.tricky_tweaks.library.model.Student;

import static android.view.View.GONE;
import static com.tricky_tweaks.library.utils.Constants.IConstants.APP_CONFIG;
import static com.tricky_tweaks.library.utils.Constants.IConstants.FIRST_RUN;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.FAILED;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.LOADING;

public class AuthActivity extends AppCompatActivity {

    boolean isLoginShown = false;
    private ActivityMainBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LoginViewModel loginViewModel = new LoginViewModel();
        binding.setViewModel(loginViewModel);

        loadScreen();
        initAuthViewModel();
        initAuthButton();
        observeFirebaseState();
    }

    //initialize auth view model
    private void initAuthViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }


    private void initAuthButton() {
        binding.loginMbCreateAccount.setOnClickListener(n -> {
            signUp();
        });
        binding.mbLogin.setOnClickListener(n -> {
            signIn();
        });
    }

    //sign in using firebase auth
    private void signIn() {
        String emailCredentials = binding.getViewModel().getEmail();
        String passwordCredentials = binding.getViewModel().getPassword();

        authViewModel.signInWithEmail(emailCredentials, passwordCredentials);
        authViewModel.authenticatedStudentLiveData.observe(this, authenticatedStudent -> {
            if (authenticatedStudent.isAuthenticated) {
                startMainActivity(authenticatedStudent);
            }
        });
    }

    //sign up using firebase auth
    private void signUp() {
        String emailCredentials = binding.getViewModel().getEmail();
        String passwordCredentials = binding.getViewModel().getPassword();
        authViewModel.signUpWithEmail(emailCredentials, passwordCredentials);
        authViewModel.authenticatedStudentLiveData.observe(this, authenticatedStudent -> {
            if (authenticatedStudent.isNew) {
                createNewUser(authenticatedStudent);
            } else {
                startMainActivity(authenticatedStudent);
            }
        });
    }

    //upload new user document if student is newly registered
    private void createNewUser(Student authenticatedStudent) {
        authViewModel.createStudent(authenticatedStudent);
        authViewModel.createdStudentLiveData.observe(this, student -> {
            if (student.isCreated) {
                Toast.makeText(this, "welcome", Toast.LENGTH_SHORT).show();
                startMainActivity(authenticatedStudent);
            }
        });
    }

    //go to main activity
    void startMainActivity(Student student) {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        intent.putExtra("USER", student);
        startActivity(intent);
        finish();
    }

    //this method will load screen based on app instance
    private void loadScreen() {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_CONFIG, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean isFirstRun = sharedPreferences.getBoolean(FIRST_RUN, true);

        if (isFirstRun) {
            firstTimeUserScreen();
            editor.putBoolean(FIRST_RUN, !isFirstRun);
            binding.setFirstRun(true);
            editor.apply();
        }else {
            afterTimeUserScreen();
        }
    }

    //when the app is opened after first time
    private void afterTimeUserScreen(){
        ConstraintSet constraintSet = new ConstraintSet();
        ConstraintLayout constraintLayout = binding.activityMain;
        constraintSet.clone(constraintLayout);
        constraintSet.applyTo(constraintLayout);
    }

    //when app open first time  animation
    private void firstTimeUserScreen() {
        ConstraintSet constraintSetGone = new ConstraintSet();
        ConstraintSet constraintSetVisible = new ConstraintSet();

        ConstraintLayout constraintLayout = binding.activityMain;
        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AccelerateDecelerateInterpolator());
        transition.setDuration(1500);

        constraintSetGone.clone(constraintLayout);
        constraintSetVisible.clone(this, R.layout.start_screen_scene_one);

        constraintLayout.post(() -> {
            TransitionManager.beginDelayedTransition(constraintLayout, transition);
            constraintSetVisible.applyTo(constraintLayout);
        });

        constraintLayout.setOnClickListener(n -> {
            if (!isLoginShown) {
                TransitionManager.beginDelayedTransition(constraintLayout, transition);
                constraintSetGone.applyTo(constraintLayout);
                isLoginShown = true;

                new Handler().postDelayed(() -> {
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.btn_pop_anim);
                    binding.loginIvLoginImg.setVisibility(View.VISIBLE);
                    binding.loginEtEmail.setVisibility(View.VISIBLE);
                    binding.loginEtPass.setVisibility(View.VISIBLE);
                    binding.mbLogin.setVisibility(View.VISIBLE);
                    binding.loginForgetPassword.setVisibility(View.VISIBLE);

                    binding.mbLogin.setAnimation(animation);
                    binding.loginMbCreateAccount.setVisibility(View.VISIBLE);
                    binding.loginMbCreateAccount.setAnimation(animation);
                    animation.start();

                    binding.imageView11.animate().translationY(200).setDuration(500).setInterpolator(new LinearInterpolator()).start();
                    binding.imageView12.animate().translationY(300).setDuration(500).setInterpolator(new LinearInterpolator()).start();
                }, 800);
            }
        });
    }

    void observeFirebaseState() {
        authViewModel.getFirebaseMutableLiveData().observe(this, task ->{
            if (task == LOADING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else if (task == FAILED) {
                binding.progressBar.setVisibility(GONE);
                binding.errorTextView.setText("Failed to Loading");
            }
        });
    }
}
