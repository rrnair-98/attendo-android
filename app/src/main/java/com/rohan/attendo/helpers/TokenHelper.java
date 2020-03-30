package com.rohan.attendo.helpers;

import android.content.Context;

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
        try {
            FileOutputStream fos = this.mContext.openFileOutput("token", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(token);
            fos.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readToken(){
        try{
            FileInputStream fin = this.mContext.openFileInput("token");
            ObjectInputStream oin = new ObjectInputStream(fin);
            this.token = (AccessToken) oin.readObject();
            fin.close();
            oin.close();
        }catch (IOException|ClassNotFoundException e){
            this.token = null;
            e.printStackTrace();
        }
    }

    public boolean isTokenNull(){
        return this.token == null;
    }

    public AccessToken getToken(){
        return this.token;
    }



}
