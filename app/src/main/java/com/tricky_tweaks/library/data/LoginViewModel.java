package com.tricky_tweaks.library.data;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.tricky_tweaks.library.BR;

public class LoginViewModel extends BaseObservable {
    private String password;
    private String email;
    private String error;
    private boolean isEnableBtn;
    private int lottieCurrProgress;

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
    public String getError() {
        return error;
    }

    @Bindable
    public void setError(String error) {
        this.error = error;
        notifyPropertyChanged(BR.error);
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
    public int getLottieCurrProgress() {
        return lottieCurrProgress;
    }

    @Bindable
    public void setLottieCurrProgress(int lottieCurrProgress) {
        this.lottieCurrProgress = lottieCurrProgress;
        notifyPropertyChanged(BR.lottieCurrProgress);
    }

    public TextWatcher getEmailWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEmail(s.toString());
                setLottieCurrProgress(getEmail().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e(LoginViewModel.class.getName(), Patterns.EMAIL_ADDRESS.matcher(s.toString().toLowerCase()).matches() + " " + s.toString());

                if (getEmail().length() == 0) {
                    setIsEnableBtn(false);
                    setLottieCurrProgress(0);
                }else if (Patterns.EMAIL_ADDRESS.matcher(s.toString().toLowerCase()).matches()) {
                    setError("");
                    setLottieCurrProgress(getEmail().length());
                    if (getPassword().length() >= 6)
                        setIsEnableBtn(true);
                } else {
                    setError("email malformed");
                    setIsEnableBtn(false);
                    setLottieCurrProgress(-1);
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
                if (Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches() && getPassword().length() > 6) {
                    setIsEnableBtn(true);
                } else {
                    if (password == null || password.isEmpty()) {
                        setError("Enter password");
                        setIsEnableBtn(false);
                    } else if (password.equals("password")) {
                        setError("very bad password");
                        setIsEnableBtn(false);
                    } else if (password.length() < 6) {
                        setError("should be greater then six");
                        setIsEnableBtn(false);
                    } else {
                        setError("");
                        setIsEnableBtn(true);
                    }
                }

            }
        };
    }
}
