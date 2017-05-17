
package com.apackage.nguye.sjsucloser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Jack on 04/10/17.
 */

public class NotificationAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    private FirebaseUser user;
    private String friendUid;
    private DatabaseReference mFirebaseDatabase;

    public NotificationAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.notification, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView) view.findViewById(R.id.tvNotification);
        listItemText.setText(list.get(position) /*+ " wants to add you"*/);

        //Handle buttons and add onClickListeners
        Button bAccept = (Button) view.findViewById(R.id.bAccept);
        Button bDecline = (Button) view.findViewById(R.id.bDecline);

        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotification(position);
                acceptFriend(position);
                notifyDataSetChanged();
            }
        });
        bDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotification(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    private void deleteNotification(int position) {
        System.out.println("checking: " + list.get(position));

        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("notification list");
        Query query = mFirebaseDatabase.orderByChild("src").equalTo(list.get(position));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {

                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                    String key = nodeDataSnapshot.getKey();
                    mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
                    mFirebaseDatabase.child("notification list").child(key).child("notification").setValue(false);
                    //System.out.println("checking for ref: " + mFirebaseDatabase.child("notification list").child(key).child("notification").getRef());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }


    private void acceptFriend(int position) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        friendUid = list.get(position);

        //Redirecting path
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase.child("friend list").child(user.getUid()).child(friendUid).setValue(true);

        //Redirecting path
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase.child("friend list").child(friendUid).child(user.getUid()).setValue(true);

    }
}