package com.example.omdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.omdb.model.Search;
import com.example.omdb.model.SearchResponse;
import com.example.omdb.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button searchBtn;
    private ListView listView;
    private ArrayAdapter<Search> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etSearch = findViewById(R.id.searchText);

        searchBtn = findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callService(etSearch.getText().toString().trim());
            }
        });

        listView = findViewById(R.id.list_view);
    }


    public void callService(String query) {
        Log.d(TAG, "callService: Query " + query);

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("apikey", Constants.API_KEY);
        queryParams.put("s", query);


        Call<SearchResponse> searchResponseCall = RetrofitService.getSearchEndpoint().searchOMDB(queryParams);

        //Log.d(TAG, "callService: searchResponse " + searchResponseCall.toString());

        searchResponseCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                if (searchResponse == null) {
                    Log.e(TAG, "onResponse: Search response is null");
                    return;
                }

                Log.d(TAG, "onResponse: " + searchResponse.getResponse() + " Total results " + searchResponse.getTotalResults()
                        + " Search " + searchResponse.getSearch().size());

                ArrayList<Search> searchResults = (ArrayList<Search>) searchResponse.getSearch();

                //TODO TEST
                for (Search result : searchResults) {
                    Log.d(TAG, "onResponse: Title " + result.getTitle() + " ID: " + result.getImdbID());
                }

                arrayAdapter = new ArrayAdapter<Search>(MainActivity.this, android.R.layout.simple_list_item_1, searchResults);
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Search searchItem = (Search) listView.getAdapter().getItem(position);
                        Log.d(TAG, "onItemClick: Title " + searchItem.getTitle() + " ID " + searchItem.getImdbID());

                        String movieID = searchItem.getImdbID();
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("id", movieID);
                        startActivity(intent);

                        //TODO Open Result activity, send title for response
                    }
                });

            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);


            }
        });
    }
}
