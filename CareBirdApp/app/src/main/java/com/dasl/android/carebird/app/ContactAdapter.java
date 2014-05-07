package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by crazz_000 on 5/6/2014.
 */
public class ContactAdapter extends BaseAdapter {
    ArrayList<Contact> contacts;
    Context context;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    public int getCount() {
        return contacts.size();
    }

    public Object getItem(int position) {
        return contacts.get(position);
    }

    public long getItemId(int position) {
        return contacts.indexOf(getItem(position));
    }

    private class ViewHolder {
        TextView name;
        TextView id;
        TextView phone;
        TextView userName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(view == null) {
            view = mInflater.inflate(R.layout.list_contact_item, null);
            holder = new ViewHolder();
            holder.name =(TextView) view.findViewById(R.id.real_name);
            holder.phone = (TextView) view.findViewById(R.id.phone_num);
            holder.id = (TextView) view.findViewById(R.id.id_num);
            holder.userName = (TextView) view.findViewById(R.id.user_name);

            Contact contact = contacts.get(i);
            holder.name.setText(contact.getPersonName());
            holder.phone.setText(contact.getPhoneNum());
            holder.id.setText("" + contact.getUserId());
            holder.userName.setText(contact.getUserName());
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }
}
