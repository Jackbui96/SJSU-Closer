package com.apackage.nguye.sjsucloser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import com.google.firebase.database.ValueEventListener;


public class UserMainActivity extends AppCompatActivity implements OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "UserMainActivity";
    private static final String FRIENDTAG = "Add Friend";

    private Toolbar toolbar;
    private TextView tvWelcome;
    private TextView tvBasicInfo;
    private EditText etSearchFriend;
    private Spinner spinner;
    private ListView listView;
    private Button bSubmit;

    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private ValueEventListener dbListener;
    private String mUserKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_drop_down);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = v.getId();
                        if (i == R.id.action_add_friend) {
                            Toast.makeText(UserMainActivity.this, "Add friend clicked",
                                    Toast.LENGTH_SHORT).show();
                            addFriend();
                        }
                    }
                });
        setSupportActionBar(toolbar);
        //getSupportActionBar().setIcon(R.drawable.ic_menu_drop_down);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvBasicInfo = (TextView) findViewById(R.id.tvBasicInfo);
        etSearchFriend = (EditText) findViewById(R.id.etSearchFriend);



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
        /*
        if (!validateForm(friend)) {
            return;
        }
        */
        //mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(friend);
        dbListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("account").getValue().equals(friend)) {
                    Boolean test = true;
                    if (test) {
                        Toast.makeText(UserMainActivity.this, "Found",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserMainActivity.this, "E rror",
                                Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };

    }

    /*
    private boolean validateForm(String friend){
        boolean valid = true;
        if(TextUtils.isEmpty(friend)){
            AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this);
            builder
        }
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_main_menu, menu);
        return true;
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
