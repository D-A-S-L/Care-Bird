package com.dasl.android.carebird.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alec on 5/21/2014.
 */
public class ReminderListActivity extends Activity {
    private final ArrayList<ReminderSchedule> toView = new ArrayList<ReminderSchedule>();
    class getReminders extends AsyncTask<String, Integer, ArrayList<ReminderSchedule>> {
        @Override
        protected ArrayList<ReminderSchedule> doInBackground(String... params) {
            User me = new User(params[0],params[1],"","");
            //com.dasl.android.carebird.app.Status response;
            ArrayList<ReminderSchedule> result = null;
            try {

                //User computer = new User("computer","computer","computer","computer");
                //Database.login(computer);
                //computer.setToken(Database.me.getToken());
                //Database.addCareReceiver("okay");

                result = ((GlobalApplication) getApplication()).getDatabase().getReminderSchedules();

                //Database.addCareGiver("okay");

                //result = response.getMessage();
            }catch (IOException error){
                //result = "failure in try catch";
            }
            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<ReminderSchedule> results) {
            //Context context = getApplicationContext();
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            //Log.v("carebird", result);
            for(ReminderSchedule result:results)
                toView.add(result);
        }
    }
       /*
    class setReminders extends AsyncTask<ArrayList<ReminderSchedule>, Integer,String > {
        @Override
        protected String doInBackground(ArrayList<ReminderSchedule>... params) {
            User me = new User(params[0],params[1],"","");
            //com.dasl.android.carebird.app.Status response;
            ArrayList<ReminderSchedule> result = null;
            try {

                //User computer = new User("computer","computer","computer","computer");
                //Database.login(computer);
                //computer.setToken(Database.me.getToken());
                //Database.addCareReceiver("okay");

                result = ((GlobalApplication) getApplication()).getDatabase().getReminderSchedules();

                //Database.addCareGiver("okay");

                //result = response.getMessage();
            }catch (IOException error){
                //result = "failure in try catch";
            }
            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<ReminderSchedule> results) {
            //Context context = getApplicationContext();
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            //Log.v("carebird", result);
            for(ReminderSchedule result:results)
                toView.add(result);
        }
    }
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_reminder_list);

        ImageButton syncButton = (ImageButton) findViewById(R.id.sync_button);

        syncButton.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                   refreshList();

               }
        });

        Button addButton = (Button) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createNewReminder();
            }
        });


        new getReminders().execute(new String[]{Database.me.getUserName(), Database.me.getPassword()});
        /*
        try
        {
            toView = ((GlobalApplication) getApplication()).getDatabase().getReminderSchedules();
        } catch (IOException e) {
            return;
        }
        */
        ListView remList = (ListView) findViewById(R.id.listView);

        if (toView == null)
            return;

        for (int i = 0; i < toView.size() && toView.get(i) != null; i++) {
            TextView temp = new TextView(this);

            temp.setText(toView.get(i).toString());
            remList.addView(temp);
        }

    }

    public void createNewReminder() {
        ReminderSchedule test = new ReminderSchedule(3, 30, "Fancy pill", 0);

        try {
            ((GlobalApplication) getApplication()).getDatabase().addReminderSchedule(test);
        } catch (IOException e) {
            return;
        }

        refreshList();
    }

    public void refreshList() {
        ListView remList = (ListView) findViewById(R.id.listView);
        //ArrayList<ReminderSchedule> toView;

        new getReminders().execute(new String[]{Database.me.getUserName(), Database.me.getPassword()});
        /*
        try {
            toView = ((GlobalApplication) getApplication()).getDatabase().getReminderSchedules();

        } catch (IOException e) {
            return;
        }
        */
        remList.removeAllViews();

        for (int i = 0; i < toView.size() && toView.get(i) != null; i++) {
            TextView temp = new TextView(this);
            temp.setText(toView.get(i).toString());
            remList.addView(temp);
        }
    }
}
