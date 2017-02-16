package wigzo.com.wigzopushsamplenofcm;

import android.app.Activity;
import android.util.Log;

import com.wigzo.sdk.AbstractWigzoFcmListenerService;

import java.util.HashMap;

/**
 * Created by wigzo on 30/11/16.
 */

public class MyFirebaseMessagingService extends AbstractWigzoFcmListenerService {
    @Override
    protected Class<? extends Activity> getTargetActivity() {

        HashMap<String, String> payload = getWigzoNotificationPayload();
        Log.e("FCM", "Title: " + getWigzoNotificationTitle() + " Body: " + getWigzoNotificationBody());
        Log.e("FCM", "Payload: " + payload);
        return MainActivity.class;
    }
}
