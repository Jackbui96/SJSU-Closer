package com.apackage.nguye.sjsucloser;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack on 02/20/17.
 */

public class AddFriendRequest extends StringRequest {
    private static final String ADDFRIEND_REQUEST_URL = "https://wwwsjsu-closer.000webhostapp.com/add_friend.php";
    private Map<String, String> params;

    public AddFriendRequest(String friendAccount, String userAccount, Response.Listener<String> listener) {
        super(Request.Method.POST, ADDFRIEND_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("inputFriendAccount", friendAccount);
        params.put("inputUserAccount", userAccount);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
