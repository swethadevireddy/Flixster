package com.codepath.flixster;

import android.os.Bundle;

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

public class VideoPlayActivity extends YouTubeBaseActivity {
    AsyncHttpClient client;
    String videoApiUrl;
    String videoKey;
    @BindView(R.id.player) YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        client = new AsyncHttpClient();
        Long id = getIntent().getLongExtra("id", 0);
        videoApiUrl = String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", id);

        getVideos();


    }

    private void getVideos(){
        client.get(videoApiUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");

                    if(array != null &&  array.length() > 0){
                       for(int i=0 ; i < array.length() ; i++) {
                           videoKey = ((JSONObject) array.get(i)).getString("key");
                           if(videoKey != null) {
                               break;
                           }
                       }
                       playVideo();
                    }
                   // Log.d("VideoPlayActivity : videos", movies.toString());
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

                        youTubePlayer.loadVideo(videoKey);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

    }
}
