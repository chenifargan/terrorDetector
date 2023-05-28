package com.example.terrordetector;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UpdateFeedback {
    @PUT("/alert/{alertID}")
    Call<Integer> updateFeedback(@Path("alertID") String alertID, @Body Result result);
}
