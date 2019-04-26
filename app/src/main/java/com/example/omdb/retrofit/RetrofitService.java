package com.example.omdb.retrofit;

import android.util.Log;

import com.example.omdb.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static final String TAG = "RetrofitService";

    private static Retrofit getRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d(TAG, "getRetrofitInstance Retrofit: " + retrofit.baseUrl());
        return retrofit;
    }

    public static SearchApiEndpoint getSearchEndpoint() {
        SearchApiEndpoint searchApiEndpoint = getRetrofitInstance().create(SearchApiEndpoint.class);
       // Log.d(TAG, "getSearchEndpoint Search endpoint: " + searchApiEndpoint.toString());
        return searchApiEndpoint;
    }

    public static MovieDetailsEndPoint getMovieDetailsPoint() {
        MovieDetailsEndPoint movieDetailsEndPoint = getRetrofitInstance().create(MovieDetailsEndPoint.class);
        // Log.d(TAG, "getSearchEndpoint Search endpoint: " + searchApiEndpoint.toString());
        return movieDetailsEndPoint;
    }
}
