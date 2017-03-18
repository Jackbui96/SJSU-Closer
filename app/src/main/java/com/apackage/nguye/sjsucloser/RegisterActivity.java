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

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private UsrPOJO usr;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAccount;
    private EditText etPassword;
    private Button bRegister;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

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

        mFirebaseInstance = FirebaseDatabase.getInstance();

        bRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerUsr(
                        etAccount.getText().toString().trim(),
                        etPassword.getText().toString().trim(),
                        etFirstName.getText().toString().trim(),
                        etLastName.getText().toString().trim());
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void registerUsr(String email, String password, String firstName, String lastName) {

        Log.d(TAG, "createNewAccount:" + email);
        if (!validateForm(email, password, firstName, lastName)) {
            return;
        }

        //This method sets up a new User by fetching the user entered details.
        //setUpUsr(email, password, firstName, lastName);

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Failed to register",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(loginIntent);
                        }
                    }
                });

    }

    private boolean validateForm(String email, String password, String firstName, String lastName) {
        boolean valid = true;

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Please enter all fields")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            valid = false;
        }

        return valid;
    }

    protected void setUpUsr(String email, String password, String firstName, String lastName) {
        usr = new UsrPOJO();
        usr.setFirstName(firstName);
        usr.setLastName(lastName);
        usr.setAccount(email);
        usr.setPassword(password);
    }

    /*
    private void onAuthenticationSucess(FirebaseUser mUser) {
        // Write new user
        saveNewUser(mUser.getUid(), usr.getFirstName(), usr.getLastName(), usr.getAccount(), usr.getPassword());
        signOut();
        // Go to LoginActivity
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    private void saveNewUser(String userId, String firstName, String lastName, String account, String password) {
        usr = new UsrPOJO(userId,firstName,lastName,account,password);

        mRef.child("users").child(userId).setValue(usr);
    }

    private void signOut() {
        mAuth.signOut();
    }


    */

}
