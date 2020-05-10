package com.tricky_tweaks.library.auth;

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

import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.data.LoginViewModel;
import com.tricky_tweaks.library.databinding.ActivityMainBinding;

public class AuthActivity extends AppCompatActivity {

    boolean isLoginShown = false;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LoginViewModel loginViewModel = new LoginViewModel();
        binding.setViewModel(loginViewModel);

        SharedPreferences preferences = getSharedPreferences("permissions", 0);
        SharedPreferences.Editor editor = preferences.edit();

        if (!preferences.getBoolean("FIRST_RUN", false)) {
            statingAnimation();
        }


    }

    //start animation
    private void statingAnimation() {
        ConstraintSet constraintSetGone = new ConstraintSet();
        ConstraintSet constraintSetVisible = new ConstraintSet();

        ConstraintLayout constraintLayout = findViewById(R.id.activity_main);
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
}
