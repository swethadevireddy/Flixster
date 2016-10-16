package com.codepath.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.flixster.adapter.MovieAdapter;
import com.codepath.flixster.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import cz.msebera.android.httpclient.Header;


public class MovieActivity extends AppCompatActivity {
    @BindView(R.id.lvMovies) ListView lvMovies;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    ArrayList<Movie> movies;
    AsyncHttpClient client;
    String movieApiUrl;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        movies = new ArrayList<Movie>();
        movieAdapter = new MovieAdapter(this, movies);
        lvMovies.setAdapter(movieAdapter);
        movieApiUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        client = new AsyncHttpClient();
        getMovieList();
        setUpSwipeRefreshListener();
    }


    private void getMovieList(){

        client.get(movieApiUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    movieAdapter.clear();
                    movies.addAll(Movie.fromJsonArray(response.getJSONArray("results")));
                    Log.d("MovieActivity : movies", movies.toString());
                    //movieAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

        });

    }

    private void setUpSwipeRefreshListener(){
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovieList();
            }

        });
    }


    @OnItemClick(R.id.lvMovies)
    void loadMovieDetailActivity(int position){
        Movie m = movies.get(position);
        Intent i;
        if(m.getMovieCategory().ordinal() == Movie.Category.POPULAR.ordinal()){
            i = new Intent(MovieActivity.this, VideoPlayActivity.class);
            i.putExtra("id", m.getId());
        }else {
            i = new Intent(MovieActivity.this, MovieDetailActivity.class);
            i.putExtra("title", m.getTitle());
            i.putExtra("overview", m.getOverview());
            i.putExtra("rating", m.getRating());
            i.putExtra("popularity", m.getPopularity());
            i.putExtra("poster", m.getPoster());
            i.putExtra("id", m.getId());
        }
        startActivity(i);
    }
}
