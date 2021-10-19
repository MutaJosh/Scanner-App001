package dev.mugisha.recdtsqrcodescanner.interfaces;

import java.util.Map;

import dev.mugisha.recdtsqrcodescanner.model.Driver;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RESTApiInterface {
    // as we are making a post request to post a data
    // so we are annotating it with post
    //on below line we are creating a method to post our data.

    @POST("traveler_check_endpoint")
    Call<ResponseBody> validatecode(@Body Map<String,String> obj);


}
