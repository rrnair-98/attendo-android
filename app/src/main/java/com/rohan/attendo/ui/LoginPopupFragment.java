package com.rohan.attendo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rohan.attendo.MainActivity;
import com.rohan.attendo.R;
import com.rohan.attendo.api.models.requests.LoginRequest;
import com.rohan.attendo.api.models.response.AccessToken;
import com.rohan.attendo.api.retrofit.RetrofitApiClient;
import com.rohan.attendo.api.retrofit.Reverberator;

public class LoginPopupFragment extends DialogFragment implements Reverberator {

    private EditText email, password;
    private Button buttonOk;
    private static MainActivity mainActivityRef;
    private LoginPopupFragmentCallback callback;
    private RetrofitApiClient client;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, true);

        this.client = RetrofitApiClient.getInstance();
        this.email = view.findViewById(R.id.userEmail);
        this.password = view.findViewById(R.id.userPassword);
        this.buttonOk = view.findViewById(R.id.buttonSigninOk);
        this.buttonOk.setOnClickListener((View v1)->{
            this.client.login(new LoginRequest(this.email.getText().toString(), this.password.getText().toString()), this);
        });
        
        return view;
    }

    /** Reverberator callback **/
    @Override
    public void reverb(Object data, int httpResponseCode) {
        if(httpResponseCode == 200){
            this.callback.onLoginSuccess((AccessToken) data);
        
        }else 
            this.callback.onLoginFailed();
    }
    /** End of reverberator Callbacks **/
    
    
    public static LoginPopupFragment getInstance(MainActivity mainActivity, LoginPopupFragmentCallback callback){
        LoginPopupFragment fragment = new LoginPopupFragment();
        fragment.callback = callback;
        mainActivityRef = mainActivityRef == null? mainActivity: mainActivityRef;

        return fragment;
    }

    public interface LoginPopupFragmentCallback{
        public void onLoginSuccess(AccessToken accessToken);
        public void onLoginFailed();
    }



}
