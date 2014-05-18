package com.dasl.android.carebird.app;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by David on 5/17/2014.
 */
public class SchedulerCGActivity extends Activity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_cg);

        Spinner spinner = (Spinner) findViewById(R.id.Schedule_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.schedule_type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        EditText pillName = (EditText) findViewById(R.id.pill_name);
        pillName.setVisibility(View.GONE);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

        getSharedPreferences("BOOT_PREF", MODE_PRIVATE).edit().putString("type", (String) parent.getItemAtPosition(pos)).commit();

        if(pos == 0) {
            EditText pillName = (EditText) findViewById(R.id.pill_name);
            pillName.setVisibility(View.GONE);
        } else {
            EditText pillName = (EditText) findViewById(R.id.pill_name);
            pillName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
