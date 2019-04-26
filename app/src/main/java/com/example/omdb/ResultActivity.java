package com.example.omdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.omdb.model.IdResponse;
import com.example.omdb.model.SearchResponse;
import com.example.omdb.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";
    private ListView listView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listView = findViewById(R.id.list_view_result);

        imageView = findViewById(R.id.image);


        //TODO RESPONSE BY TITLE
        //http://www.omdbapi.com/?apikey=1fa0f79c&i=tt2442560

        Intent intent = getIntent();
        String movieId = intent.getExtras().getString("id");
        Log.d(TAG, "onCreate: Movie ID: " + movieId);

        callService(movieId);

    }

    private void callService(String movieId) {
        Log.d(TAG, "callService: Query ID  " + movieId);

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("apikey", Constants.API_KEY);
        queryParams.put("i", movieId);

        Log.d(TAG, "callService Query params: " + queryParams.get("apikey") +
                " : " + queryParams.get("i"));

        Call<IdResponse> idResponseCall = RetrofitService.getMovieDetailsPoint().getMovieDetails(queryParams);

        idResponseCall.enqueue(new Callback<IdResponse>() {
            @Override
            public void onResponse(Call<IdResponse> call, Response<IdResponse> response) {

                IdResponse idResponse = response.body();

                Log.d(TAG, "onResponse: RESPONSE " + idResponse.getActors() + " " + idResponse.getImdbRating());

                Picasso.with(getApplicationContext()).load(idResponse.getPoster())
                        .resize(1000, 1000).into(imageView);

            }

            @Override
            public void onFailure(Call<IdResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t);
            }
        });


    }

}
