package com.tricky_tweaks.library.binding;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.databinding.BindingAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.tricky_tweaks.library.R;

public class LottieBinding {
    @BindingAdapter("setProgress")
    public static void setLottieProgress(LottieAnimationView lottieAnimationView, int textLength) {
        if (textLength == 0) {
            lottieAnimationView.setProgress(0);
            lottieAnimationView.setVisibility(View.GONE);
            return;
        }

        if (lottieAnimationView.getVisibility() == View.GONE) {
            lottieAnimationView.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(lottieAnimationView.getContext(), R.anim.btn_pop_anim);
            lottieAnimationView.setAnimation(animation);
            animation.start();
        }


        if (textLength == -1) {
            lottieAnimationView.setMinAndMaxProgress(.27F, .45F);
            lottieAnimationView.playAnimation();
            lottieAnimationView.setRepeatCount(0);
        }
        else if (textLength > 0 && textLength < 62 ) {
            lottieAnimationView.setMinAndMaxProgress(.0F, .25F);
            lottieAnimationView.playAnimation();
            lottieAnimationView.setRepeatCount(0);
        } else if (textLength > 62) {
            lottieAnimationView.setMinAndMaxProgress(.65F, .90F);
            lottieAnimationView.playAnimation();
            lottieAnimationView.setRepeatCount(0);
        }
    }

}
