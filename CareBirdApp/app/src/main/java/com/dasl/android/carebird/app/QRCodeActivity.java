package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;


public class QRCodeActivity extends Activity {
    static String contents = null; //contents not needed??????? also add Activity to Settings Bar
    TextView qrDecoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getBoolean(getString(R.string.user_type), true)) {
            setContentView(R.layout.activity_qrcode_cg);
        } else {
            setContentView(R.layout.activity_qrcode);
        }
        ImageView qrImage = (ImageView) findViewById(R.id.qrCode);
        Button scanBtn = (Button) findViewById(R.id.scanCode);
        qrDecoded = (TextView) findViewById(R.id.qrDecoded);

        String qrData = getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("myPhoneNumber", null);
        int qrCodeDimension = 500;
        Bundle bundle = new Bundle();

        final QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, bundle,
                    Contents.Type.CONTACT, BarcodeFormat.QR_CODE.toString(), qrCodeDimension, getSharedPreferences("BOOT_PREF", MODE_PRIVATE));

        if(contents != null) {
            qrDecoded.setText("QR Scan Result: " + contents);
        }
        else
            qrDecoded.setText("QR Scan Result: ");

        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            if(bitmap != null) {
                qrImage.setImageBitmap(bitmap);
            } else {
                qrDecoded.setText("INVALID");
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String capturedQrValue = intent.getStringExtra("SCAN_RESULT");
                intent.getStringExtra("SCAN_RESULT_FORMAT");
                qrDecoded.setText("QR Scan Result: " + capturedQrValue);
                contents = capturedQrValue;
                String[] items = contents.split(";");
                String[] items2 = items[0].split(":");
                getSharedPreferences("BOOT_PREF", MODE_PRIVATE).edit().putString("otherName", items2[2]).commit();
                items2 = items[1].split(":");
                getSharedPreferences("BOOT_PREF", MODE_PRIVATE).edit().putString("otherUserName", items2[1]).commit();
                getSharedPreferences("BOOT_PREF", MODE_PRIVATE).edit().putString("otherPhoneNumber", items[2]).commit();
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel

            }
        } else {

        }
    }
}
