package com.tricky_tweaks.library.main;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.tricky_tweaks.library.interfaces.INavigation;

public class MainFragmentViewModel extends ViewModel {

    public MainFragmentViewModel() {}

    public void navigateToDestination(Context context, int destination) {
        NavHostFragment navHostfragment = (NavHostFragment) ((AppCompatActivity)context).getSupportFragmentManager().getPrimaryNavigationFragment();
        Fragment fragment = navHostfragment.getChildFragmentManager().getFragments().get(0);
        if (fragment instanceof  INavigation) {
            INavigation iNavigation = (MainFragment)fragment;
            iNavigation.navigateTo(destination);
        }
    }

}
