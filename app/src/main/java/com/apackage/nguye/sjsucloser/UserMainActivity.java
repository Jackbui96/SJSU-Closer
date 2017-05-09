package com.apackage.nguye.sjsucloser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class UserMainActivity extends AppCompatActivity implements OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "UserMainActivity";
    private static final String FRIENDTAG = "Add Friend";

    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView tvWelcome;
    private TextView tvBasicInfo;
    private EditText etSearchFriend;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private Button bAddFriend;
    private Spinner spinner;
    private ListView listView;
    private Button bSubmit;

    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private ValueEventListener dbListener;
    private String mUserKey;

    private String friendAccount;
    private String friendUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_drop_down);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvBasicInfo = (TextView) findViewById(R.id.tvBasicInfo);
        etSearchFriend = (EditText) findViewById(R.id.etSearchFriend);
        //tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //tabLayout.addTab(tabLayout.newTab().setText("Friend List"));
        //tabLayout.addTab(tabLayout.newTab().setText("Class View"));
        //tabLayout.addTab(tabLayout.newTab().setText("Add Class"));
        //tabLayout.addTab(tabLayout.newTab().setText("Chat View"));
        //spinner = (Spinner) findViewById(R.id.optionSpinner);
        //listView = (ListView) findViewById(R.id.lvFriendList);
        //bSubmit = (Button) findViewById(R.id.bSubmit);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        dbListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UsrPOJO user = dataSnapshot.getValue(UsrPOJO.class);

                //Adding it to a string
                String welcomeMessage = "Welcome back, " + user.getFirstName() + " " + user.getLastName();
                String basicInfo = "Here is your profile\nYour account: " + user.getAccount() + "\nYour name: " + user.getFirstName() + " " + user.getLastName();

                //Displaying it on textview
                tvWelcome.setText(welcomeMessage);
                tvBasicInfo.setText(basicInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };
        mFirebaseDatabase.addValueEventListener(dbListener);

        /*
        spinner.setOnItemSelectedListener(this);
        List<String> option = new ArrayList<String>();
        option.add("Add Friend");
        option.add("Add Class");
        option.add("Match Class");
        option.add("Send Email");

        String welcomeMessage = "Welcome back, " + usrFirstName + usrLastName;
        tvWelcome.setText(welcomeMessage);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, option);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        List<String> friendList = getFriendList(usrAccount);
        System.out.println("Friendlist is: " + friendList);
        ArrayAdapter<String> friendListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendList);
        listView.setAdapter(friendListAdapter);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (spinner.getSelectedItemId() == 3) {
                    Intent SendEmailIntent = new Intent(UserMainActivity.this, SendEmailActivity.class);
                    UserMainActivity.this.startActivity(SendEmailIntent);
                }
            }
        });
        */

    }

    private void addFriend() {
        final String friend = etSearchFriend.getText().toString();
        Log.d(FRIENDTAG, "Adding friend");
        if (!validateForm(friend)) {
            return;
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = mFirebaseDatabase.orderByChild("account").equalTo(friend);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {

                    Toast.makeText(UserMainActivity.this, "Friend found", Toast.LENGTH_SHORT).show();

                    for (DataSnapshot value : dataSnapshot.getChildren()) {
                        friendAccount = value.child("account").getValue().toString();
                        friendUid = value.child("id").getValue().toString();

                        Map<String, Boolean> friend = new HashMap<String, Boolean>();
                        friend.put(friendUid, false);
                        sendNotification(user.getEmail(), friendAccount);
                        makeFriend(friend);
                    }

                } else {
                    Toast.makeText(UserMainActivity.this, "Friend not found", Toast.LENGTH_SHORT).show();
                    System.out.println("not found");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    private void sendNotification(String userAccount, String friendAccount) {

        FriendNotificationPOJO notificationPOJO = new FriendNotificationPOJO(userAccount, friendAccount, true);

        //Redirecting path
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseDatabase.child("notification").push().setValue(notificationPOJO);

    }

    private void makeFriend(Map<String, Boolean> friend) {

        FriendStructPOJO friendStruct = new FriendStructPOJO(friend);

        //Redirecting path
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseDatabase.child("friend list").child(user.getUid()).push().setValue(friendStruct);
    }

    private boolean validateForm(String friend){
        boolean valid = true;
        if (friend.equals(user.getEmail())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this);
            builder.setMessage("You can't add yourself, you sick fuck")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            valid = false;
        } else if (TextUtils.isEmpty(friend)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this);
            builder.setMessage("At least enter something if you want to add")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            valid = false;
        }
        return valid;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            case R.id.action_add_friend:
                addFriend();
                return true;
            case R.id.action_notification:
                Intent notificationIntent = new Intent(UserMainActivity.this, NotificationActivity.class);
                UserMainActivity.this.startActivity(notificationIntent);
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        */
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Toast.makeText(parent.getContext(), "NothingSelected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
    }

}
