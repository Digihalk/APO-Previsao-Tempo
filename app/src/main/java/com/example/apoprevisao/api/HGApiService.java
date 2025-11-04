package com.example.apoprevisao.api;

import com.example.apoprevisao.model.PrevisaoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HGApiService {



    @GET("weather")
    Call<PrevisaoResponse> getPrevisaoPorWOEID(
            @Query("woeid") String woeid
    );

}