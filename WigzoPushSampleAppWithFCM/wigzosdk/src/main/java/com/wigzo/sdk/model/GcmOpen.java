package com.wigzo.sdk.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import com.wigzo.sdk.helpers.Configuration;
import com.wigzo.sdk.helpers.WigzoSharedStorage;

/**
 * Created by wigzo on 18/5/16.
 */
public class GcmOpen {
    public static class Operation {
        public enum OperationType {
            SAVE_ONE,
            REMOVE_PARTIALLY
        }

        OperationType operationType;
        GcmOpen gcmOpen;
        List<GcmOpen> gcmOpenList;

        public Operation() {}

        public static Operation saveOne(GcmOpen gcmOpen) {
            Operation operation = new Operation();
            operation.operationType = OperationType.SAVE_ONE;
            operation.gcmOpen = gcmOpen;
            return operation;
        }

        public static Operation removePartially(List<GcmOpen> gcmOpenList) {
            Operation operation = new Operation();
            operation.operationType = OperationType.REMOVE_PARTIALLY;
            operation.gcmOpenList = gcmOpenList;
            return operation;
        }
    }

    private String uuid;
    private String timestamp;

    public GcmOpen(){};

    public GcmOpen(String uuid) {
        this.uuid = uuid;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof GcmOpen){
            GcmOpen gcmOpen = (GcmOpen) obj;
            return this.uuid.equalsIgnoreCase(gcmOpen.uuid);
        }
        return false;
    }

    public synchronized static List<GcmOpen> getGcmOpenList(Context context) {
        Gson gson = new Gson();
        List<GcmOpen> gcmOpenList = new ArrayList<>();
        WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(context);

        String gcmOpensStr = wigzoSharedStorage.getSharedStorage().getString(Configuration.GCM_OPEN_KEY.value, "");
        if(StringUtils.isNotEmpty(gcmOpensStr))
            gcmOpenList = gson.fromJson(gcmOpensStr, new TypeToken<List<GcmOpen>>() { }.getType());
        return gcmOpenList;
    }


    public synchronized static void editOperation(Context context, Operation operation) {
        Gson gson = new Gson();
        List<GcmOpen> gcmOpenList = new ArrayList<>();

        WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(context);
        String gcmOpensStr = wigzoSharedStorage.getSharedStorage().getString(Configuration.GCM_OPEN_KEY.value, "");

        if(StringUtils.isNotEmpty(gcmOpensStr)) {
            gcmOpenList = gson.fromJson(gcmOpensStr, new TypeToken<List<GcmOpen>>() { }.getType());
        }
        // operations begin
        if (operation.operationType == Operation.OperationType.SAVE_ONE) {
            gcmOpenList.add(operation.gcmOpen);
        }
        else if (operation.operationType == Operation.OperationType.REMOVE_PARTIALLY) {
            gcmOpenList.removeAll(operation.gcmOpenList);
        }
        gcmOpensStr = gson.toJson(gcmOpenList);
        wigzoSharedStorage.getSharedStorage().edit().putString(Configuration.GCM_OPEN_KEY.value, gcmOpensStr).apply();
    }
}
