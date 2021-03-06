/**
 * Created by brian on 5/9/14.
 */
package com.dasl.android.carebird.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.util.Log;


public class dbAdapter{

    private static final String PILL_LOGGER_TABLE = "Pill_Logger";
    private static final String PEDOMETER_LOGGER_TABLE = "Pedometer_Logger";
    private static final String GLUCOSE_LOGGER_TABLE = "Glucose_Logger";
    private static final String LOCATION_LOGGER_TABLE = "Location_Logger";
    private static final String SCHEDULE_TABLE = "Schedules";
    private static final String USERS_TABLE = "Users";

    private static final String ID = "_ID INTEGER PRIMARY KEY AUTOINCREMENT,";
    private static final String ORIGINAL_ALERT_TIME = " alert_time DATE,";
    private static final String LOG_TIME = " log_time DATE,";

    private static final String TAG = "dbAdapter";
    private DatabaseHelper dbHelper;
    private static SQLiteDatabase db;

    //make sure this matches the
    //package com.MyPackage;
    //at the top of this file
    //set in the constructor when the context is known
    private static String DB_PATH;

    //make sure this matches your database name in your assets folder
    // my database file does not have an extension on it
    // if yours does
    // add the extention
    private static final String DATABASE_NAME = "configs.db";

    //versions will change when major modifications to the database occur like new tables or new columns in tables
    private static final int DATABASE_VERSION = 1;

    private final Context adapterContext;

    public dbAdapter(Context context) {
        this.adapterContext = context;
        this.DB_PATH = adapterContext.getFilesDir().getPath() + "/data/com.dasl.android.carebird.app/databases/";
    }

    public dbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(adapterContext);

        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            dbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        return this;
    }
    //Usage from outside
    // dbAdapter dba = new dbAdapter(contextObject); //in my case contextObject is a Map
    // dba.open();
    // Cursor c = dba.ExampleSelect("Rawr!");
    // contextObject.startManagingCursor(c);
    // String s1 = "", s2 = "";
    // if(c.moveToFirst())
    // do {
    //  s1 = c.getString(0);
    //  s2 = c.getString(1);
    //  } while (c.moveToNext());
    // dba.close();
    public Cursor ExampleSelect(String myVariable)
    {
        String query = "SELECT locale, ? FROM android_metadata";
        return db.rawQuery(query, new String[]{myVariable});
    }

    //Usage
    // dbAdatper dba = new dbAdapter(contextObjecT);
    // dba.open();
    // dba.ExampleCommand("en-CA", "en-GB");
    // dba.close();
    public void ExampleCommand(String myVariable1, String myVariable2)
    {
        String command = "INSERT INTO android_metadata (locale) SELECT ? UNION ALL SELECT ?";
        db.execSQL(command, new String[]{myVariable1, myVariable2});
    }

    public void close() {
        dbHelper.close();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        Context helperContext;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            helperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(PillLogTable());
            db.execSQL(PedometerLogTable());
            db.execSQL(GlucoseLogTable());
            db.execSQL(LocationLogTable());
            db.execSQL(ScheduleTable());
            db.execSQL(UsersTable());
        }

        //creates the pill logger table with the below column names
        private String PillLogTable(){
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + PILL_LOGGER_TABLE +
                    "(" + ID + ORIGINAL_ALERT_TIME + LOG_TIME + " message TEXT, action_taken TEXT);";
        }

        //creates the pedometer logging table with the below columns. duration i.e. month or week specifies the time duration of the steps_taken value
        private String PedometerLogTable(){
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + PEDOMETER_LOGGER_TABLE +
                    "(" + ID + LOG_TIME + " duration TEXT, steps_taken INTEGER);";
        }

        /*creates the glucose logging table with the below values. "snooze" is determined by a null value in the glucose_value.
        "dismiss" is determined by a null value in the glucose_value and the last entry for the specifies ORIGINAL_ALERT_TIME.
        */
        private String GlucoseLogTable() {
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + GLUCOSE_LOGGER_TABLE +
                    "(" + ID + ORIGINAL_ALERT_TIME + LOG_TIME + " glucose_value REAL);";
        }

        //creates the location log table
        private String LocationLogTable() {
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + LOCATION_LOGGER_TABLE +
                    "(" + ID + ORIGINAL_ALERT_TIME + LOG_TIME + " location TEXT, distance_from_home REAL);";
        }

        //creates the schedule table so alerts are persistent. column names will be the inputs to what ever scheduler we use
        private String ScheduleTable() {
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + SCHEDULE_TABLE +
                    "(" + ID + ORIGINAL_ALERT_TIME + " type TEXT, optional_message TEXT, optional_repeats INTEGER);";
        }

        private String UsersTable() {
            return "CREATE TABLE" + "IF NOT EXIST" + DATABASE_NAME + "." + USERS_TABLE +
                    "(" + ID + LOG_TIME + " type TEXT, user_name TEXT, password TEXT, first_name TEXT, last_name TEXT,);";
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //this is version one there is nothing to update

            /*
            ReminderLog.w(TAG, "Upgrading database!!!!!");
            //db.execSQL("");
            onCreate(db);
            */
        }

        public void createDataBase() throws IOException {
            boolean dbExist = checkDataBase();
            if (!dbExist) {
                //make sure your database has this table already created in it
                //this does not actually work here
                /*
                 * db.execSQL("CREATE TABLE IF NOT EXISTS \"android_metadata\" (\"locale\" TEXT DEFAULT 'en_US')"
                 * );
                 * db.execSQL("INSERT INTO \"android_metadata\" VALUES ('en_US')"
                 * );
                 */
                this.getReadableDatabase();
                try {
                    copyDataBase();
                } catch (IOException e) {
                    throw new Error("Error copying database");
                }
            }
        }

        public SQLiteDatabase getDatabase() {
            String myPath = DB_PATH + DATABASE_NAME;
            return SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        }

        private boolean checkDataBase() {
            SQLiteDatabase checkDB = null;
            try {
                String myPath = DB_PATH + DATABASE_NAME;
                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READONLY);
            } catch (SQLiteException e) {
            }
            if (checkDB != null) {
                checkDB.close();
            }
            return checkDB != null;
        }

        private void copyDataBase() throws IOException {

            // Open your local db as the input stream
            InputStream myInput = helperContext.getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = DB_PATH + DATABASE_NAME;

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }

        public void openDataBase() throws SQLException {
            // Open the database
            String myPath = DB_PATH + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }

        @Override
        public synchronized void close() {

            if (db != null)
                db.close();

            super.close();

        }
    }

    public void addPillLog(Date orginalAlertTime, String message, String action){
        db.execSQL("INSERT INTO" + DATABASE_NAME + "." + PILL_LOGGER_TABLE +
                "(alert_time, log_time, message, action_taken)" +
                "VALUES" +
                "(" + orginalAlertTime + ", " + new Date() + ", " + message + ", " +action + ");");
    }

    public void addUser(String type, String userName, String password, String firstName, String lastName){
        db.execSQL("INSERT INTO" + DATABASE_NAME + "." + USERS_TABLE +
                "(log_time, type, user_name, password, first_name, last_name)" +
                "VALUES" +
                "(" +  new Date() + ", " + type + ", " + userName + ", " + password + ", " + firstName + ", " + lastName + ");");
    }

}