package com.apackage.nguye.sjsucloser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddClassActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etAddClass;
    private Button bSubmit;
    private ProgressDialog progressDialog;

    private String classId;

    private FirebaseUser mUser;
    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        etAddClass = (EditText) findViewById(R.id.etAddClass);
        bSubmit = (Button) findViewById(R.id.bSubmit);
        progressDialog = new ProgressDialog(AddClassActivity.this);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();

        bSubmit.setOnClickListener(this);
    }

    private void addClass() {

        classId = etAddClass.getText().toString();
        if (!validateForm(classId)) {
            return;
        }

        progressDialog.setMessage("Adding class...");
        progressDialog.show();

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("class list").child(classId).child(mUser.getUid());
        mFirebaseDatabase.setValue("Enroll");
        Intent userMain = new Intent(AddClassActivity.this, UserMainActivity.class);
        AddClassActivity.this.startActivity(userMain);
        progressDialog.dismiss();

    }

    private boolean validateForm(String classId) {
        boolean valid = true;
        if (TextUtils.isEmpty(classId)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddClassActivity.this);
            builder.setMessage("Please enter your class")
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
        if (i == R.id.bSubmit)
            addClass();
    }
}
