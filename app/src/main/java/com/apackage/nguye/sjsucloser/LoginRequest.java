package com.apackage.nguye.sjsucloser;

/**
 * Created by Jack on 02/10/17.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, Constants.LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("inputAccount", username);
        params.put("inputPassword", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
