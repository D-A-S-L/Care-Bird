package com.dasl.android.carebird.app;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by David on 5/9/2014.
 */
public class CareGiversActivity extends ListActivity {

    Activity act = this;

    private ArrayList<CareGiver> mCareGivers;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            CGAdapter adapter = new CGAdapter(mCareGivers);
            setListAdapter(adapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub
            super.onActivityResult(requestCode, resultCode, data);
        }

private class CGAdapter extends ArrayAdapter<CareGiver> {
    public CGAdapter(ArrayList<CareGiver> taqs) {
        super(act, android.R.layout.simple_list_item_1, taqs);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = act.getLayoutInflater().inflate(R.layout.list_item_cg, null);
        }

        final CareGiver c = mCareGivers.get(position);

        TextView txt = (TextView)convertView.findViewById(R.id.cg_list_text);
        txt.setText(c.getFname() + " " + c.getLname() + "\n" + c.getPhoneNumber());

        return convertView;
    }
}
}