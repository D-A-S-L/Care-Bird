package com.dasl.android.carebird.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.util.Log;
import android.widget.TimePicker;

/**
 * Created by Alec on 5/26/2014.
 */

public class ReminderListActivityCG extends Activity {
//public class ReminderListActivityCG extends FragmentActivity {
    private ArrayList<User> careReceivers;
    private String crName;
    private ArrayList<ReminderSchedule> toView = new ArrayList<ReminderSchedule>();
    private String itemSelected;
    private PopupMenu deleteOpt;
    private Timer refresher;
    //private Handler mHandler;
    private Runnable mUpdateResults;


    private class CareReceiverGetter extends AsyncTask<String, Integer, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... params) {
            User me = new User(params[0],params[1],"","","");
            //com.dasl.android.carebird.app.Status response;
            ArrayList<User> result = null;
            try {

                result = ((GlobalApplication) getApplication()).getDatabase().getCareReceivers(me);
                System.out.println("result was: " + result.size());

            }catch (IOException error){
                //result = "failure in try catch";
            }
            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<User> results) {
            careReceivers = results;
        }
    }

    private class ReminderGetter extends AsyncTask<String, Integer, ArrayList<ReminderSchedule>> {
        @Override
        protected ArrayList<ReminderSchedule> doInBackground(String... params) {
            User cr = new User(params[0], "", "", "", "");
            //com.dasl.android.carebird.app.Status response;
            ArrayList<ReminderSchedule> result = null;
            try {

                //User computer = new User("computer","computer","computer","computer");
                //Database.login(computer);
                //computer.setToken(Database.me.getToken());
                //Database.addCareReceiver("okay");

                result = ((GlobalApplication) getApplication()).getDatabase().getReminderSchedules(cr);

                System.out.println("" + result.size());

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
            //ReminderLog.v("carebird", result);
            toView = results;
        }
    }

    private class ReminderAdder extends AsyncTask<String, Integer, ArrayList<ReminderSchedule>> {
        @Override
        protected ArrayList<ReminderSchedule> doInBackground(String... params) {
            User cr = new User(params[0],"","","", "");
            //com.dasl.android.carebird.app.Status response;
            ArrayList<ReminderSchedule> result = null;

            try {

                //User computer = new User("computer","computer","computer","computer");
                //Database.login(computer);
                //computer.setToken(Database.me.getToken());
                //Database.addCareReceiver("okay");

               com.dasl.android.carebird.app.Status status=  ((GlobalApplication) getApplication()).getDatabase().addReminderSchedule(new ReminderSchedule(params[1]), cr);
                result = ((GlobalApplication) getApplication()).getDatabase().getReminderSchedules();
                Log.v("ReminderListActivity.addReminderSchedule: ", status.getMessage());
                //Database.addCareGiver("okay");

                //result = response.getMessage();
            }catch (IOException error){
                //result = "failure in try catch";
            }
            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<ReminderSchedule> results) {
            toView = results;
        }
    }

    private class ReminderDeleter extends AsyncTask<String, Integer, ArrayList<ReminderSchedule>> {
        @Override
        protected ArrayList<ReminderSchedule> doInBackground(String... params) {
            User cr = new User(params[0],"","","","");
            //com.dasl.android.carebird.app.Status response;
            ArrayList<ReminderSchedule> result = null;

            try {

                //User computer = new User("computer","computer","computer","computer");
                //Database.login(computer);
                //computer.setToken(Database.me.getToken());
                //Database.addCareReceiver("okay");

                ((GlobalApplication) getApplication()).getDatabase().removeReminderSchedule(new ReminderSchedule(params[1]), cr);
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
            toView = results;
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
         //ReminderLog.v("carebird", result);
         for(ReminderSchedule result:results)
             toView.add(result);
     }
 }
 */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        crName = null;

        setContentView(R.layout.activity_reminder_list_cg);

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

        new CareReceiverGetter().execute(new String[]{a, b});
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                crName = (String) adapterView.getItemAtPosition(i);

                System.out.println("CRname: " + crName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                crName = null;
            }
        });

        ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);

        for (int i = 0; careReceivers != null && i < careReceivers.size() && careReceivers.get(i) != null; i++) {
            sAdapter.add(careReceivers.get(i).getUserName());
        }

        spinner.setAdapter(sAdapter);

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

        refresher = new Timer();
        refresher.schedule (new RefresherTask(), 2000, 5000);

        //final Handler mHandler = new Handler();

        // Create runnable for refreshing the list
        Runnable mUpdateResults = new Runnable() {
            public void run() {
                refreshList();
            }
        };

        remList.setAdapter(adapter);
        return;
    }

    public void createNewReminder() {
        if (crName == null)
            return;

        Random rand = new Random();

        ReminderSchedule test = new ReminderSchedule(rand.nextInt(24), rand.nextInt(60), "Fancy pill", 0);
        System.out.println("creating " + test.toString());
        /*
        final ReminderSchedule[] test = new ReminderSchedule[1];
        class timeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState){
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Do something with the time chosen by the user
                test[0] = new ReminderSchedule(hourOfDay , minute, "Fancy pill", 0);
                System.out.println("creating " + test[0].toString());
            }
        }
        new timeDialog().show(getFragmentManager(), "timePicker");
        new ReminderAdder().execute(new String[] {crName, test[0].toString()});
        */
        new ReminderAdder().execute(new String[] {crName, test.toString()});

        refreshList();
    }

    public void deleteReminder(String reminderString) {
        new ReminderDeleter().execute(new String[] {crName, reminderString});

        refreshList();
    }

    public void refreshList() {
        if (crName == null)
            return;

        System.out.println("Refreshing list");
        ListView remList = (ListView) findViewById(R.id.listView);

        new ReminderGetter().execute(new String[]{crName});

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) remList.getAdapter();

        // removing items that are no longer in the database's list
        for (int i = adapter.getCount() - 1; i >= 0; i--) {
            ReminderSchedule temp = new ReminderSchedule(adapter.getItem(i));

            if (!toView.contains(temp)) {
                adapter.remove(adapter.getItem(i));
            }
        }


        // adding items that have been added to database's list
        for (int i = 0; i < toView.size() && toView.get(i) != null; i++) {

            if (adapter.getPosition(toView.get(i).toString()) < 0) {
                adapter.add(toView.get(i).toString());
            }

        }

        adapter.notifyDataSetChanged();

        for (int i = 0; i < toView.size(); i++)
            System.out.println("Contents: " + toView.get(i).toString());
    }

    public void onDestroy() {
        refresher.cancel();

        super.onDestroy();
    }

    private class RefresherTask extends TimerTask {
        public void run() {
            System.out.println("Timer triggered");

            //if (crName == null)
                //return;

            runOnUiThread(new Runnable() {
                public void run() {

                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    ArrayAdapter<String> sAdapter = (ArrayAdapter<String>) spinner.getAdapter();

                    if (careReceivers != null) {
                        for (int i = 0; i < careReceivers.size() && careReceivers.get(i) != null; i++) {
                            System.out.println(careReceivers.get(i));
                        }

                    }

                    if (sAdapter.getCount() == 0 && careReceivers != null) {
                        System.out.println("Updating CR list");

                        for (int i = 0; careReceivers != null && i < careReceivers.size() && careReceivers.get(i) != null; i++) {
                            sAdapter.add(careReceivers.get(i).getUserName());
                        }
                    }

                    if (crName != null)
                        refreshList();
                }
            });
            //mHandler(mUpdateResults);
        }
    }
}
