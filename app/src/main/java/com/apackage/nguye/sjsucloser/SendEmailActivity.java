package com.apackage.nguye.sjsucloser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class SendEmailActivity extends AppCompatActivity {

    //private Button bSend;
    //private ProgressDialog progressDialog;
    //private EditText etReceiverEmail, etTitle, etMsg, etImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        final Button bSend = (Button) findViewById(R.id.bSend);
        final EditText etReceiverEmail = (EditText) findViewById(R.id.etReceiverEmail);
        final EditText etTitle = (EditText) findViewById(R.id.etTitle);
        final EditText etMsg = (EditText) findViewById(R.id.etMsg);
        final EditText etImage = (EditText) findViewById(R.id.etImg);

        bSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String email = etReceiverEmail.getText().toString();
                final String title = etTitle.getText().toString();
                final String msg = etMsg.getText().toString();
                final String img = etImage.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(SendEmailActivity.this);

                progressDialog.setMessage("Sending...");
                progressDialog.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(SendEmailActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                };

                SendingRequest sendingRequest = new SendingRequest(email, title, msg, img, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(SendEmailActivity.this);
                requestQueue.add(sendingRequest);


            }
        });
    }
}
