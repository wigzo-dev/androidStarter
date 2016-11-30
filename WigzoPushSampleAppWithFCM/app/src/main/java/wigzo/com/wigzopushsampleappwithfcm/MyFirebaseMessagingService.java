package wigzo.com.wigzopushsampleappwithfcm;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by wigzo on 29/11/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {

            Log.e("Message: ", remoteMessage.toString());

            Log.e("Message: ", remoteMessage.getNotification().getBody());
            Log.e("Collapse Key: ", remoteMessage.getCollapseKey());
            Log.e("From: ", remoteMessage.getFrom());
            Log.e("Message ID: ", remoteMessage.getMessageId());
            //Log.e("Message Type: ", remoteMessage.getMessageType());
            //Log.e("To: ", remoteMessage.getTo().toString());
            Log.e("Data: ", remoteMessage.getData().toString());
            Log.e("SentTime: ", remoteMessage.getSentTime() + "");
            Log.e("TTL: ", remoteMessage.getTtl() + "");
//            Log.e("Body Localisation Key: ", remoteMessage.getNotification().getBodyLocalizationKey());
//            Log.e("Tag: ", remoteMessage.getNotification().getTag());
            Log.e("Title: ", remoteMessage.getNotification().getTitle());
            Log.e("Title ", "Localisation Key: " + remoteMessage.getNotification().getTitleLocalizationKey());
            Log.e("Body ", "Localisation Args: " + remoteMessage.getNotification().getBodyLocalizationArgs());
            Log.e("Title ", "Localisation Args: " + remoteMessage.getNotification().getTitleLocalizationArgs());

            /*Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
            notificationIntent.putExtra("notification", remoteMessage.getNotification().getBody());
            startActivity(notificationIntent);*/
        }
    }
}
