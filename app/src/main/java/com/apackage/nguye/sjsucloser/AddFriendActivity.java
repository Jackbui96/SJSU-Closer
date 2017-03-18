package com.apackage.nguye.sjsucloser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        final Intent intent = getIntent();

        final EditText etFriendAccount = (EditText) findViewById(R.id.etFriendAccount);
        final Button bAdd = (Button) findViewById(R.id.bAdd);

        final Firebase ref = new Firebase(Constants.FIREBASE_URL);

        bAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String friendAccount = etFriendAccount.getText().toString();

            }
        });

    }
}
