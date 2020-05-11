package com.tricky_tweaks.library.model;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.tricky_tweaks.library.BR;

public class LoginViewModel extends BaseObservable {
    private String password;
    private String email;
    private boolean isEnableBtn;
    private int lottieProgress;
    private int lottiePassProgress;

    @Bindable
    public String getPassword() {
        return password != null ? password : "";
    }


    @Bindable
    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getEmail() {
        return email != null ? email.toLowerCase() : "";
    }

    @Bindable
    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public boolean getIsEnableBtn() {
        return isEnableBtn;
    }

    @Bindable
    public void setIsEnableBtn(boolean isEnableBtn) {
        this.isEnableBtn = isEnableBtn;
        notifyPropertyChanged(BR.isEnableBtn);
    }

    @Bindable
    public int getLottieProgress() {
        return lottieProgress;
    }

    @Bindable
    public void setLottieProgress(int lottieProgress) {
        this.lottieProgress = lottieProgress;
        notifyPropertyChanged(BR.lottieProgress);
    }

    @Bindable
    public int getLottiePassProgress() {
        return lottiePassProgress;
    }

    @Bindable
    public void setLottiePassProgress(int lottiePassProgress) {
        this.lottiePassProgress = lottiePassProgress;
        notifyPropertyChanged(BR.lottiePassProgress);
    }

    public TextWatcher getEmailWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEmail(s.toString());
                setLottieProgress(getEmail().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getEmail().length() == 0) {
                    setIsEnableBtn(false);
                    setLottieProgress(0);
                }else if (Patterns.EMAIL_ADDRESS.matcher(s.toString().toLowerCase()).matches()) {
                    setLottieProgress(getEmail().length());
                    if (getPassword().length() >= 6)
                        setIsEnableBtn(true);
                } else {
                    setIsEnableBtn(false);
                    setLottieProgress(-1);
                }
            }
        };
    }

    public TextWatcher getPasswordWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches() && getPassword().length() >= 6) {
                    setIsEnableBtn(true);
                    setLottiePassProgress(getPassword().length());
                } else {
                    if (password == null || password.isEmpty()) {
                        setIsEnableBtn(false);
                        setLottiePassProgress(0);
                    } else if (password.length() < 6) {
                        setIsEnableBtn(false);
                        setLottiePassProgress(getPassword().length());
                    } else {
                        setIsEnableBtn(false);
                        setLottiePassProgress(getPassword().length());
                    }
                }

            }
        };
    }
}
