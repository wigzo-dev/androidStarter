/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wigzo.sdk;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.wigzo.sdk.helpers.Configuration;
import com.wigzo.sdk.helpers.ConnectionStream;
import com.wigzo.sdk.helpers.WigzoSharedStorage;

public class WigzoInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyInstanceIDLS";
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is also called
     * when the InstanceID token is initially generated, so this is where
     * you retrieve the token.
     */
    // [START refresh_token]


    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("WigzoInstanceIDService", ": onCreate() called");

        onTokenRefresh();
    }
    @Override
    public void onTokenRefresh() {

        Log.e("WigzoInstanceIDService", ": onTokenRefresh() called");

        WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(WigzoSDK.getInstance().getContext());
        SharedPreferences sharedPreferences = wigzoSharedStorage.getSharedStorage();

        // Setting SENT_GCM_TOKEN_TO_SERVER to false since we want to refresh the token.
        sharedPreferences.edit().putBoolean(Configuration.SENT_GCM_TOKEN_TO_SERVER.value, false).apply();
        sharedPreferences.edit().putBoolean(Configuration.GCM_DEVICE_MAPPED.value, false).apply();

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token WigzoInstanceIDService: " + refreshedToken);
        // TODO: Implement this method to send any registration to your app's servers: This is done.

        sendRegistrationToServer(refreshedToken);

        mapGcmToDeviceId(refreshedToken);

        try {
            subscribeTopics(WigzoSDK.getInstance().getOrgToken());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // [END refresh_token]

    private void sendRegistrationToServer(String token) {
        final Gson gson = new Gson();
        WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(WigzoSDK.getInstance().getContext());
        SharedPreferences sharedPreferences = wigzoSharedStorage.getSharedStorage();

        // Send to server only when the token is not yet sent
        if (!sharedPreferences.getBoolean(Configuration.SENT_GCM_TOKEN_TO_SERVER.value, false)) {

            //hashmap to store user data to send it to FCM server
            Map<String, Object> eventData = new HashMap<>();

            //store data in eventData
            eventData.put("registrationId", token);
            eventData.put("orgtoken", WigzoSDK.getInstance().getOrgToken());

            //Convert eventData to json string
            final String eventDataStr = gson.toJson(eventData);

            //Endpoint Url TODO change url
            //final String url = Configuration.BASE_URL.value + Configuration.GCM_REGISTRATION_URL.value;
            final String url = "https://professorx.wigzopush.com/rest/v1/push/android/register-subscription";

            /*//post data to end point Url
            String response = ConnectionStream.postRequest(url, eventDataStr);

            if (null != response) {

                Map<String, Object> jsonResponse = gson.fromJson(response, new TypeToken<HashMap<String, Object>>() {}.getType());

                if ("success".equals(jsonResponse.get("status"))) {

                    sharedPreferences.edit().putBoolean(Configuration.SENT_GCM_TOKEN_TO_SERVER.value, true).apply();

                }
            }*/



            ExecutorService executorService = Executors.newSingleThreadExecutor();

            Future<Boolean> future = executorService.submit(new Callable<Boolean>(){
                public Boolean call()  {
                    //Post data to server
                    String response = ConnectionStream.postRequest(url,eventDataStr);

                    //Check if post request returned success if the response is not null
                    if (null != response) {
                        Map<String, Object> jsonResponse = gson.fromJson(response, new TypeToken<HashMap<String, Object>>() {}.getType());
                        if ("success".equals(jsonResponse.get("status"))) {
                            return true;
                        }
                    }
                    return false;
                }
            });

            try {

                //if post request was successful save the Synced data flag as true in shared preferences
                if(future.get()){
                    Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_SHORT).show();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    private void mapGcmToDeviceId(String token) {
        Gson gson = new Gson();

        WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(WigzoSDK.getInstance().getContext());

        SharedPreferences sharedPreferences = wigzoSharedStorage.getSharedStorage();

        Boolean initDataSynced = sharedPreferences.getBoolean(Configuration.WIGZO_INIT_DATA_SYNC_FLAG_KEY.value, false);

        Boolean isGcmDeviceMapped = sharedPreferences.getBoolean(Configuration.GCM_DEVICE_MAPPED.value, false);

        if (initDataSynced && !isGcmDeviceMapped) {

            Map<String, Object> eventData = new HashMap<>();

            String deviceId = sharedPreferences.getString(Configuration.DEVICE_ID_KEY.value, "");

            eventData.put("registrationId", token);
            eventData.put("orgtoken", WigzoSDK.getInstance().getOrgToken());
            eventData.put("deviceId", deviceId);

            final String eventDataStr = gson.toJson(eventData);

            final String url = Configuration.BASE_URL.value + Configuration.GCM_DEVICE_MAPPING_URL.value;

            String response = ConnectionStream.postRequest(url, eventDataStr);

            if (null != response) {

                Map<String, Object> jsonResponse = gson.fromJson(response, new TypeToken<HashMap<String, Object>>() {}.getType());

                if ("success".equals(jsonResponse.get("status"))) {
                    sharedPreferences.edit().putBoolean(Configuration.GCM_DEVICE_MAPPED.value, true);
                }
            }
        }
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {

        //Below commented GcmPubSub part is not used with FCM

        /*GcmPubSub pubSub = GcmPubSub.getInstance(this);
        pubSub.subscribe(token, "/topics/" + WigzoSDK.getInstance().getOrgToken(), null);*/

        //FirebaseMessaging.getInstance().subscribeToTopic("mytopic");
        //FirebaseMessaging.getInstance().subscribeToTopic("/topics/" + WigzoSDK.getInstance().getOrgToken());
        FirebaseMessaging.getInstance().subscribeToTopic("orgsubscribe-" + WigzoSDK.getInstance().getOrgToken());


    }
    // [END subscribe_topics]
}
