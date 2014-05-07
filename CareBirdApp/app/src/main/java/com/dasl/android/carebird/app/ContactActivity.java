package com.dasl.android.carebird.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by crazz_000 on 5/6/2014.
 */
public class ContactActivity extends Activity {
    ArrayList<Contact> contacts;
    ListView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Set Custom View for the ActionBar
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflate = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.contacts_custom, null);
        actionBar.setCustomView(v);

        //Query Server for list of contacts to add to the Contacts List
        contacts = new ArrayList<Contact>();
        contacts.add(new Contact("crazzzy17", "Amir Sandoval", "626-808-7839", 17));
        //contacts.add(new Contact("dsc", "David Scianni", "111-111-1111", 9));

        contactList = (ListView) findViewById(R.id.list_contacts);
        ContactAdapter adapter = new ContactAdapter(this, contacts);
        contactList.setAdapter(adapter);
        //Add OnItemClickListener
    }
}
