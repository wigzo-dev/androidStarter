package com.wigzo.sdk.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.wigzo.sdk.WigzoSDK;
import com.wigzo.sdk.helpers.Configuration;
import com.wigzo.sdk.helpers.WigzoSharedStorage;

/**
 * An instance of this class represents an event(or activity).
 * Event information includes -
 * {@link EventInfo#eventName} - Name of underlying event. It can either be standard event name(as defined under {@link com.wigzo.sdk.helpers.OrganizationEvents.Events}) or a custom event name.
 * {@link EventInfo#eventValue} - Value of underlying event. e.g. rating info in case of Rate event
 * {@link EventInfo#metadata}. For more info see - {@link Metadata}
 * @author Minaz Ali
 */

public class EventInfo {

    private String eventName;
    private String eventValue;
    private Metadata metadata;

    private String timestamp;

    /**
     * Constructor to create {@link EventInfo} object whenever an event( or activity ) takes place
     * @param eventName : Name of Event ( or activity), must not be null. It can either be standard event name(as defined under {@link com.wigzo.sdk.helpers.OrganizationEvents.Events}) or a custom event name.
     * @param eventValue : Value of Event ( or Activity), must not be null
     */
    public EventInfo(String eventName, String eventValue) {
        this.eventName = eventName;
        this.eventValue = eventValue;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }


    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**{@code Metadata} class is used to provide additional information about activity.
     * Example : <p>If it is a product page, metadata can be used to provide product details like
     * {@link Metadata#productId}, {@link Metadata#title}, {@link Metadata#description}, {@link Metadata#url}, {@link Metadata#price}
     */
    public static class Metadata{
        private String productId;
        private String title;
        private String description;
        private String url;
        private String tags;
        private BigDecimal price;

        public Metadata (){}

        /**
         * Constructor to obtain {@code Metadata} object with id, title, description,url.
         * This object used to provide additional information about activity.
         * Example: If it is a product page, metadata can be used to provide product details like id, title, description, url, price
         * @param productId productId of Item
         * @param title title of Item
         * @param description description of Item
         * @param url url of application's  market place or web
         */
        public Metadata(String productId, String title, String description, String url) {
            this.productId = productId;
            this.title = title;
            this.description = description;
            if(StringUtils.isEmpty(url)){
                String packageName = WigzoSDK.getInstance().getContext().getPackageName();
                this.url ="http://play.google.com/store/apps/details?id="+packageName;
            }else {
                this.url = url;
            }
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Operation {
        public enum OperationType {
            SAVE_ONE,
            REMOVE_PARTIALLY
        }

        OperationType operationType;
        EventInfo eventInfo;
        List<EventInfo> eventInfoList;

        public Operation() {}

        public static Operation saveOne(EventInfo eventInfo) {
            Operation operation = new Operation();
            operation.operationType = OperationType.SAVE_ONE;
            operation.eventInfo = eventInfo;
            return operation;
        }

        public static Operation removePartially(List<EventInfo> eventInfoList) {
            Operation operation = new Operation();
            operation.operationType = OperationType.REMOVE_PARTIALLY;
            operation.eventInfoList = eventInfoList;
            return operation;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EventInfo){
            EventInfo eventInfo = (EventInfo) obj;
            return this.timestamp.equalsIgnoreCase(eventInfo.timestamp);
        }
        return false;
    }

    /**
     * This method is used to store events(or Activities)
     */
    public void saveEvent() {
        final Operation operation = Operation.saveOne(this);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                EventInfo.editOperation(operation);
            }
        });

        /*WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(WigzoSDK.getInstance().getContext());
        List<EventInfo> eventInfos = wigzoSharedStorage.getEventList();
        eventInfos.add(this);
        Gson gson = new Gson();
        final String eventsStr = gson.toJson(eventInfos);
        wigzoSharedStorage.getSharedStorage().edit().putString(Configuration.EVENTS_KEY.value, eventsStr).apply();*/

    }

//    public synchronized static
    public synchronized static List<EventInfo> getEventList() {
        Gson gson = new Gson();
        List<EventInfo> eventInfoList = new ArrayList<>();
        WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(WigzoSDK.getInstance().getContext());

        String eventsStr = wigzoSharedStorage.getSharedStorage().getString(Configuration.EVENTS_KEY.value, "");
        if(StringUtils.isNotEmpty(eventsStr))
            eventInfoList = gson.fromJson(eventsStr, new TypeToken<List<EventInfo>>() { }.getType());
        return eventInfoList;
    }


    public synchronized static void editOperation(Operation operation) {
        Gson gson = new Gson();
        List<EventInfo> eventInfoList = new ArrayList<>();

        WigzoSharedStorage wigzoSharedStorage = new WigzoSharedStorage(WigzoSDK.getInstance().getContext());
        String eventsStr = wigzoSharedStorage.getSharedStorage().getString(Configuration.EVENTS_KEY.value, "");

        if(StringUtils.isNotEmpty(eventsStr)) {
            eventInfoList = gson.fromJson(eventsStr, new TypeToken<List<EventInfo>>() { }.getType());
        }
        // operations begin
        if (operation.operationType == Operation.OperationType.SAVE_ONE) {
            eventInfoList.add(operation.eventInfo);
        }
        else if (operation.operationType == Operation.OperationType.REMOVE_PARTIALLY) {
            eventInfoList.removeAll(operation.eventInfoList);
        }
        eventsStr = gson.toJson(eventInfoList);
        wigzoSharedStorage.getSharedStorage().edit().putString(Configuration.EVENTS_KEY.value, eventsStr).apply();
    }

}
