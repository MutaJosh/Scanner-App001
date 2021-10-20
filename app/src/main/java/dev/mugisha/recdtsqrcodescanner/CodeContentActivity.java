package dev.mugisha.recdtsqrcodescanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dev.mugisha.recdtsqrcodescanner.model.Driver;
import dev.mugisha.recdtsqrcodescanner.model.Traveller;
import dev.mugisha.recdtsqrcodescanner.singleton.RESTApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeContentActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnrescan, btnproceed;
    private TextView tvcode, tvnid, tvnames;

    private String jsonresponse;

    private ProgressDialog progressDialog;
    private String code, nid, names,message;
    private Driver modal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_content);


        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("EACPass Scanner");

        Intent i = getIntent();
        code = i.getStringExtra("code");
        names = i.getStringExtra("names");
        nid = i.getStringExtra("nid");

        progressDialog = new ProgressDialog(CodeContentActivity.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCanceledOnTouchOutside(false);

        tvnid = findViewById(R.id.tvnid);
        tvcode = findViewById(R.id.tvcode);

        tvnames = findViewById(R.id.tvnames);

        btnproceed = findViewById(R.id.btnproceed);

        btnrescan = findViewById(R.id.btnrescan);
        btnrescan.setOnClickListener(this);


        tvnames.setText(names);
        tvcode.setText(code);
        tvnid.setText(nid);

        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callsendcodeapi();

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnrescan:

                startActivity(new Intent(CodeContentActivity.this, ScannerUI.class));
                finish();
                break;


        }
    }


    // create API
    public void callsendcodeapi() {

        progressDialog.show();
        // create user body object
        Traveller obj = new Traveller();
        obj.setCode(code);
        obj.setNID(nid);

        Map<String, String> param = new HashMap<>();
        param.put("code", code);
        param.put("NID", nid);
        out(param);

    }


    private void out(final Map<String, String> user) {
        Call<ResponseBody> request = RESTApiClient.getInstance().getApi().validatecode(user);

        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

              //  Toast.makeText(getBaseContext(), "igisubizo"+response.code() +"\n"+response.message(), Toast.LENGTH_LONG).show();
                //Response
                if (Integer.toString(response.code()).equals("200")) {
                    progressDialog.dismiss();
                    try {
                        String jsondata = response.body().string();

                        if (jsondata != null) {

                            progressDialog.dismiss();
                            JSONObject reader = new JSONObject(jsondata);

                            message=reader.getString("message");
                          //  Toast.makeText(getBaseContext(), "Message is "+message, Toast.LENGTH_SHORT).show();

                            Intent intentio=new Intent(CodeContentActivity.this,ResultActivity.class);
                            intentio.putExtra("result",message);
                            startActivity(intentio);
                            CodeContentActivity.this.finish();

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getBaseContext(), "response error"+response.message(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Something went wrong. Please, contact the system administrators.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //failure
                progressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Error"+t.getLocalizedMessage().toLowerCase(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}