package com.apackage.nguye.sjsucloser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 03/03/17.
 */

public class ActivitySendPushNotification extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSendPush;
    private RadioGroup radioGroup;
    //private Spinner spinner;
    private ProgressDialog progressDialog;

    private EditText editTextTitle, editTextMessage, editTextImage, editTextRecieverEmail;

    private boolean isSendAllChecked;
    private List<String> devices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        //radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //spinner = (Spinner) findViewById(R.id.spinnerDevices);
        buttonSendPush = (Button) findViewById(R.id.bSend);

        editTextTitle = (EditText) findViewById(R.id.etTitle);
        editTextMessage = (EditText) findViewById(R.id.etMsg);
        editTextImage = (EditText) findViewById(R.id.etImg);
        editTextRecieverEmail = (EditText) findViewById(R.id.etReceiverEmail);

        //devices = new ArrayList<>();

        buttonSendPush.setOnClickListener(this);

        loadRegisteredDevices();
    }

    //method to load all the devices from database
    private void loadRegisteredDevices() {

    }

    //this method will send the push
    //from here we will call sendMultiple() or sendSingle() push method
    //depending on the selection
    private void sendPush() {
        if (isSendAllChecked) {
            sendMultiplePush();
        } else {
            sendSinglePush();
        }
    }

    private void sendMultiplePush() {

    }

    private void sendSinglePush() {
        final String title = editTextTitle.getText().toString();
        final String message = editTextMessage.getText().toString();
        final String image = editTextImage.getText().toString();
        final String email = editTextRecieverEmail.getText().toString();

        progressDialog.setMessage("Sending Push");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SEND_SINGLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(ActivitySendPushNotification.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);

                if (!TextUtils.isEmpty(image))
                    params.put("image", image);

                params.put("email", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ActivitySendPushNotification.this);
        requestQueue.add(stringRequest);
        //MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onClick(View view) {
        //calling the method send push on button click
        sendPush();
    }
}