package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.Random;


public class QRCodeActivity extends Activity {
    static String contents = null;
    TextView qrDecoded;
    Button scanBtn;
    ImageView qrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getBoolean(getString(R.string.user_type), true)) {
            setContentView(R.layout.activity_qrcode_cg);

            scanBtn = (Button) findViewById(R.id.scanCode);
            scanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                }
            });
            qrDecoded = (TextView) findViewById(R.id.qrDecoded);

            if(contents != null) {
                qrDecoded.setText(contents);
            }
            else
                qrDecoded.setText("Scan not initiated");
        }
        else {
            setContentView(R.layout.activity_qrcode);
            qrImage = (ImageView) findViewById(R.id.qrCode);
            Random rand = new Random(System.currentTimeMillis());
            int tok = rand.nextInt(900000) + 100000; //Six digit permission token
            String qrToken = "" + tok;

            String fname = getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("fname", null);
            String lname = getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("lname", null);
            String name = fname + " " + lname;

            SharedPreferences.Editor pref = getSharedPreferences("BOOT_PREF", MODE_PRIVATE).edit();
            pref.putString("name", name);
            pref.commit();
            int qrCodeDimension = 500;
            Bundle bundle = new Bundle();

            final QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrToken, bundle,
                    Contents.Type.CONTACT, BarcodeFormat.QR_CODE.toString(),
                    qrCodeDimension, getSharedPreferences("BOOT_PREF", MODE_PRIVATE));

            try {
                Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                if (bitmap != null) {
                    qrImage.setImageBitmap(bitmap);
                } else {
                    qrDecoded.setText("INVALID");
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
            new SyncCare().execute(new String[]{qrToken});
        }
        Button doneBtn = (Button) findViewById(R.id.done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String capturedQrValue = intent.getStringExtra("SCAN_RESULT");
                intent.getStringExtra("SCAN_RESULT_FORMAT");

                contents = capturedQrValue;
                String qrVal = contents.substring(9, contents.length()-2).trim();
                String[] val = qrVal.split(";");
                String[] fullName = val[0].split("\\s+");
                String firstName = fullName[0];
                String lastName = fullName[1];
                String userName = val[1].split(":")[1];
                String permToken = val[2].split(":")[1];
                contents = "\nCareReceiver Information:\n\nFirst name: " + firstName +
                        "\nLast name: " + lastName + "\nUsername: " + userName;

                qrDecoded.setText(contents);
                new SyncCare().execute(new String[]{permToken});
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        } else {

        }
    }

    class SyncCare extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            com.dasl.android.carebird.app.Status response;
            String result;
            try {
                if(getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getBoolean(getString(R.string.user_type), true)) {
                    response = ((GlobalApplication)getApplication()).getDatabase().addCareReceiver(strings[0]);
                }
                else {
                    response = ((GlobalApplication)getApplication()).getDatabase().addCareGiver(strings[0]);
                }
                result = response.getMessage();

            }
            catch(IOException e) {
                result = "Cannot sync CareGiver and CareReceiver";
            }
            return result;
        }

        protected void onPostExecute(String result) {
            Context context = getApplicationContext();
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            Log.i("QRCodeActivity", result);
        }
    }
}
