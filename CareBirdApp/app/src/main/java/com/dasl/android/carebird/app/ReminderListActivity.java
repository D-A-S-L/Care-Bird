package com.dasl.android.carebird.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Alec on 5/21/2014.
 */
public class ReminderListActivity extends Activity {
    public int h, m;
    public long interval;
    public String n;

    private AlarmManager keeperOfAlarms;
    private ArrayList<ReminderSchedule> toView = new ArrayList<ReminderSchedule>();
    private String itemSelected;
    private PopupMenu deleteOpt;
    private Timer refresher;
    //private Handler mHandler;
    private Runnable mUpdateResults;
    private Status addReminderResponse = null;


    private class ReminderGetter extends AsyncTask<String, Integer, ArrayList<ReminderSchedule>> {
        @Override
        protected ArrayList<ReminderSchedule> doInBackground(String... params) {
            User me = new User(params[0],params[1],"","","");
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
            toView = results;
        }
    }

    private class ReminderAdder extends AsyncTask<String, Integer, ArrayList<ReminderSchedule>> {
        @Override
        protected ArrayList<ReminderSchedule> doInBackground(String... params) {
            User me = new User(params[0],params[1],"","","");
            //com.dasl.android.carebird.app.Status response;
            ArrayList<ReminderSchedule> result = null;

            try {

                //User computer = new User("computer","computer","computer","computer");
                //Database.login(computer);
                //computer.setToken(Database.me.getToken());
                //Database.addCareReceiver("okay");

                addReminderResponse = ((GlobalApplication) getApplication()).getDatabase().addReminderSchedule(new ReminderSchedule(params[2]));

                //Database.addCareGiver("okay");

                //result = response.getMessage();
            }catch (IOException error){
                //result = "failure in try catch";
            }
            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<ReminderSchedule> results) {

        }
    }

    private class ReminderDeleter extends AsyncTask<String, Integer, ArrayList<ReminderSchedule>> {
        @Override
        protected ArrayList<ReminderSchedule> doInBackground(String... params) {
            User me = new User(params[0],params[1],"","","");
            //com.dasl.android.carebird.app.Status response;
            ArrayList<ReminderSchedule> result = null;

            try {

                //User computer = new User("computer","computer","computer","computer");
                //Database.login(computer);
                //computer.setToken(Database.me.getToken());
                //Database.addCareReceiver("okay");

                ((GlobalApplication) getApplication()).getDatabase().removeReminderSchedule(new ReminderSchedule(params[2]));

                //Database.addCareGiver("okay");

                //result = response.getMessage();
            }catch (IOException error){
                //result = "failure in try catch";
            }
            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<ReminderSchedule> results) {
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        keeperOfAlarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        setContentView(R.layout.activity_reminder_list_cr);

        Button syncButton = (Button) findViewById(R.id.sync_button);

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


        String a = Database.me.getUserName();
        String b = Database.me.getPassword();
        new ReminderGetter().execute(new String[]{a, b});

        /*
        try
        {
            toView = ((GlobalApplication) getApplication()).getDatabase().getReminderSchedules();
        } catch (IOException e) {
            return;
        }
        */

        ListView remList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        deleteOpt = new PopupMenu(this, remList);
        deleteOpt.getMenu().add("Delete Reminder");
        deleteOpt.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                deleteReminder(itemSelected);

                return false;
            }
        });

        remList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
                itemSelected = ((ArrayAdapter<String>) ((ListView) findViewById(R.id.listView)).getAdapter()).getItem(index);
                deleteOpt.show();

                return true;
            }
        });

        /*remList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemSelected = ((ArrayAdapter<String>) ((ListView) findViewById(R.id.listView)).getAdapter()).getItem(0);

                return;
            }
        });*/

        refresher = new Timer();
        refresher.schedule (new RefresherTask(), 5000, 5000);

        //final Handler mHandler = new Handler();

        // Create runnable for refreshing the list
        Runnable mUpdateResults = new Runnable() {
            public void run() {
                refreshList();
            }
        };


        if (toView == null) {
            remList.setAdapter(adapter);
            return;
        }

        for (int i = 0; i < toView.size() && toView.get(i) != null; i++) {

            String temp = toView.get(i).toString();

            adapter.add(temp);


            //temp.setText(toView.get(i).toString());
            //remList.addView(temp);
        }

        remList.setAdapter(adapter);

    }

    public void createNewReminder() {
        Intent intent = new Intent(this, CreateReminderActivity.class);
        intent.putExtra("CARE_RECEIVER", true);
        intent.putExtra("USER_NAME", "");
        startActivity(intent);
        /*Random rand = new Random();

        ReminderSchedule test = new ReminderSchedule(rand.nextInt(24), rand.nextInt(60), "Fancy pill", 0);
        System.out.println("creating " + test.toString());

        /*try {
            ((GlobalApplication) getApplication()).getDatabase().addReminderSchedule(test);
        } catch (IOException e) {
            return;
        }

        new ReminderAdder().execute(new String[] {Database.me.getUserName(), Database.me.getPassword(), test.toString()});

        while(addReminderResponse==null)
            try {
                Thread.sleep(333);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        addReminderResponse=null;*/



        refreshList();
    }

    public void deleteReminder(String reminderString) {
        ///* NECFORDATABASE
        new ReminderDeleter().execute(new String[]{Database.me.getUserName(), Database.me.getPassword(), reminderString});
        //*/

        // NOTNEC
        //toView.remove(new ReminderSchedule(reminderString));

        /*while(addReminderResponse==null)
            try {
                Thread.sleep(333);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        addReminderResponse=null;*/

        refreshList();
    }

    public void refreshList() {

        System.out.println("Refreshing list");
        ListView remList = (ListView) findViewById(R.id.listView);


        new ReminderGetter().execute(new String[]{Database.me.getUserName(), Database.me.getPassword()});

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) remList.getAdapter();

        // removing items that are no longer in the database's list
        for (int i = adapter.getCount() - 1; i >= 0; i--) {
            ReminderSchedule temp = new ReminderSchedule(adapter.getItem(i));

            if (!toView.contains(temp)) {
                removeFromAlarms(temp.getKey());


                adapter.remove(adapter.getItem(i));
            }
        }


        // adding items that have been added to database's list
        for (int i = 0; i < toView.size() && toView.get(i) != null; i++) {

            if (adapter.getPosition(toView.get(i).toString()) < 0) {
                addToAlarms(toView.get(i));

                adapter.add(toView.get(i).toString());
            }

            //String temp = toView.get(i).toString();

            //adapter.add(temp);
            //temp.setText(toView.get(i).toString());
            //remList.addView(temp);

        }

        adapter.notifyDataSetChanged();

        //remList.setAdapter(adapter);
        //TextView isThisWorking = (TextView) findViewById(R.id.toPopulate);

        for (int i = 0; i < toView.size(); i++)
            System.out.println("Contents: " + toView.get(i).toString());
    }

    public void addToAlarms(ReminderSchedule schedule) {
        Intent toReminder = new Intent(this, ReminderActivity.class);
        toReminder.putExtra("REMINDER_NAME", schedule.getName());
        toReminder.putExtra("YES_NO", schedule.getMessage());
        toReminder.putExtra("ORIGINAL_ALERT_TIME", Long.parseLong("0"));

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, schedule.getHour());
        cal.set(Calendar.MINUTE, schedule.getMinute());

        PendingIntent temp = PendingIntent.getActivity(this, schedule.getKey(),
                toReminder, PendingIntent.FLAG_UPDATE_CURRENT);

        if (schedule.getInterval() != 0) {
            keeperOfAlarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                    schedule.getInterval(), temp);
        } else {
            keeperOfAlarms.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), temp);
        }


    }

    public void removeFromAlarms(int reminderKey) {
        Intent toReminder = new Intent(this, ReminderActivity.class);

        PendingIntent temp = PendingIntent.getActivity(this, reminderKey, toReminder,
                PendingIntent.FLAG_UPDATE_CURRENT);

        keeperOfAlarms.cancel(temp);
    }

    public void onDestroy() {
        refresher.cancel();

        super.onDestroy();
    }

    private class RefresherTask extends TimerTask {
        public void run() {
            System.out.println("Timer triggered");
            runOnUiThread(new Runnable() {
                public void run() {
                    refreshList();
                }
            });
            //mHandler(mUpdateResults);
        }
    }
}
