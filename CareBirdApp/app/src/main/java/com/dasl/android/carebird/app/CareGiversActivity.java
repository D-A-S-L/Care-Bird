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
import android.os.AsyncTask;
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

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by David on 5/9/2014.
 */
public class CareGiversActivity extends ListActivity {

    Activity act = this;

    private ArrayList<User> mCareGivers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        try {
            mCareGivers = ((GlobalApplication) getApplication()).getDatabase().getCareGivers(((GlobalApplication) getApplication()).getMe());
            mCareGivers.add(new User("username", "pass", "first", "last"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        CGAdapter adapter = new CGAdapter(mCareGivers);
        setListAdapter(adapter);
        */
        class getCG extends AsyncTask<User, String, ArrayList<User>> {
            @Override
            protected ArrayList<User> doInBackground(User... params) {
                User me = params[0];
                ArrayList<User> response = null;
                //ArrayList<User> result;
                try {
                    //Database db = new Database();
                    response = Database.getCareGivers(me);
                    //result = response.getMessage();
                }catch (IOException error){
                    //result = "failure in try catch";
                }
                return response;
            }
            @Override
            protected void onPostExecute(ArrayList<User> result) {
                mCareGivers = result;
            }
        }
        new getCG().execute(Database.me);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class CGAdapter extends ArrayAdapter<User> {
        public CGAdapter(ArrayList<User> taqs) {
            super(act, android.R.layout.simple_list_item_1, taqs);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = act.getLayoutInflater().inflate(R.layout.list_item_cg, null);
            }

            final User c = mCareGivers.get(position);

            TextView txt = (TextView) convertView.findViewById(R.id.cg_list_text);
            txt.setText(c.getFirstName() + " " + c.getLastName());

            return convertView;
        }
    }
}