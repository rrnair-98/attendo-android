package com.rohan.attendo.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.rohan.attendo.api.chirp.ChirpWrapper;
import com.rohan.attendo.api.models.response.AttendanceToken;

public class WhistleBlower extends Service {
    public static String ACTION_START_CHIRPING = "com.rohan.attendo.services.WhistleBlower.start_chirping";
    public static String ACCESS_TOKEN_EXTRA = "accessTokenExtra";
    public static String TAG = "WhistleBlower";
    private boolean mKeepChirping;
    public WhistleBlower() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            Log.d(TAG, "Intent Received, ACTION = "+intent.getAction());
            if(intent.getAction().equals(ACTION_START_CHIRPING)){
                if(ChirperThread.canScheduleShirper()){
                    Log.d(TAG, "Initializing Thread for Chirping= "+intent.getStringExtra(ACCESS_TOKEN_EXTRA));
                    ChirperThread.scheduleChirper(this, intent.getStringExtra(ACCESS_TOKEN_EXTRA));
                }
            }
        }
        return START_STICKY;
    }

    private static class ChirperThread extends Thread{
        private boolean mKeepChirping;
        private ChirpWrapper mChirpWrapper;
        private String mAttendanceToken;
        private Context mContext;

        private static ChirperThread sChirperThread;

        private ChirperThread(String attendanceToken){
            mChirpWrapper = ChirpWrapper.getInstance();
            mAttendanceToken = attendanceToken;
        }

        public static boolean canScheduleShirper(){
            return sChirperThread==null || !sChirperThread.isAlive();
        }

        public static void scheduleChirper(Context context, String attendanceToken){
            if(canScheduleShirper()){
                sChirperThread = new ChirperThread(attendanceToken);
                sChirperThread.mContext = context;
                sChirperThread.start();
            }
            else{
                Log.d(TAG, "Chirper Already In Process");
            }
        }

        synchronized private void setKeepChirping(boolean keepChirping){
            mKeepChirping = keepChirping;
        }

        synchronized private boolean getKeepChirping(){
            return mKeepChirping;
        }

        public void run(){
            setKeepChirping(true);
            ChirpWrapper.ChirpDataReceiver chirpDataReceiver = new ChirpWrapper.ChirpDataReceiver() {
                @Override
                public void chirpDataReceived(String receivedData) {
                    if(receivedData.equals(mAttendanceToken)){
                        Log.d(TAG, "STOPPED Chirping");
                        setKeepChirping(false);
                        showToast("Attendance Marked");
                    }
                }
            };
            mChirpWrapper.addChirpDataReceiver(chirpDataReceiver);
            showToast("Marking you present");
            while (getKeepChirping()){
                mChirpWrapper.sendData(mAttendanceToken);
                Log.d(TAG, "SENT: "+mAttendanceToken);
                sleepFor(5000);
            }
            mChirpWrapper.removeChirpDataReceiver(chirpDataReceiver);
        }

        public void sleepFor(long millis){
            try {
                Thread.sleep(millis);
            }
            catch (InterruptedException ie){
                Log.d(TAG, "InterruptedException", ie);
            }
        }

        public void showToast(String message){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
