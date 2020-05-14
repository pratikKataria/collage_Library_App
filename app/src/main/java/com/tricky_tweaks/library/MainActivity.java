package com.tricky_tweaks.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.card.MaterialCardView;
import com.tricky_tweaks.library.databinding.ActivityMainBinding;
import com.tricky_tweaks.library.utils.CustomBounceInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Window window = getWindow();
        View view =  window.getDecorView();
        int flags  = view.getSystemUiVisibility();
        flags ^= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        view.setSystemUiVisibility(flags);
        window.setStatusBarColor(Color.BLACK);


        setAnimation(binding.materialCardView, 0);
        setAnimation(binding.materialCardView2, 50);
        setAnimation(binding.materialCardView3, 150);
        setAnimation(binding.materialCardView4, 200);
        setAnimation(binding.materialCardView5, 250);

        binding.imageView41.setOnClickListener(n -> {
            Drawable d = binding.imageView41.getDrawable();
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
            avd.start();
        });
    }

    void setAnimation(View view, int duration) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_bounce);
        animation.setInterpolator(new CustomBounceInterpolator(0.2, 20));
        animation.setStartOffset(duration);
        view.setAnimation(animation);
        animation.start();
    }
}
