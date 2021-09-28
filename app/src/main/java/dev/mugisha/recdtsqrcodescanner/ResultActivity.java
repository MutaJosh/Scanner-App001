package dev.mugisha.recdtsqrcodescanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private Button btnscan;
    private String result;
    private TextView tvresults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("EACPass Scanner");
        tvresults=findViewById(R.id.tvresults);

        Intent intent=getIntent();
        result=intent.getStringExtra("result");


        tvresults.setText(result);
        btnscan=findViewById(R.id.btnscan);
        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this,ScannerUI.class));
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                finish();
            }
        });
    }
}