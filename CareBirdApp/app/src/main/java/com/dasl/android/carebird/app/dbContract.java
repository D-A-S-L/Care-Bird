/**
 * Created by brian on 5/11/14.
 *
 * this file stores the schema for the database
 */
package com.dasl.android.carebird.app;

import android.provider.BaseColumns;

public final class dbContract {

    private static final String TEXT = " TEXT";
    private static final String REAL = " REAL";
    private static final String INTEGER = " INTEGER";
    private static final String BLOB = " BLOB";
    private static final String COMMA = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public dbContract() {}

    /* defines the table contents */
    public static abstract class pillTable implements BaseColumns {
        /**
         * inheriting from BaseColumns gives this class gives two strings (_ID , _COUNT)
         * that are 'public static final' for uniquely identifying a row and counting
         * the number of rows in a table
         */
        public static final String TABLE_NAME = "Pill_Log";
        public static final String COLUMN_NAME_ORIGINAL_ALERT_TIME = "alert_time";
        public static final String COLUMN_NAME_LOG_TIME = "log_time";
        public static final String COLUMN_NAME_MESSAGE = "message";
        public static final String COLUMN_NAME_ACTION_TAKEN = "action_taken";
    }
    public static final String CREATE_PILL_TABLE = "CREATE TABLE IF NOT EXIST"+ pillTable.TABLE_NAME + "(" +
            pillTable.COLUMN_NAME_MESSAGE + TEXT + COMMA +
            pillTable.COLUMN_NAME_ACTION_TAKEN + TEXT + COMMA +
            pillTable.COLUMN_NAME_ORIGINAL_ALERT_TIME + " INTEGER DEFAULT CURRENT_TIMESTAMP," +
            pillTable.COLUMN_NAME_LOG_TIME + " INTEGER DEFAULT CURRENT_TIMESTAMP," +
            pillTable._ID + " INTEGER PRIMARY KEY" + ");";

    /* defines the table contents */
    public static abstract class pedometerTable implements BaseColumns {
        /**
         * inheriting from BaseColumns gives this class gives two strings (_ID , _COUNT)
         * that are 'public static final' for uniquely identifying a row and counting
         * the number of rows in a table
         */
        public static final String TABLE_NAME = "Pedometer_Log";
        public static final String COLUMN_NAME_LOG_TIME = "log_time";
        public static final String COLUMN_NAME_INTERVAL = "interval"; //i.e. month, week, year...
        public static final String COLUMN_NAME_STEPS_TAKEN = "steps_taken";
    }
    public static final String CREATE_PEDOMETER_TABLE = "CREATE TABLE IF NOT EXIST"+ pedometerTable.TABLE_NAME + "(" +
            pedometerTable.COLUMN_NAME_INTERVAL + TEXT + COMMA +
            pedometerTable.COLUMN_NAME_STEPS_TAKEN + INTEGER + COMMA +
            pedometerTable.COLUMN_NAME_LOG_TIME + " INTEGER DEFAULT CURRENT_TIMESTAMP," +
            pedometerTable._ID + " INTEGER PRIMARY KEY" + ");";

    /* defines the table contents */
    public static abstract class glucoseTable implements BaseColumns {
        /**
         * inheriting from BaseColumns gives this class gives two strings (_ID , _COUNT)
         * that are 'public static final' for uniquely identifying a row and counting
         * the number of rows in a table
         */
        public static final String TABLE_NAME = "Glucose_Log";
        public static final String COLUMN_NAME_ORIGINAL_ALERT_TIME = "alert_time";
        public static final String COLUMN_NAME_LOG_TIME = "log_time";
        public static final String COLUMN_NAME_GLUCOSE_VALUE = "glucose_value";
    }
    public static final String CREATE_GLUCOSE_TABLE = "CREATE TABLE IF NOT EXIST"+ glucoseTable.TABLE_NAME + "(" +
            glucoseTable.COLUMN_NAME_GLUCOSE_VALUE + REAL + COMMA +
            glucoseTable.COLUMN_NAME_ORIGINAL_ALERT_TIME + " INTEGER DEFAULT CURRENT_TIMESTAMP," +
            glucoseTable.COLUMN_NAME_LOG_TIME + " INTEGER DEFAULT CURRENT_TIMESTAMP," +
            glucoseTable._ID + " INTEGER PRIMARY KEY" + ");";

    /* defines the table contents */
    public static abstract class locationTable implements BaseColumns {
        /**
         * inheriting from BaseColumns gives this class gives two strings (_ID , _COUNT)
         * that are 'public static final' for uniquely identifying a row and counting
         * the number of rows in a table
         */
        public static final String TABLE_NAME = "Location_Log";
        public static final String COLUMN_NAME_ORIGINAL_ALERT_TIME = "alert_time";
        public static final String COLUMN_NAME_LOG_TIME = "log_time";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_DISTANCE_FROM_HOME = "distance_from_home";
    }
    public static final String CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXIST"+ locationTable.TABLE_NAME + "(" +
            locationTable.COLUMN_NAME_LOCATION + TEXT + COMMA +
            locationTable.COLUMN_NAME_DISTANCE_FROM_HOME + INTEGER + COMMA +
            locationTable.COLUMN_NAME_ORIGINAL_ALERT_TIME + " INTEGER DEFAULT CURRENT_TIMESTAMP," +
            locationTable.COLUMN_NAME_LOG_TIME + " INTEGER DEFAULT CURRENT_TIMESTAMP," +
            locationTable._ID + " INTEGER PRIMARY KEY" + ");";

    /* defines the table contents */
    public static abstract class scheduleTable implements BaseColumns {
        /**
         * inheriting from BaseColumns gives this class gives two strings (_ID , _COUNT)
         * that are 'public static final' for uniquely identifying a row and counting
         * the number of rows in a table
         */
        public static final String TABLE_NAME = "Active_Schedules";
        public static final String COLUMN_NAME_ORIGINAL_ALERT_TIME = "alert_time";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_OPTIONAL_MESSAGE = "optional_message";
        public static final String COLUMN_NAME_OPTIONAL_REPEATES = "optional_repeats";
    }
    public static final String CREATE_SCHEDULE_TABLE = "CREATE TABLE IF NOT EXIST"+ scheduleTable.TABLE_NAME + "(" +
            scheduleTable.COLUMN_NAME_TYPE + TEXT + COMMA +
            scheduleTable.COLUMN_NAME_OPTIONAL_MESSAGE + TEXT + COMMA +
            scheduleTable.COLUMN_NAME_OPTIONAL_REPEATES + INTEGER + COMMA +
            scheduleTable.COLUMN_NAME_ORIGINAL_ALERT_TIME + " INTEGER DEFAULT CURRENT_TIMESTAMP," +
            scheduleTable._ID + " INTEGER PRIMARY KEY" + ");";

    /* defines the table contents */
    public static abstract class usersTable implements BaseColumns {
        /**
         * inheriting from BaseColumns gives this class gives two strings (_ID , _COUNT)
         * that are 'public static final' for uniquely identifying a row and counting
         * the number of rows in a table
         */
        public static final String TABLE_NAME = "Pill_Log";
        public static final String COLUMN_NAME_LOG_TIME = "log_time";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
    }
    public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXIST"+ usersTable.TABLE_NAME + "(" +
            usersTable.COLUMN_NAME_TYPE + TEXT + COMMA +
            usersTable.COLUMN_NAME_PASSWORD + TEXT + COMMA +
            usersTable.COLUMN_NAME_FIRST_NAME + TEXT + COMMA +
            usersTable.COLUMN_NAME_LAST_NAME + TEXT + COMMA +
            usersTable.COLUMN_NAME_LOG_TIME + " INTEGER DEFAULT CURRENT_TIMESTAMP," +
            usersTable._ID + " INTEGER PRIMARY KEY" + ");";

}
