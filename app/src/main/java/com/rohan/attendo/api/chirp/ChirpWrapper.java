package com.rohan.attendo.api.chirp;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;

import io.chirp.chirpsdk.ChirpSDK;
import io.chirp.chirpsdk.interfaces.ChirpEventListener;
import io.chirp.chirpsdk.models.ChirpError;

public class ChirpWrapper {
    private static ChirpWrapper chirpWrapper;
    private ChirpSDK chirpSDK;
    private Context mContext;
    private ChirpError chirpError;
    private ArrayList<ChirpDataReceiver> mChirpDataReceivers = new ArrayList<>();
    private ChirpEventListener chirpEventListener = new ChirpEventListener() {

        @Override
        public void onReceived(byte[] data, int channel) {
            if (data != null) {
                String identifier = bytesToHexString(data);
                for(int i = 0; i<mChirpDataReceivers.size(); i++){
                    mChirpDataReceivers.get(i).chirpDataReceived(identifier);
                }
                Log.v("ChirpSDK: ", "Received " + identifier);
            } else {
                Log.e("ChirpError: ", "Decode failed");
            }
        }

        @Override
        public void onSending(@NotNull byte[] bytes, int i) {
            System.out.println("ON SENDING");
        }

        @Override
        public void onSent(@NotNull byte[] bytes, int i) {
            System.out.println("ON SENT");
        }

        @Override
        public void onReceiving(int i) {
            System.out.println("ON RECEIVING");
        }

        @Override
        public void onStateChanged(int i, int i1) {
            System.out.println("ON STATE CHANGED");
        }

        @Override
        public void onSystemVolumeChanged(float old, float current) {
            System.out.println("ON SYSVOLCHANGED");
        }
    };

    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    private final static String CHIRP_APP_KEY = "1AFAA1289e1CBfeA7557dfcec";
    private final static String CHIRP_APP_SECRET = "e19FEA88cC55abB6d63B7b3E01761EFA874b7FA60Bd976aF73";
    private final static String CHIRP_APP_CONFIG = "QfVUBxJ2LrRDrTLNaJSeYAKoPzddhKUhxby+aGiIfYjadT8gbnmLtmfNaemW3rm8JON4knN15JC6PiVZVfaa/a0iybafpBvZsUI5feRFYffexUNo+1zbLlaEawxvlKXbYf3V3tOhDjEGQnhxaJ2RlgvKHAP1dZTvhgVbPHk2z55S863hnDg0CheNs9c/QVarST4D8Lnd4lNuuPbzO0fibJRdUSk0LdaANCW/sI+af/KZDTpnuh0Spf2hV00bCy+j1Iq6/Gh9agMRNTrYsaVHAzUZUjAV2LMt0b9g0IbkEBfviLjzCmX0/SuTh9ie7RKitQbalWyqjSyOmRxG83ViQXzCVPgb6OeNdlouYzOfyZozZgU2M7QkGtXUIKTsDK2lhU6nlo/wVJtyp+WsUQY02UvMoZzQFk+tTeGmRAathaaBqaMfxYarWJQZRDqkxvb2SojTYYMZBzNSZuD2ua8wKTU/G1SB91QigZ0iVqO3MU6D6LM9XnfyKJZa/9FDGzm0WJ7yQG/Aqk1lutw7MLxX8JlHpTv+4k9rQiI5gBo/d6lLEtU8vrpaC92EOnFrFA5c3vIg6reaz0KlIO201eNWEByyjD9zl5hytLAgGyu82bnt06NnbTuPqE+0kwhU7CiQfLEfn6PQcjlremx3A7xc3fqz6YCP8grm2iYFpDQ80srficG0qK8ng/WE2ckzUQ7WPs9nYHQ9gjAEJ4F0KP0Vij8Rbs4LMIWZoAmeBwhoyqSXibIezZ7gaDhFHCT+LZNauTcej4Dn+Hmq41Kny1ndIqsjG7TzWwZItJAP5El8N4DdgghbKJv/rhfkMJBXzvIeemwtEGXJlRS8ECOVG5zddwggIcazv5l9+DZhkCIEw98b4T69GmJ0GT7UWkIJ/JPe19iwtjRPrSfquo/KVUkQ135C3OXdSoJkeKVV1IpOopiMrcfdwetjZNMSi4kXVmpNYFRE4D1EdAr+hPM+9rFXHhed+nrR2+C98Rbr30L1YmeT1hsFsnOHdM3pzhS0c61rwjDI9WAzaeMgRMu7B47LM09piuA8khNlalCS7N6hpL/F2zuLBy/oUx5PHqi8EMjGy0wbgr9h/65OLdBj3STwAH8tZkhy/imJ6521qqONqwz0a84cEyVkCWK3v9ZiRBOw+cqsTVIVaDNJR0lq+REvrA==";

    private ChirpWrapper(Context context){
        this.mContext = context;
        this.chirpSDK = new ChirpSDK(this.mContext, CHIRP_APP_KEY, CHIRP_APP_SECRET);
        this.chirpError = chirpSDK.setConfig(CHIRP_APP_CONFIG);
        this.chirpSDK.setListener(this.chirpEventListener);
        if (chirpError.getCode() == 0) {
            Log.v("ChirpSDK: ", "Configured ChirpSDK");
        } else {
            Log.e("ChirpError: ", chirpError.getMessage());
        }
    }

    public static ChirpWrapper getInstance(Context c){
        chirpWrapper = chirpWrapper == null? new ChirpWrapper(c): chirpWrapper;
        return chirpWrapper;
    }

    public static ChirpWrapper getInstance(){
        return chirpWrapper;
    }

    public void addChirpDataReceiver(ChirpDataReceiver chirpDataReceiver){
        mChirpDataReceivers.add(chirpDataReceiver);
    }

    public void removeChirpDataReceiver(ChirpDataReceiver chirpDataReceiver){
        mChirpDataReceivers.remove(chirpDataReceiver);
    }

    public void sendData(String data){
        byte[] payload = hexStringToBytes(data);

        ChirpError error = chirpSDK.send(payload);
        if (error.getCode() > 0) {
            Log.e("ChirpError: ", error.getMessage());
        } else {
            Log.v("ChirpSDK: ", "Sent " + data);
        }
    }

    public void stop(){
        chirpSDK.stop();
        try{
            chirpSDK.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len/2];

        for(int i = 0; i < len-1; i+=2){
            data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }

        return data;
    }

    public ChirpSDK getChirpSDK(){
        return this.chirpSDK;
    }

    public interface ChirpDataReceiver{
        void chirpDataReceived(String receivedData);
    }

}
