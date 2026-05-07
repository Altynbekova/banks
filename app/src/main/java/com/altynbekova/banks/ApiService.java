package com.altynbekova.banks;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Authorization: Token 3a2aa6f034bd5618320ec208aa57608dfb1b0178")
    @POST("/suggestions/api/4_1/rs/suggest/bank")
    Call<ResponseBody> suggestions(@Body RequestBody request);
}
