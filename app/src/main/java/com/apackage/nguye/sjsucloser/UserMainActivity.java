package com.apackage.nguye.sjsucloser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class UserMainActivity extends AppCompatActivity implements OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        Intent intent = getIntent();
        final TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        final Spinner spinner = (Spinner) findViewById(R.id.optionSpinner);
        final ListView listView = (ListView) findViewById(R.id.lvFriendList);
        final Button submitButton = (Button) findViewById(R.id.bSubmit);

        final String usrFirstName = intent.getStringExtra("firstName");
        final String usrLastName = intent.getStringExtra("lastName");
        final String usrAccount = intent.getStringExtra("account");

        spinner.setOnItemSelectedListener(this);
        List<String> option = new ArrayList<String>();
        option.add("Add Friend");
        option.add("Add Class");
        option.add("Match Class");
        option.add("Send Email");

        String welcomeMessage = "Welcome, " + usrFirstName + usrLastName + "\nHere is your profile"
                + "\nYour current account: " + usrAccount + "\nFirst Name: " + usrFirstName + "\nLast Name: " + usrLastName;
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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    private List<String> getFriendList(String usrAccount) {
        final List<String> friendList = new ArrayList<String>();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("Response:" + response);

                try {
                    JSONArray result = new JSONArray(response);
                    for (int i = 0; i < result.length(); i++) {
                        friendList.add(result.getJSONObject(i).getString("friendList"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetFriendListRequest request = new GetFriendListRequest(usrAccount, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(UserMainActivity.this);
        requestQueue.add(request);
        return friendList;
    }

}
