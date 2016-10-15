package com.codepath.flixster.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flixster.R;
import com.codepath.flixster.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by sdevired on 10/13/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie>{

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get data
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        Log.d(" MovieAdapter " + position , (convertView == null)+" ");
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.posterView = (ImageView)convertView.findViewById(R.id.ivPoster);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.posterView.setImageResource(0);

        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPoster()).into(viewHolder.posterView);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getPosterLand()).into(viewHolder.posterView);
        }
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        return convertView;
    }

    private class ViewHolder {
        ImageView posterView;
        TextView tvTitle;
        TextView tvOverview;
    }
}
