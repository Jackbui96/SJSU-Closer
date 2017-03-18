package com.apackage.nguye.sjsucloser;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack on 02/22/17.
 */

public class GetFriendListRequest extends StringRequest {

    private Map<String, String> params;

    public GetFriendListRequest(String account, Response.Listener<String> listener) {
        super(Request.Method.POST, Constants.GETLIST_URL_REQUEST, listener, null);
        params = new HashMap<>();
        params.put("inputAccount", account);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
