package com.wigzo.sdk.helpers;

/**
 * Created by wigzo
 */
public enum Configuration {

    // ----------------------------------------- MIND IT !!!!!!!!!! -----------------------------------------//
    BASE_URL("baseUrl","https://professorx.wigzopush.com"),
    // ----------------------------------------- MIND IT !!!!!!!!!! -----------------------------------------//
    APP_KEY("appKey","APP_KEY"),
    DEFAULT_APP_VERSION("defaultAppVersion","1.0"),
    DEFAULT_SDK_VERSION("defaultSdkVersion","1.0"),
    DEVICE_ID_KEY("deviceIdKey","DEVICE_ID_KEY"),
    DEVICE_ID_TAG("deviceIdTag","DeviceInfo"),
    EMAIL_DATA_URL("emailDataUrl","/androidsdk/mapemail"),
    EMAIL_KEY("emailKey","EMAIL_KEY"),
    EMAIL_SYNC_KEY("emailSyncKey","EMAIL_SYNC_KEY"),
    EVENTS_KEY("eventKey","WIGZO_EVENTS"),
    EVENT_DATA_URL("eventDataUrl","/androidsdk/geteventdata"),
    GCM_DEVICE_MAPPED("gcmDeviceMapped", "GCM_DEVICE_MAPPED"),
    GCM_DEVICE_MAPPING_URL("gcmDeviceMappingUrl", "/androidsdk/map-gcm"),
    GCM_OPEN_KEY("gcmOpenKey", "GCM_OPEN_KEY"),
    GCM_OPEN_URL("gcmOpenUrl", "/rest/v1/push/android/track/open-multiple"),
    GCM_READ_KEY("gcmReadKey", "GCM_READ_KEY"),
    GCM_READ_URL("gcmReadUrl", "/rest/v1/push/android/track/read-multiple"),
    GCM_REGISTRATION_URL("gcmRegistrationUrl", "/rest/v1/push/android/register-subscription"),
    INITIAL_DATA_URL("initialDataUrl","/androidsdk/getinitialdata"),
    PREV_TIME_SPENT_KEY("prevTimeSpent","PREV_TIME_SPENT_KEY"),
    SENT_GCM_TOKEN_TO_SERVER("sentTokenToServer", "SENT_GCM_TOKEN_TO_SERVER"),
    SESSION_DATA_URL("sessionDataUrl","/androidsdk/getsessiondata"),
    STORAGE_KEY("storageKey","WIGZO_SHARED_STORAGE"),
    TIME_DELAY("timeDelay","45"),
    TIME_SPENT_KEY("timeSpentKey","TIME_SPENT_KEY"),
    USER_PROFILE_DATA_KEY("userProfileDataKey","USER_PROFILE_DATA_KEY"),
    USER_PROFILE_PICTURE_KEY("userProfilePictureKey","USER_PROFILE_PICTURE_KEY"),
    USER_PROFILE_SYNC_KEY("userProfileSyncKey","USER_PROFILE_SYNC_KEY"),
    USER_PROFILE_URL("userProfileUrl","/androidsdk/getuserprofiledata"),
    WIGZO_GCM_LISTENER_SERVICE_TAG("wigzoGCMListenerServiceTag","AbstractWigzoFcmListenerService"),
    WIGZO_INIT_DATA_SYNC_FLAG_KEY("wigzoInitiDataSyncFlag","WIGZO_INIT_DATA_SYNC_FLAG_KEY"),
    WIGZO_REG_INTENT_SERVICE_TAG("wigzoRegIntentServiceTag","RegIntentService"),
    WIGZO_SDK_TAG("wigzoSdkTag","Wigzo");

    /*APP_KEY("appKey","APP_KEY"),
    INITIAL_DATA_URL("initialDataUrl","/androidsdk/getinitialdata"),
    DEVICE_ID_TAG("deviceIdTag","DeviceInfo"),
    DEFAULT_SDK_VERSION("defaultSdkVersion","1.0"),
    DEFAULT_APP_VERSION("defaultAppVersion","1.0"),
    WIGZO_SDK_TAG("wigzoSdkTag","Wigzo"),
    WIGZO_GCM_LISTENER_SERVICE_TAG("wigzoGCMListenerServiceTag","AbstractWigzoFcmListenerService"),
    WIGZO_REG_INTENT_SERVICE_TAG("wigzoRegIntentServiceTag","RegIntentService"),
    WIGZO_INIT_DATA_SYNC_FLAG_KEY("wigzoInitiDataSyncFlag","WIGZO_INIT_DATA_SYNC_FLAG_KEY"),
    STORAGE_KEY("storageKey","WIGZO_SHARED_STORAGE"),
    USER_PROFILE_DATA_KEY("userProfileDataKey","USER_PROFILE_DATA_KEY"),
    USER_PROFILE_SYNC_KEY("userProfileSyncKey","USER_PROFILE_SYNC_KEY"),
    USER_PROFILE_PICTURE_KEY("userProfilePictureKey","USER_PROFILE_PICTURE_KEY"),
    EMAIL_SYNC_KEY("emailSyncKey","EMAIL_SYNC_KEY"),
    EVENTS_KEY("eventKey","WIGZO_EVENTS"),
    DEVICE_ID_KEY("deviceIdKey","DEVICE_ID_KEY"),
    EMAIL_KEY("emailKey","EMAIL_KEY"),
    PREV_TIME_SPENT_KEY("prevTimeSpent","PREV_TIME_SPENT_KEY"),
    TIME_SPENT_KEY("timeSpentKey","TIME_SPENT_KEY"),
    SENT_GCM_TOKEN_TO_SERVER("sentTokenToServer", "SENT_GCM_TOKEN_TO_SERVER"),
    EVENT_DATA_URL("eventDataUrl","/androidsdk/geteventdata"),
    SESSION_DATA_URL("sessionDataUrl","/androidsdk/getsessiondata"),
    USER_PROFILE_URL("userProfileUrl","/androidsdk/getuserprofiledata"),
    EMAIL_DATA_URL("emailDataUrl","/androidsdk/mapemail"),
    GCM_REGISTRATION_URL("gcmRegistrationUrl", "/rest/v1/push/android/register-subscription"),
    GCM_DEVICE_MAPPING_URL("gcmDeviceMappingUrl", "/androidsdk/map-gcm"),
    TIME_DELAY("timeDelay","45"),
    GCM_READ_KEY("gcmReadKey", "GCM_READ_KEY"),
    GCM_OPEN_KEY("gcmOpenKey", "GCM_OPEN_KEY"),
    GCM_DEVICE_MAPPED("gcmDeviceMapped", "GCM_DEVICE_MAPPED"),
    GCM_READ_URL("gcmReadUrl", "/rest/v1/push/android/track/read-multiple"),
    GCM_OPEN_URL("gcmOpenUrl", "/rest/v1/push/android/track/open-multiple");*/

    /*USER_LOGGED_IN("loggedIn",  "logged_in"),
    USER_LOGGED_OUT("loggedOut","logged_out"),
    USER_LOGGEDINTIME("loggedInTime","logged_in_time"),
    USER_LOGGEDOUTTIME("loggedOutTime","logged_out_time");
*/

    public String key;
    public String value;

    Configuration(String key, String value)
    {
        this.key   = key;
        this.value = value;
    }


}
