package com.apackage.nguye.sjsucloser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText userAccount = (EditText) findViewById(R.id.etUserAccount);
        final EditText userPassword = (EditText) findViewById(R.id.etUserPassword);
        final Button loginButton = (Button) findViewById(R.id.bLogin);
        final Button registerButton = (Button) findViewById(R.id.bRegisterAccount);

        final Button bDisplayToken = (Button) findViewById(R.id.bDisplayToken);
        final TextView tvToken = (TextView) findViewById(R.id.tvToken);

        bDisplayToken.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == bDisplayToken) {
                    //getting token from shared preferences
                    String token = SharedPrefManager.getInstance(LoginActivity.this).getDeviceToken();

                    //if token is not null
                    if (token != null) {
                        //displaying the token
                        tvToken.setText(token);
                    } else {
                        //if token is null that means something wrong
                        tvToken.setText("Token not generated");
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String account = userAccount.getText().toString();
                System.out.println(account);
                final String password = userPassword.getText().toString();
                System.out.println(password);

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Logging in...");
                progressDialog.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);

                        try {
                            JSONObject result = new JSONObject(response);
                            boolean hit = result.getBoolean("hit");
                            if (hit) {
                                String firstName = result.getString("firstName");
                                String lastName = result.getString("lastName");
                                String account = result.getString("accountName");
                                Intent intent = new Intent(LoginActivity.this, UserMainActivity.class);
                                intent.putExtra("firstName", firstName);
                                intent.putExtra("lastName", lastName);
                                intent.putExtra("account", account);
                                LoginActivity.this.startActivity(intent);
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(account, password, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(loginRequest);
            }
        });
    }

}
