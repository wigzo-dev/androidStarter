package com.wigzo.sdk.helpers;


/**
 * Utility for standard events. It's advisable to use standard events so that Wigzo's recommendation engine can function properly.
 * Although you are free to use custom event names.
 * Standard events can be used as follows -
 *
 *     EventInfo eventInfo = new EventInfo( OrganizationEvents.Events.SEARCH.key,"iphone" );
 *
 */
public class OrganizationEvents {

    /**
     * Enum containing all events for all types of organizations.
     */
    public enum Events {
        ADDTOCART("addtocart", "Add To Cart"),
        ADDTOPLAYLIST( "addtoplaylist", "Add To Playlist"),
        ADDTOWISHLIST( "addtowishlist", "Add To Wishlist"),
        BOOK( "book", "Book"),
        BUY("buy", "Buy"),
        CHECKOUT( "checkout", "Checkout"),
        ITEM( "item", "Item"),
        JOIN( "join", "Join"),
        LIKE( "like", "Like"),
        LISTEN( "listen", "Listen"),
        LOGGEDIN("loggedin","LastLoggedIn"),
        LOGGEDOUT("loggedout","LastLoggedOut"),
        OTHER( "other", "Other"),
        RATE("rate", "Rate"),
        REGISTERED("registered", "Registered"),
        SEARCH("search", "Search"),
        SHARE( "share", "Share"),
        VIEW("view", "View"),
        WATCH( "watch", "Watch"),
        WATCHLATER( "watchlater", "Watch Later" );


        public String key;
        public String label;

        Events(String key, String label) {
            this.key = key;
            this.label = label;
        }
    }

}
