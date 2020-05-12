package com.tricky_tweaks.library.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tricky_tweaks.library.MainActivity;
import com.tricky_tweaks.library.R;
import com.tricky_tweaks.library.databinding.ActivityProfileBinding;

import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.FAILED;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.LOADING;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding activityProfileBinding;
    ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing view model
        initViewModel();
        //set content view through data binding
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        //setup life cycle owner
        activityProfileBinding.setLifecycleOwner(this);
        //setup with viewModel
        activityProfileBinding.setViewModel(profileViewModel);
        //observe changes to Enrollment Number
        observeFirebaseState();
    }

    void initViewModel() {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    void observeFirebaseState() {
        profileViewModel.getFirebaseStateLiveData().observe(this, task -> {
            if (task == LOADING) {
                Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }else if (task == FAILED) {
                Toast.makeText(this, "update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
