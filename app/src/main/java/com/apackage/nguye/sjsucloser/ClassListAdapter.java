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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Jack on 05/12/17.
 */

public class ClassListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> classList = new ArrayList<String>();
    private Context context;

    private String classId;

    private FirebaseUser mUser;
    private DatabaseReference mFirebaseDatabase;

    public ClassListAdapter(ArrayList<String> classList, Context context) {
        this.classList = classList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return classList.size();
    }

    @Override
    public Object getItem(int pos) {
        return classList.get(pos);
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
            view = inflater.inflate(R.layout.class_list, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView) view.findViewById(R.id.tvClassList);
        listItemText.setText(classList.get(position));

        //Handle buttons and add onClickListeners
        Button bEdit = (Button) view.findViewById(R.id.bEdit);
        Button bDelete = (Button) view.findViewById(R.id.bDelete);

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClass(position);
            }
        });

        return view;
    }

    private void deleteClass(int position) {

        classId = classList.get(position);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("class list").child(classId).child(mUser.getUid());
        mFirebaseDatabase.setValue(null);

    }


}
