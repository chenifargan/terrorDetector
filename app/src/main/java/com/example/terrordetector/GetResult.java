package com.example.terrordetector;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetResult {
   /* @GET("alerts")
    Call<List<Result>> getResult();
*/
    @GET("alerts")
    Call<List<Result>> getSpecificResult(@Query("keyword") String keyword,
                                          @Query("time") String time,@Query("date") String date
           ,@Query("location") String location,@Query("website") String website);

    @GET("alerts")
    Call<List<Result>> getSpecificResult1(@Query("filters") String ans,@Query("sortBy")String alertId,@Query("sortOrder") String ASC,@Query("page")int page,@Query("size") int size);



    //@GET("alerts")
    //Call<List<Result>> getResultbykey(@Path())


    //alerts
}
