package com.example.omdb.retrofit;

import com.example.omdb.model.IdResponse;
import com.example.omdb.model.SearchResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SearchApiEndpoint {

    //http://www.omdbapi.com/?apikey=[yourkey]&s=Batman
  //  @GET("/")
    //Call<OMDBResponse> searchOMDB(@QueryMap Map<String, String> options);

    @GET("/")
    Call<SearchResponse> searchOMDB(@QueryMap Map<String, String> query);


}
