package com.dasl.android.carebird.app;

import android.app.Activity;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList toView;

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

        try
        {
            toView = ((GlobalApplication) getApplication()).getDatabase().getReminderSchedules();
        } catch (IOException e) {
            return;
        }

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
        ArrayList<ReminderSchedule> toView;

        try {
            toView = ((GlobalApplication) getApplication()).getDatabase().getReminderSchedules();
        } catch (IOException e) {
            return;
        }

        remList.removeAllViews();

        for (int i = 0; i < toView.size() && toView.get(i) != null; i++) {
            TextView temp = new TextView(this);
            temp.setText(toView.get(i).toString());
            remList.addView(temp);
        }
    }
}
