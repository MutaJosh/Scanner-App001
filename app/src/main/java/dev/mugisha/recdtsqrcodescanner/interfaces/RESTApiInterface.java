package dev.mugisha.recdtsqrcodescanner.interfaces;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RESTApiInterface {

    // Traveler endpoint
    @POST("traveler_check_endpoint")
    Call<ResponseBody> senddata(@Body Map<String, String> obj);





}
