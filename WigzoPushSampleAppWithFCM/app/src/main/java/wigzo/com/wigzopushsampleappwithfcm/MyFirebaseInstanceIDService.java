package wigzo.com.wigzopushsampleappwithfcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.wigzo.sdk.WigzoSDK;

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by wigzo on 29/11/16.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        String ORG_TOKEN = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE).getString("ORG_TOKEN", "56065c5b-db30-4b89-bd76-0a9c2938c90b");
        WigzoSDK.getInstance().initializeWigzoData(getApplicationContext(), ORG_TOKEN, token);
    }
}
