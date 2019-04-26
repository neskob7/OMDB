package com.example.omdb.retrofit;

import com.example.omdb.model.IdResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MovieDetailsEndPoint {

    @GET("/")
    Call<IdResponse> getMovieDetails(@QueryMap Map<String, String> queryParams);

//    http://www.omdbapi.com/?apikey=1fa0f79c&i=tt0106062
    //Map: Key, Value
//    Key:"apikey"
//    Value:"1fa0f79c"

    //Key:"i"
    //Value:"tt0106062"


}
