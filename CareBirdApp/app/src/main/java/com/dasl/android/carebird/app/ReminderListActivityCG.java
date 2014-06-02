package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Alec on 5/26/2014.
 */

public class ReminderListActivityCG extends Activity {

    /*
     * This corresponds to the option to view medication reminders.
     * One of the 5 options must be chosen at any point in this activity.
     */
    public final static int REMINDERS = 0;

    /*
     * This corresponds to the option to view medication logs
     */
    public final static int MED_LOGS = 1;

    /*
     * This corresponds to the option to view glucose logs
     */
    public final static int GLUCOSE_LOGS = 2;

    /*
     * This corresponds to the option to view location logs
     */
    public final static int LOCATION_LOGS = 3;

    private int viewing;

    public int h, m;
    public long interval;
    public String n;

    //public class ReminderListActivityCG extends FragmentActivity {
    private ArrayList<User> careReceivers;
    private String crName;
    private ArrayList<ReminderSchedule> toViewRem = new ArrayList<ReminderSchedule>();
    private ArrayList<ReminderLog> toViewLog = new ArrayList<ReminderLog>();
    //private ArrayList<GlucoseLog> toViewGlucoseLog = new ArrayList<GlucoseLog>();
    //private ArrayList<LocationLog> toViewLocationLog = new ArrayList<LocationLog>();
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
            //Log.v("carebird", result);
            toViewRem = results;
        }
    }

    private class ReminderLogGetter extends AsyncTask<String, Integer, ArrayList<ReminderLog>> {
        @Override
        protected ArrayList<ReminderLog> doInBackground(String... params) {
            User cr = new User(params[0], "", "", "", "");
            //com.dasl.android.carebird.app.Status response;
            ArrayList<ReminderLog> result = null;
            try {

                //User computer = new User("computer","computer","computer","computer");
                //Database.login(computer);
                //computer.setToken(Database.me.getToken());
                //Database.addCareReceiver("okay");

                result = ((GlobalApplication) getApplication()).getDatabase().getLogs(cr, params[1], 0);

                System.out.println("" + result.size());

                //Database.addCareGiver("okay");

                //result = response.getMessage();
            }catch (IOException error){
                //result = "failure in try catch";
            }
            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<ReminderLog> results) {
            //Context context = getApplicationContext();
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            //Log.v("carebird", result);
            toViewLog = results;
        }
    }

    /*private class ReminderGetter extends AsyncTask<String, Integer, ArrayList<ReminderSchedule>> {
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
            //Log.v("carebird", result);
            toViewRem = results;
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
            //Log.v("carebird", result);
            toViewRem = results;
        }
    } */

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
            toViewRem = results;
        }
    }

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

        viewing = 0;

        Spinner viewing = (Spinner) findViewById(R.id.spinner);
        viewing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeViewType(i);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                changeViewType(0);
            }
        });

        ArrayAdapter<String> viewTypes = new ArrayAdapter<String>(this, R.layout.spinner_item);
        viewTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewTypes.add("Reminders");
        viewTypes.add("Medication Logs");
        viewTypes.add("Glucose Logs");
        viewTypes.add("Location Logs");
        viewing.setAdapter(viewTypes);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                crName = (String) adapterView.getItemAtPosition(i);

                //System.out.println("CRname: " + crName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                crName = null;
            }
        });

        ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (int i = 0; careReceivers != null && i < careReceivers.size() && careReceivers.get(i) != null; i++) {
            sAdapter.add(careReceivers.get(i).getUserName());
        }

        spinner.setAdapter(sAdapter);

        ListView remList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item);

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

        Intent intent = new Intent(this, CreateReminderActivity.class);
        intent.putExtra("CARE_RECEIVER", false);
        intent.putExtra("USER_NAME", crName);
        startActivity(intent);

        /*Random rand = new Random();

        ReminderSchedule test = new ReminderSchedule(rand.nextInt(24), rand.nextInt(60), "Fancy pill", 0);
        System.out.println("creating " + test.toString());*/
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
        //new ReminderAdder().execute(new String[] {crName, test.toString()});

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
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) remList.getAdapter();

        if (viewing == REMINDERS) {

            new ReminderGetter().execute(new String[]{crName});

            // removing all items from adapter
            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                adapter.remove(adapter.getItem(i));
            }

            // adding items from the database's retrieved list
            for (int i = 0; i < toViewRem.size() && toViewRem.get(i) != null; i++) {
                adapter.add(toViewRem.get(i).toString());
            }

            adapter.notifyDataSetChanged();

            for (int i = 0; i < toViewRem.size(); i++)
                System.out.println("Contents: " + toViewRem.get(i).toString());

            return;
        } else if (viewing == MED_LOGS) {
            new ReminderLogGetter().execute(new String[] {crName, PillLog.type});
        } else if (viewing == GLUCOSE_LOGS) {
            new ReminderLogGetter().execute(new String[] {crName, GlucoseLog.type});
        } else {
            new ReminderLogGetter().execute(new String[] {crName, LocationLog.type});
        }

        // removing all items from adapter
        for (int i = adapter.getCount() - 1; i >= 0; i--) {
            adapter.remove(adapter.getItem(i));
        }

        // adding items from the database's retrieved list
        for (int i = 0; i < toViewLog.size() && toViewLog.get(i) != null; i++) {
            adapter.add(toViewLog.get(i).toString());
        }

        adapter.notifyDataSetChanged();

        for (int i = 0; i < toViewLog.size() && toViewLog.get(i) != null; i++)
            System.out.println("Contents: " + toViewLog.get(i).toString());
    }

    public void onDestroy() {
        refresher.cancel();

        super.onDestroy();
    }

    public void changeViewType(int type) {
        viewing = type;
        Button addButton = (Button) findViewById(R.id.add_button);

        if (type == REMINDERS) {
            addButton.setEnabled(true);
        } else {
            addButton.setEnabled(false);
        }

        refreshList();
    }

    private class RefresherTask extends TimerTask {
        public void run() {
            System.out.println("Timer triggered");

            //if (crName == null)
            //return;

            runOnUiThread(new Runnable() {
                public void run() {

                    Spinner spinner = (Spinner) findViewById(R.id.spinner2);
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
