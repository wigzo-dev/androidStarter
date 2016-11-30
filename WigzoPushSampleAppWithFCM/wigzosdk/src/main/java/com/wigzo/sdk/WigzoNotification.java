package com.wigzo.sdk;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.wigzo.sdk.model.GcmRead;

/**
 * Created by ankit on 16/5/16.
 */
public class WigzoNotification {
    public static void notification(final Context applicationContext, Class<? extends Activity> targetActivity, NotificationCompat.Builder notificationBuilder, String intentData, final String uuid, Integer notificationId, String linkType, String link, Integer secondSound) {
        // if notification_id is provided use it.
        final int mNotificationId = null != notificationId ? notificationId : new Random().nextInt();
        int icon = applicationContext.getApplicationInfo().icon;


        Intent proxyIntent = new Intent(applicationContext, ProxyActivity.class);

       /* if (null != intentData) {
            for (Map.Entry<String, Object> entry : intentData.entrySet())
            {
                if (entry.getValue() instanceof CharSequence) {
                    proxyIntent.putExtra(entry.getKey(), (CharSequence) entry.getValue());
                }
                else if (entry.getValue() instanceof Number) {
                    proxyIntent.putExtra(entry.getKey(), (Number) entry.getValue());
                }
                else if (entry.getValue() instanceof Boolean) {
                    proxyIntent.putExtra(entry.getKey(), (Boolean) entry.getValue());
                }
            }
        }*/



        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.

        proxyIntent.putExtra("targetActivity", targetActivity);
        proxyIntent.putExtra("uuid", uuid);
        proxyIntent.putExtra("intentData", intentData);
        proxyIntent.putExtra("linkType", linkType);
        proxyIntent.putExtra("link", link);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        applicationContext,
                        0,
                        proxyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Resources resources = applicationContext.getResources(),
                systemResources = Resources.getSystem();
        notificationBuilder.setLights(
                ContextCompat.getColor(applicationContext, systemResources
                        .getIdentifier("config_defaultNotificationColor", "color", "android")),
                resources.getInteger(systemResources
                        .getIdentifier("config_defaultNotificationLedOn", "integer", "android")),
                resources.getInteger(systemResources
                        .getIdentifier("config_defaultNotificationLedOff", "integer", "android")));


        notificationBuilder.setSmallIcon(icon);
//            .setSound(defaultSoundUri)
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setVibrate(new long[] { 0, 330, 300, 300 });
        notificationBuilder.setContentIntent(resultPendingIntent);




        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) applicationContext.getSystemService(applicationContext.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, notificationBuilder.build());

        // Play sound
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone ringtone = RingtoneManager.getRingtone(applicationContext, defaultSoundUri);
        final ScheduledExecutorService soundWorker = Executors.newSingleThreadScheduledExecutor();
        Runnable playSound = new Runnable() {
            public void run() {
                System.out.println("here");
                ringtone.stop();
                ringtone.play();
            }
        };

        soundWorker.schedule(playSound, 0, TimeUnit.SECONDS);
        // Play second sound
        if (null != secondSound && secondSound > 0) {
            if (secondSound > 10) {
                secondSound = 10;
            }
            soundWorker.schedule(playSound, secondSound, TimeUnit.SECONDS);
        }

        // increase counter
        if (StringUtils.isNotEmpty(uuid)) {
            final ScheduledExecutorService gcmReadWorker = Executors.newSingleThreadScheduledExecutor();
            gcmReadWorker.schedule(new Runnable() {
                @Override
                public void run() {
                    GcmRead gcmRead = new GcmRead(uuid);
                    GcmRead.Operation operation = GcmRead.Operation.saveOne(gcmRead);
                    GcmRead.editOperation(applicationContext, operation);
                }
            }, 0, TimeUnit.SECONDS);
        }
    }

    public static void simpleNotification(Context applicationContext, Class<? extends Activity> targetActivity, String title, String body, String intentData, String uuid, Integer notificationId, String linkType, String link, Integer secondSound) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(applicationContext)
            .setContentTitle(title)
            .setContentText(body);
        notification(applicationContext, targetActivity, notificationBuilder, intentData, uuid, notificationId, linkType, link, secondSound);
    }

    public static void imageNotification(Context applicationContext, Class<? extends Activity> targetActivity, String title, String body, String imageUrl, String intentData, String uuid, Integer notificationId, String linkType, String link, Integer secondSound) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(applicationContext)
                .setContentTitle(title)
                .setSmallIcon(applicationContext.getApplicationInfo().icon)
                .setContentText(body);

        if(StringUtils.isNotEmpty(imageUrl)){
            Bitmap remote_picture = null;
            NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
            try {
                remote_picture = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            notiStyle.bigPicture(remote_picture);
//            notificationBuilder.setLargeIcon(remote_picture);
             notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));
            notificationBuilder.setStyle(notiStyle);
        }
        notification(applicationContext, targetActivity, notificationBuilder, intentData, uuid, notificationId, linkType, link, secondSound);
    }



}
