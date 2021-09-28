package dev.mugisha.recdtsqrcodescanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private Button btnopen_qrcode;

    private static final int TIME_INTERVAL = 3000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnopen_qrcode=findViewById(R.id.btnopen_qrcode);
        btnopen_qrcode.setOnClickListener(this);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("EACPass Scanner");
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnopen_qrcode:
               startActivity(new Intent(MainActivity.this,ScannerUI.class));
               overridePendingTransition(R.anim.fadein,R.anim.fadeout);

                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;

        } else {
            Toast.makeText(getBaseContext(), "press back again to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }



}