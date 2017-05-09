package com.apackage.nguye.sjsucloser;

import java.util.Map;

/**
 * Created by Jack on 05/08/17.
 */

public class FriendStructPOJO {

    private Map<String, Boolean> friend;

    public FriendStructPOJO() {

    }

    public FriendStructPOJO(Map<String, Boolean> friend) {
        this.friend = friend;
    }

    public Map<String, Boolean> getFriend() {
        return friend;
    }

    public void setFriend(Map<String, Boolean> friend) {
        this.friend = friend;
    }

}
