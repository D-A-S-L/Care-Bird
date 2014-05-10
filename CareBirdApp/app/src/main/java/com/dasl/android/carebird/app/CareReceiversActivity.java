package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by David on 5/9/2014.
 */
public class CareReceiversActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.care_receivers_activity);

        TextView tview1 = (TextView) findViewById(R.id.CareReceiver1Name);
        tview1.setText("Name: " + getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("otherName", "Null"));

        TextView tview2 = (TextView) findViewById(R.id.CareReceiver1UserName);
        tview2.setText("User Name: " + getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("otherUserName", "Null"));

        TextView tview3 = (TextView) findViewById(R.id.CareReceiver1Phone);
        tview3.setText("Phone: " + getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("otherPhoneNumber", "tel:1111111111").substring(4, 7) + ") " + getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("otherPhoneNumber", "Null").substring(7, 10) + "-" + getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("otherPhoneNumber", "Null").substring(10));

        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(this.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        final Button button = (Button) findViewById(R.id.CallButton2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_CALL, Uri.parse(getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("otherPhoneNumber", "tel:1111111")));
                startActivityForResult (myIntent, 0);
            }
        });
    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }
}
