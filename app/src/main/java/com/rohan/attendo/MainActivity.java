package com.rohan.attendo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.rohan.attendo.api.chirp.ChirpWrapper;
import com.rohan.attendo.api.models.requests.LoginRequest;
import com.rohan.attendo.api.models.response.AccessToken;
import com.rohan.attendo.api.retrofit.RetrofitApiClient;
import com.rohan.attendo.api.retrofit.Reverberator;
import com.rohan.attendo.helpers.TokenHelper;
import com.rohan.attendo.ui.LectureListFragment;
import com.rohan.attendo.ui.LoginPopupFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import io.chirp.chirpsdk.models.ChirpError;

public class MainActivity extends AppCompatActivity implements LoginPopupFragment.LoginPopupFragmentCallback {


    private static final String TAG = "attendo.MAinActivity";
    private FragmentManager fragmentManager;
    private TokenHelper tokenHelper;

    private ChirpWrapper chirpWrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.chirpWrapper = ChirpWrapper.getInstance(this);
        setSupportActionBar(toolbar);

        RetrofitApiClient.getInstance(this).login(new LoginRequest("anton@gmail.com", "password"), new Reverberator() {
            @Override
            public void reverb(Object data, int httpResponseCode) {
                if(httpResponseCode == 200){
                    Log.d(TAG, ((AccessToken) data).getAccessToken());
                }else
                    Log.e(TAG, httpResponseCode+" Failed to Login");
            }
        });
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * LoginPopupFragment callbacks
     */
    @Override
    public void onLoginSuccess(AccessToken accessToken) {
        this.tokenHelper.persistToken(accessToken);
        // hide current login fragment
        Snackbar.make(this.findViewById(R.id.coordinatorSnackbarContainer), "Logged in", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailed() {
        Snackbar.make(this.findViewById(R.id.coordinatorSnackbarContainer), "Login failed, please try again", Snackbar.LENGTH_SHORT).show();
    }

    /** End of LoginPopupFragment callbacks**/

    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        }
        else {
            // Start ChirpSDK sender and receiver, if no arguments are passed both sender and receiver are started
            ChirpError error = chirpWrapper.getChirpSDK().start(true, true);
            if (error.getCode() > 0) {
                Log.e("ChirpError: ", error.getMessage());
            } else {
                Log.v("ChirpSDK: ", "Started ChirpSDK");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ChirpError error = this.chirpWrapper.getChirpSDK().start();
                    if (error.getCode() > 0) {
                        Log.e("ChirpError: ", error.getMessage());
                    } else {
                        Log.v("ChirpSDK: ", "Started ChirpSDK");
                    }
                }
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.chirpWrapper.getChirpSDK().stop();
    }


    private void init(){
//        this.fragmentManager = this.getSupportFragmentManager();
//        this.tokenHelper = TokenHelper.getInstance(this);
//        this.tokenHelper.readToken();
//        if(this.tokenHelper.isTokenNull()){
//            //todo show popup dialog
//        }
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentHolder, new LectureListFragment(), TAG).commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.chirpWrapper.stop();
    }
}
