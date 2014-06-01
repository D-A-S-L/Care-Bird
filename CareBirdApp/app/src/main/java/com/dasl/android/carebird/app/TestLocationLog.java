package com.dasl.android.carebird.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by crazz_000 on 5/31/2014.
 */
public class TestLocationLog extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btn = (Button) findViewById(R.id.test_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LogLocation().execute(new String[]{});
            }
        });
    }

    class LogLocation extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            com.dasl.android.carebird.app.Status response = null;
            String result = null;
            double lat = 175.42;
            double lon = 23.32;
            double currLat = 123.12;
            double currLon = 12.123;

            try {
                response = ((GlobalApplication) getApplication()).getDatabase().addLog(new LocationLog(System.currentTimeMillis(),
                        45.76, currLat, currLon));
                result = response.getMessage();
            }
            catch(IOException e) {
                result = "Could not log location";
            }
            android.util.Log.i("LogLocation", result);
            return result;
        }
    }
}
