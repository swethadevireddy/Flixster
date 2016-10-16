package com.codepath.flixster.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
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

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


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
        int type = getItemViewType(position);
        ViewHolder viewHolder;
        Log.d(" MovieAdapter " + position , (convertView == null)+" ");
        if(type == Movie.Category.NORMAL.ordinal()) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.posterView.setImageResource(0);
            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Picasso.with(getContext()).load(movie.getPoster()).transform(new RoundedCornersTransformation(10, 10))
                        .placeholder(viewHolder.placeholder)
                        .resize(0, 500)
                        .error(viewHolder.placeholderError).into(viewHolder.posterView);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Picasso.with(getContext()).load(movie.getPosterLand()).
                        transform(new RoundedCornersTransformation(10, 10))
                        .placeholder(viewHolder.placeholder)
                        .error(viewHolder.placeholderError).resize(500, 0).into(viewHolder.posterView);
            }
            viewHolder.tvTitle.setText(movie.getTitle());
            viewHolder.tvOverview.setText(movie.getOverview());
        }else{
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie_popular, parent, false);
                viewHolder = new ViewHolder(convertView);
                 convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.posterView.setImageResource(0);
            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Picasso.with(getContext()).load(movie.getPosterLand())
                        .transform(new RoundedCornersTransformation(10, 10))
                        .placeholder(viewHolder.placeholder)
                        .error(viewHolder.placeholderError).resize(0,500).into(viewHolder.posterView);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Picasso.with(getContext()).load(movie.getPosterLand())
                        .transform(new RoundedCornersTransformation(10, 10))
                        .placeholder(viewHolder.placeholder)
                        .error(viewHolder.placeholderError).resize(500,0).into(viewHolder.posterView);
            }
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getMovieCategory().ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Movie.Category.values().length;
    }


     class ViewHolder {
        @BindView(R.id.ivPoster) ImageView posterView;
        @Nullable @BindView(R.id.tvTitle) TextView tvTitle;
        @Nullable @BindView(R.id.tvOverview) TextView tvOverview;
        @BindDrawable(R.mipmap.placeholder) Drawable placeholder;
        @BindDrawable(R.mipmap.placeholder) Drawable placeholderError;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
