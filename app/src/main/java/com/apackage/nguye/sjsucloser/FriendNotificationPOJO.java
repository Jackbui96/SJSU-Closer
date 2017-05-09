package com.apackage.nguye.sjsucloser;

import java.util.Date;

/**
 * Created by Jack on 04/10/17.
 */

public class FriendNotificationPOJO {

    private String src;
    private String dest;
    private boolean notification;
    private long time;

    public FriendNotificationPOJO() {

    }

    public FriendNotificationPOJO(String src, String dest, boolean notification) {
        this.src = src;
        this.dest = dest;
        this.notification = notification;
        time = new Date().getTime();
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public boolean getNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
