package com.tricky_tweaks.library.main;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.databinding.FragmentMainBinding;
import com.tricky_tweaks.library.utils.CustomBounceInterpolator;


public class MainFragment extends Fragment {

    private FragmentMainBinding fragmentMainBinding;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMainBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_main,
                container,
                false
        );


        setAnimation(fragmentMainBinding.materialCardView, 0);
        setAnimation(fragmentMainBinding.materialCardView2, 50);
        setAnimation(fragmentMainBinding.materialCardView3, 150);
        setAnimation(fragmentMainBinding.materialCardView4, 200);
        setAnimation(fragmentMainBinding.materialCardView5, 250);

        return fragmentMainBinding.getRoot();
    }

    void setAnimation(View view, int duration) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_bounce);
        animation.setInterpolator(new CustomBounceInterpolator(0.2, 20));
        animation.setStartOffset(duration);
        view.setAnimation(animation);
        animation.start();
    }
}
