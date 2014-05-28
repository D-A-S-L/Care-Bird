package com.dasl.android.carebird.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by David on 5/9/2014.
 */
public class CareReceiversActivity extends ListActivity {

    Activity act = this;
    CRAdapter adapter;
    private ArrayList<User> mCareReceivers;

    private class getCR extends AsyncTask<User, String, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(User... params) {
            User me = params[0];
            ArrayList<User> response = null;
            //ArrayList<User> result;
            try {
                //Database db = new Database();
                response = ((GlobalApplication) getApplication()).getDatabase().getCareReceivers(me);
                Log.v("CareGiversActivity response: ",java.util.Arrays.toString(response.toArray()));
                mCareReceivers = response;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new getCR().execute(((GlobalApplication) getApplication()).getMe());
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
            while(mCareReceivers==null)
                Thread.sleep(333);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.v("mCareGivers:",java.util.Arrays.toString(mCareReceivers.toArray()));
        adapter = new CRAdapter(mCareReceivers);
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

    private class CRAdapter extends ArrayAdapter<User> {
        public CRAdapter(ArrayList<User> cg) {
            super(act, android.R.layout.simple_list_item_1, cg);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = act.getLayoutInflater().inflate(R.layout.care_receivers_activity, null);
            }

            final User u = mCareReceivers.get(position);

            TextView txt = (TextView)convertView.findViewById(R.id.CareReceiver1Name);
            txt.setText(u.toString());

            TextView txt2 = (TextView)convertView.findViewById(R.id.CareReceiver1UserName);
            txt2.setText(u.getUserName());

            TextView txt3 = (TextView)convertView.findViewById(R.id.CareReceiver1Phone);
            txt3.setText(u.getPhoneNum());

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