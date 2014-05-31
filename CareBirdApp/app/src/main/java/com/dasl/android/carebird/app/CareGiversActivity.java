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
import android.graphics.Color;
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
    CGAdapter adapter;
    private ArrayList<User> mCareGivers;

    private class getCG extends AsyncTask<User, String, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(User... params) {
            User me = params[0];
            ArrayList<User> response = null;
            //ArrayList<User> result;
            try {
                //Database db = new Database();
                response = ((GlobalApplication) getApplication()).getDatabase().getCareGivers(me);
                Log.v("CareGiversActivity response: ",java.util.Arrays.toString(response.toArray()));
                mCareGivers = response;
                //result = response.getMessage();
            }catch (IOException error){
                //result = "failure in try catch";
                System.out.println("ERROR ERROR");
            }
            return response;
        }
        @Override
        protected void onPostExecute(ArrayList<User> result) {
            //---------------------
            //for(User cg:mCareGivers)
            //    System.out.println(cg.getUserName());
            //-------------------------
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new getCG().execute(((GlobalApplication) getApplication()).getMe());
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
        try {
            while(mCareGivers==null)
            Thread.sleep(333);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v("mCareGivers:",java.util.Arrays.toString(mCareGivers.toArray()));
        adapter = new CGAdapter(mCareGivers);
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

    private class CGAdapter extends ArrayAdapter<User> {
        public CGAdapter(ArrayList<User> cg) {
            super(act, android.R.layout.simple_list_item_1, cg);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = act.getLayoutInflater().inflate(R.layout.care_givers_activity, null);
                final ListView listView = getListView();
                listView.setBackgroundColor(Color.parseColor("#03c7d8"));
            }

            final User u = mCareGivers.get(position);

            TextView txt = (TextView)convertView.findViewById(R.id.CareGiver1Name);
            txt.setText(u.toString());

            TextView txt3 = (TextView)convertView.findViewById(R.id.CareGiver1Phone);
            txt3.setText(u.getPhoneNum());

            Button button0 = (Button) convertView.findViewById(R.id.CallButton);
            button0.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent myIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + u.getPhoneNum()));
                    startActivityForResult (myIntent, 0);
                }
            });

			/*TextView repTextView = (TextView)convertView.findViewById(R.id.taq_list_item_repTextView);
            String temp = "";
            if(t.getReputation() > 0) {
                temp = "+" + t.getReputation();
                repTextView.setTextColor(Color.GREEN);
            }
            else if(t.getReputation() < 0) {
                temp = "" + t.getReputation();
                repTextView.setTextColor(Color.RED);
            }
            else {
                temp = "" + t.getReputation();
                repTextView.setTextColor(Color.BLACK);
            }

			repTextView.setText(temp);*/

            //ImageView User Color (if statement)


            return convertView;
        }
    }
}