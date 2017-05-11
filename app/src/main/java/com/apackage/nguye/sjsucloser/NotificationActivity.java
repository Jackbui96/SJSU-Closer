package com.apackage.nguye.sjsucloser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private Button bAccept;
    private Button bDecline;

    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;

    private String src;
    private boolean notification;
    private ArrayList<String> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        user = FirebaseAuth.getInstance().getCurrentUser();

        friendList = new ArrayList<String>();

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("notification list");
        Query query = mFirebaseDatabase.orderByChild("dest").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    notification = (boolean) value.child("notification").getValue();
                    if (notification) {
                        src = value.child("src").getValue().toString();
                        System.out.println("src value: " + src);
                        friendList.add(src);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        NotificationAdapter adapter = new NotificationAdapter(friendList, this);

        ListView lv = (ListView) findViewById(R.id.lvNotification);
        lv.setAdapter(adapter);
    }

}
