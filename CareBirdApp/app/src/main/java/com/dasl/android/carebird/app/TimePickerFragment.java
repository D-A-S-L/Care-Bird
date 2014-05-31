package com.dasl.android.carebird.app;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by David on 5/17/2014.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ((CreateReminderActivity) getActivity()).h = hourOfDay;
        ((CreateReminderActivity) getActivity()).m = hourOfDay;
        ((CreateReminderActivity) getActivity()).updateTimeText();

        // Do something with the time chosen by the user
        getActivity().getSharedPreferences("BOOT_PREF", getActivity().MODE_PRIVATE).edit().putInt("hour", hourOfDay).commit();
        getActivity().getSharedPreferences("BOOT_PREF", getActivity().MODE_PRIVATE).edit().putInt("minute", minute).commit();
    }
}