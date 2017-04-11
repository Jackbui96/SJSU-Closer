package com.apackage.nguye.sjsucloser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ArrayList<String> list = new ArrayList<String>();
        list.add("TabooAd wants to add you");

        NotificationAdapter adapter = new NotificationAdapter(list, this);

        ListView lv = (ListView) findViewById(R.id.lvNotification);
        lv.setAdapter(adapter);
    }
}
