package com.apackage.nguye.sjsucloser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    private EditText usrAccount;
    private EditText usrPassword;
    private TextView tvToken;
    private Button bLogin;
    private Button bRegister;
    private Button bToken;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        ConstraintLayout rl = (ConstraintLayout)findViewById(R.id.activity_login);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.sjsu_logo);
        rl.addView(imageView);
        */

        usrAccount = (EditText) findViewById(R.id.etUserAccount);
        usrPassword = (EditText) findViewById(R.id.etUserPassword);
        tvToken = (TextView) findViewById(R.id.tvToken);
        bToken = (Button) findViewById(R.id.bDisplayToken);
        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister = (Button) findViewById(R.id.bRegister);
        progressDialog = new ProgressDialog(LoginActivity.this);

        mAuth = FirebaseAuth.getInstance();

        bToken.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
    }

    private void displayToken() {
        String token = SharedPrefManager.getInstance(LoginActivity.this).getDeviceToken();

        if (token != null) {
            tvToken.setText(token);
        } else {
            tvToken.setText("Token not generated, something is wrong");
        }
    }

    private void login() {

        String account = usrAccount.getText().toString();
        String password = usrPassword.getText().toString();

        Log.d(TAG, "Signing in");
        if (!validateForm(account, password)) {
            return;
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(account, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isComplete());

                        if (task.isSuccessful()) {
                            Intent usrMainIntent = new Intent(LoginActivity.this, UserMainActivity.class);
                            LoginActivity.this.startActivity(usrMainIntent);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(LoginActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void register() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(registerIntent);
    }

    private boolean validateForm(String account, String password) {
        boolean valid = true;
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Please enter all fields")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            valid = false;
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bDisplayToken)
            displayToken();
        else if (i == R.id.bRegister)
            register();
        else if (i == R.id.bLogin)
            login();
    }

}
