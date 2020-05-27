package com.rohan.attendo.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.rohan.attendo.api.models.response.AccessToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class persists the token to a local file.
 * It is the responsibility of the caller to call the persistToken method to store it.
 */
public class TokenHelper {
    private static final String SHARED_PREF_KEY = "ACCESS_TOKEN";

    private AccessToken token;
    private Context mContext;
    private static TokenHelper mRef;
    private TokenHelper(Context context){
        this.mContext = context;
    }

    public static TokenHelper getInstance(Context ctx){
        mRef = mRef == null? new TokenHelper(ctx): mRef;
        return mRef;
    }

    public static TokenHelper getInstance(){
        return mRef;
    }

    public void persistToken(AccessToken token){
        this.token = token;
        SharedPreferences.Editor editor = mContext.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE).edit();
        String keys[] = { "id", "userId", "accessTokenExpiry", "role", "refreshTokenExpiresAt", "accessToken", "refreshToken"};
        for(int i = 0;i<keys.length; i++)
            editor.remove(keys[i]);
        Log.d("RAHUL", "ROLE = "+token.getRole());
        editor.putLong("id", token.getId());
        editor.putLong("userId", token.getUserId());
        editor.putLong("accessTokenExpiry", token.getCreatedAt());
        editor.putInt("role", token.getRole());
        editor.putLong("refreshTokenExpiresAt", token.getRefreshTokenExpiresAt());
        editor.putString("accessToken", token.getAccessToken());
        editor.putString("refreshToken", token.getRefreshToken());
        editor.apply();
    }

    public void readToken(){
        SharedPreferences sharedPref = mContext.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        token = new AccessToken();
        token.setId(sharedPref.getLong("id", -100));
        token.setUserId(sharedPref.getLong("userId", 0));
        token.setRole(sharedPref.getInt("role", -1000));
        token.setCreatedAt(sharedPref.getLong("accessTokenExpiry", 0));
        token.setRefreshTokenExpiresAt(sharedPref.getLong("refreshTokenExpiresAt", 0));
        token.setAccessToken(sharedPref.getString("accessToken", null));
        if (token.getAccessToken() == null || token.getAccessToken().length() == 0){
            token = null;
            return;
        }
    }

    public boolean isTokenNull(){
        return this.token == null;
    }

    public AccessToken getToken(){
        return this.token;
    }



}
