package com.fooxxiie.qrcodemanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrCodeShowText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_show_text);

        String textToTranslate = "Error";

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            textToTranslate = intent.getString("textQR");
        } else {
            Toast.makeText(this, "Travaille", Toast.LENGTH_SHORT).show();
        }

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(textToTranslate, BarcodeFormat.QR_CODE, 400, 400);
            ImageView imageViewQrCode = (ImageView) findViewById(R.id.imageView3);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch(Exception e) {
            Log.d("debugme", e.getMessage());
            Toast.makeText(this, "Une erreur est survenue lors de la création du QR CODE", Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_return, menu);
        return true;
    }

}
