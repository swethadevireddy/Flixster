package com.codepath.flixster.service;

import com.android.volley.RequestQueue;
import com.codepath.flixster.application.Flixster;

public class Volley {
    private static Volley mInstance = null;
    private RequestQueue mRequestQueue;
    private Volley(){
        mRequestQueue = com.android.volley.toolbox.Volley.newRequestQueue(Flixster.getAppContext());

    }
    public static Volley getInstance(){
        if(mInstance == null){
            mInstance = new Volley();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

}