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
import dev.mugisha.recdtsqrcodescanner.singleton.RESTApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeContentActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnrescan,btnproceed;
    private TextView tvcode,tvnid,tvnames;

    private ProgressDialog progressDialog;
    private String code,nid,names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_content);


        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("EACPass Scanner");

        Intent i=getIntent();
        code=i.getStringExtra("code");
        names=i.getStringExtra("names");
        nid=i.getStringExtra("nid");

        progressDialog=new ProgressDialog(CodeContentActivity.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCanceledOnTouchOutside(false);

        tvnid=findViewById(R.id.tvnid);
        tvcode=findViewById(R.id.tvcode);

        tvnames=findViewById(R.id.tvnames);

        btnproceed=findViewById(R.id.btnproceed);
        btnproceed.setOnClickListener(this);
        btnrescan=findViewById(R.id.btnrescan);
        btnrescan.setOnClickListener(this);

    tvnames.setText(names);
    tvcode.setText(code);
    tvnid.setText(nid);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnrescan:

                startActivity(new Intent(CodeContentActivity.this,ScannerUI.class));
                finish();
                break;

            case R.id.btnproceed:
callsendapi();
                break;
        }
    }

    // create API
    public void callsendapi() {

        progressDialog.show();
        // create user body object
        Driver obj = new Driver();
        obj.setNID(nid);
        obj.setCode(code);

        Map<String, String> param = new HashMap<>();
        param.put("NID", nid);
        param.put("code", code);

        in(param);
    }

    private void in(final Map<String, String> user) {
        Call<ResponseBody> request = RESTApiClient.getInstance().getApi().senddata(user);

        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Response


                try {
                    String jsondata = response.body().string();

                    if (jsondata != null) {
                        JSONObject reader = new JSONObject(jsondata);
                        String message = reader.getString("message");


                        Intent ii=new Intent(CodeContentActivity.this,ResultActivity.class);
                ii.putExtra("result",message);
startActivity(ii);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(CodeContentActivity.this, "An error occured", Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                //failure
                Toast.makeText(CodeContentActivity.this, "Something went wrong. Error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}