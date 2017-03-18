package com.apackage.nguye.sjsucloser;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack on 03/03/17.
 */

public class SendingRequest extends StringRequest {

    private Map<String, String> params;

    public SendingRequest(String email, String title, String msg, String image, Response.Listener<String> listener) {
        super(Method.POST, Constants.URL_SEND_SINGLE_PUSH, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("title", title);
        params.put("message", msg);
        if (!TextUtils.isEmpty(image))
            params.put("image", image);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
