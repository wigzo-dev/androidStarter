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
public class GcmRead {
    public static class Operation {
        public enum OperationType {
            SAVE_ONE,
            REMOVE_PARTIALLY
        }

        OperationType operationType;
        GcmRead gcmRead;
        List<GcmRead> gcmReadList;

        public Operation() {}

        public static Operation saveOne(GcmRead gcmRead) {
            Operation operation = new Operation();
            operation.operationType = OperationType.SAVE_ONE;
            operation.gcmRead = gcmRead;
            return operation;
        }

        public static Operation removePartially(List<GcmRead> gcmReadList) {
            Operation operation = new Operation();
            operation.operationType = OperationType.REMOVE_PARTIALLY;
            operation.gcmReadList = gcmReadList;
            return operation;
        }
    }

    private String uuid;
    private String timestamp;

    public GcmRead(){};

    public GcmRead(String uuid) {
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
        if(obj instanceof GcmRead){
            GcmRead gcmRead = (GcmRead) obj;
            return this.uuid.equalsIgnoreCase(gcmRead.uuid);
        }
        return false;
    }

    public synchronized static List<GcmRead> getGcmReadList(Context context) {
        Gson gson = new Gson();
        List<GcmRead> gcmReadList = new ArrayList<>();
        WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(context);

        String gcmReadsStr = wigzoSharedStorage.getSharedStorage().getString(Configuration.GCM_READ_KEY.value, "");
        if(StringUtils.isNotEmpty(gcmReadsStr))
            gcmReadList = gson.fromJson(gcmReadsStr, new TypeToken<List<GcmRead>>() { }.getType());
        return gcmReadList;
    }


    public synchronized static void editOperation(Context context, Operation operation) {
        Gson gson = new Gson();
        List<GcmRead> gcmReadList = new ArrayList<>();

        WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(context);
        String gcmReadsStr = wigzoSharedStorage.getSharedStorage().getString(Configuration.GCM_READ_KEY.value, "");

        if(StringUtils.isNotEmpty(gcmReadsStr)) {
            gcmReadList = gson.fromJson(gcmReadsStr, new TypeToken<List<GcmRead>>() { }.getType());
        }
        // operations begin
        if (operation.operationType == Operation.OperationType.SAVE_ONE) {
            gcmReadList.add(operation.gcmRead);
        }
        else if (operation.operationType == Operation.OperationType.REMOVE_PARTIALLY) {
            gcmReadList.removeAll(operation.gcmReadList);
        }
        gcmReadsStr = gson.toJson(gcmReadList);
        wigzoSharedStorage.getSharedStorage().edit().putString(Configuration.GCM_READ_KEY.value, gcmReadsStr).apply();
    }
}
