package dev.mugisha.recdtsqrcodescanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import dev.mugisha.recdtsqrcodescanner.interfaces.RESTApiInterface;
import dev.mugisha.recdtsqrcodescanner.model.Driver;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ScannerUI extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private String id,names,code;
    static final Integer CAMERA = 0x1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_ui);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("EACPass Scanner ");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
        askForPermission(Manifest.permission.CAMERA, CAMERA);
    }

    private void askForPermission(String permission, Integer requestCode) {

        if (ContextCompat.checkSelfPermission(ScannerUI.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ScannerUI.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(ScannerUI.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(ScannerUI.this, new String[]{permission}, requestCode);
            }
        } else {

            // Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                //Camera
                case 1:
                    mScannerView.setResultHandler(this);
                    mScannerView.startCamera();
                    break;


            }

            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {


        String u[] = result.getText().split(";", 100);
        String test = result.getText().substring(0, 3);
        if (test.equals("EAC")) {

            id = u[4];
            names = u[3];
            code = u[1].substring(11, 46);

            //Toast.makeText(getBaseContext(), "data is\n" + test, Toast.LENGTH_LONG).show();


            // * Wait 1 seconds to resume the preview.
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //    mScannerView.resumeCameraPreview(ScannerUI.this);

                    Intent i = new Intent(ScannerUI.this, CodeContentActivity.class);
                    i.putExtra("code", code);
                    i.putExtra("names", names);
                    i.putExtra("nid", id);
                    startActivity(i);
                    //finish();
                }
            }, 1000);

        } else{

            // * Wait 1 seconds to resume the preview.
            Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //    mScannerView.resumeCameraPreview(ScannerUI.this);

                    startActivity(new Intent(ScannerUI.this,ScannerUI.class));
                    finish();
                }
            }, 1000);
        Toast.makeText(ScannerUI.this, "Invalid QR code,Please scanner the real code", Toast.LENGTH_LONG).show();
    }


    }

}