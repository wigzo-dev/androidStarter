package wigzo.com.wigzopushsamplenofcm;

import android.app.Activity;

import com.wigzo.sdk.AbstractWigzoFcmListenerService;

/**
 * Created by wigzo on 30/11/16.
 */

public class MyFirebaseMessagingService extends AbstractWigzoFcmListenerService {
    @Override
    protected Class<? extends Activity> getTargetActivity() {
        return null;
    }
}
