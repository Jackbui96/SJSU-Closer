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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;

    private UsrPOJO usr;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAccount;
    private EditText etPassword;
    private Button bRegister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);
        progressDialog = new ProgressDialog(RegisterActivity.this);

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        bRegister.setOnClickListener(this);

    }

    private void register() {

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();

        Log.d(TAG, "createNewAccount:" + account);
        if (!validateForm(account, password, firstName, lastName)) {
            return;
        }

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(account, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            onAuthSucess(task.getResult().getUser());
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    private void onAuthSucess(FirebaseUser mUser) {
        // Write new user
        saveNewUser(
                mUser.getUid(),
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etAccount.getText().toString(),
                etPassword.getText().toString());
        // Go to LoginActivity
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        finish();
    }

    private void saveNewUser(String userId, String firstName, String lastName, String account, String password) {
        usr = new UsrPOJO(userId,firstName,lastName,account,password);
        mFirebaseDatabase.child("users").child(userId).setValue(usr);
    }

    private boolean validateForm(String account, String password, String firstName, String lastName) {
        boolean valid = true;
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
        if (i == R.id.bRegister)
            register();
    }

}
