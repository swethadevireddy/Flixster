package com.codepath.flixster.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sdevired on 10/13/16.
 */
public class Movie {
    String title;
    String poster;
    String overview;

    public String getPosterLand() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterLand);
    }

    public void setPosterLand(String posterLand) {
        this.posterLand = posterLand;
    }

    String posterLand;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return String.format("https://image.tmdb.org/t/p/w342%s", poster);
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Movie(JSONObject o) throws JSONException{
        this.poster = o.get("poster_path").toString();
        this.overview = o.get("overview").toString();
        this.title = o.get("title").toString();
        this.posterLand = o.get("backdrop_path").toString();
    }

    public static ArrayList<Movie> fromJsonArray(JSONArray jsonArray) throws JSONException {
      ArrayList<Movie> moveList = new ArrayList<Movie>();
      for (int i = 0; i < jsonArray.length(); i++) {
          moveList.add(new Movie(jsonArray.getJSONObject(i)));
      }
      return moveList;
    }
}
