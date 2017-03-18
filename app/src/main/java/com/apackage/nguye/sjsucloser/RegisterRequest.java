package com.apackage.nguye.sjsucloser;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack on 02/17/17.
 */

public class RegisterRequest extends StringRequest {

    private Map<String, String> params;

    public RegisterRequest(String firstName, String lastName, String account, String password, String token, Response.Listener<String> listener) {
        super(Method.POST, Constants.REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("inputFirstName", firstName);
        params.put("inputLastName", lastName);
        params.put("inputAccount", account);
        params.put("inputPassword", password);
        params.put("deviceToken", token);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
