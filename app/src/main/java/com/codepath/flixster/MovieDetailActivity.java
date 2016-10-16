package com.codepath.flixster;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieDetailActivity extends YouTubeBaseActivity {

    String videoApiUrl;
    String videoKey;
    AsyncHttpClient client;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvPopularity) TextView tvPopularity;
    @BindView(R.id.rbMovie) RatingBar rb;
    @BindView(R.id.player) YouTubePlayerView youTubePlayerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        String overview = getIntent().getStringExtra("overview");
        Double rating = getIntent().getDoubleExtra("rating", 0.0);
        Double popularity = getIntent().getDoubleExtra("popularity", 0);
        Long id = getIntent().getLongExtra("id", 0);

        tvOverview.setText(overview);
        tvTitle.setText(title);

        client = new AsyncHttpClient();
       /* posterView.setImageResource(0);
        Picasso.with(this).load(poster)
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder_error).resize(0,400).into(posterView);*/

        if(popularity != 0) {
            tvPopularity.setText(String.format("%.2f", (popularity * 100) / 40) +"%");
        }else{
            tvPopularity.setText(" ");
        }
        rb.setRating(rating.floatValue()/2);

        videoApiUrl = String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", id);

        getVideos();

    }

    private void getVideos(){

        client.get(videoApiUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");
                    if(array != null &&  array.length() > 1){
                        videoKey = ((JSONObject)array.get(0)).getString("key");
                        playVideo();
                    }
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

    private void playVideo(){
        youTubePlayerView.initialize("AIzaSyB-0u5_f4v645Pf0ECYNJpyQ0BVEPm_KGM",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer.cueVideo(videoKey);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

    }
}
