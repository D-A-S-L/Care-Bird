package com.dasl.android.carebird.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alec on 5/30/2014.
 */
public class CreateReminderActivity extends Activity {

    public int h, m;
    public long interval;
    public String n;

    private boolean forSelf;
    private String receiverName;

    private AlarmManager keeperOfAlarms;
    private Status addReminderResponse = null;


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

    private class ReminderAdderCG extends AsyncTask<String, Integer, ArrayList<ReminderSchedule>> {
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
                android.util.Log.v("ReminderListActivity.addReminderSchedule: ", status.getMessage());
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

        final Calendar c = Calendar.getInstance();
        h = c.get(Calendar.HOUR_OF_DAY);
        m = c.get(Calendar.MINUTE);

        keeperOfAlarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        forSelf = getIntent().getExtras().getBoolean("CARE_RECEIVER");
        receiverName = getIntent().getExtras().getString("USER_NAME");

        setContentView(R.layout.activity_create_reminder);

        TextView timeText = (TextView) findViewById(R.id.timeText);
        timeText.setText("Time: " + h + ":" + m);


        ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        sAdapter.add("Daily");
        sAdapter.add("Twice daily");
        sAdapter.add("Weekly");
        sAdapter.add("Just once");

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String interv = (String) adapterView.getItemAtPosition(i);

                if (interv.equals("Daily")) {
                    interval = ReminderSchedule.DAILY;
                } else if (interv.equals("Twice daily")) {
                    interval = ReminderSchedule.TWICE_DAILY;
                } else if (interv.equals("Weekly")) {
                    interval = ReminderSchedule.WEEKLY;
                } else if (interv.equals(("Just once"))) {
                    interval = ReminderSchedule.ONCE;
                }

                //System.out.println("CRname: " + crName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                interval = ReminderSchedule.ONCE;
            }
        });

        Button timeButton = (Button) findViewById(R.id.setTimeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        Button createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createReminder();
            }
        });

    }

    /*public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        try {
            ReminderListActivity activity = (ReminderListActivity) getActivity();
            activity.h = hourOfDay;
            activity.m = minute;
        } catch (ClassCastException e) {
            ReminderListActivityCG activity = (ReminderListActivityCG) getActivity();
            activity.h = hourOfDay;
            activity.m = minute;
        }

        // set TextField text
    }
*/
    private void createReminder() {
        EditText nameEntry = (EditText) findViewById(R.id.remNameField);

        if (nameEntry.getText().toString().equals(""))
            return;

        ReminderSchedule newReminder = new ReminderSchedule(h, m, nameEntry.getText().toString(), interval);
        System.out.println("creating " + newReminder.toString());

        // create and add the reminder
        if (forSelf) {
            new ReminderAdder().execute(new String[] {Database.me.getUserName(), Database.me.getPassword(), newReminder.toString()});
        } else {
            System.out.println("Attempt to add schedule: " + newReminder.toString());
            new ReminderAdderCG().execute(new String[] {receiverName, newReminder.toString()});
        }


        /*while (addReminderResponse == null) {
            try {
                Thread.sleep(333);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } */

        addReminderResponse = null;

        finish();
    }

    public void updateTimeText() {
        TextView timeText = (TextView) findViewById(R.id.timeText);

        timeText.setText("Time: " + h + ":" + m);
    }
}
