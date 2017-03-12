package com.codepath.flicksapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flicksapp.activities.MovieDetailActivity;
import com.codepath.flicksapp.activities.MoviePlayActivity;
import com.codepath.flicksapp.R;
import com.codepath.flicksapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flicksapp.R.id.ivMovieIcon;


/**
 * Created by Saranu on 3/7/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).popularRating >= 5){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    private static class ViewHolderLowRating {
        ImageView ivMovieIcon;
        TextView tvTitle;
        TextView tvOverview;
    }

    private static class ViewHolderHighRating { //Show only backdrop on potrait and title/overview with Landscape
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivMovieIcon;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        int viewType = this.getItemViewType(position);
        Movie movie = getItem(position);

        switch (viewType) {
            case 0: // Low Rating
                ViewHolderLowRating viewHolderLowRating = new ViewHolderLowRating();
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
                    viewHolderLowRating.ivMovieIcon = (ImageView) convertView.findViewById(ivMovieIcon);
                    viewHolderLowRating.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                    viewHolderLowRating.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
                    convertView.setTag(viewHolderLowRating);
                } else {
                    viewHolderLowRating = (ViewHolderLowRating) convertView.getTag();
                }

                viewHolderLowRating.ivMovieIcon.setImageResource(0);
                viewHolderLowRating.tvTitle.setText(movie.getOriginalTitle());
                viewHolderLowRating.tvOverview.setText(movie.getOverview());


                if (position == 1) {
                        movie.setPosterPath("www.imageurlthatdoesnotexist.com/blah");
                        movie.setBackdropPath("www.imageurlthatdoesnotexist.com/blah");;
                }
                int screenOrientation = getContext().getResources().getConfiguration().orientation;

                if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    Picasso.with(getContext())
                            .load(movie.getPosterPath()).transform(new RoundedCornersTransformation(10, 10))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.error)
                            .into(viewHolderLowRating.ivMovieIcon);

                } else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {

                    Picasso.with(getContext())
                            .load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(10, 10))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.error)
                            .into(viewHolderLowRating.ivMovieIcon);
                }

               viewHolderLowRating.ivMovieIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //    Toast.makeText(getContext(), "Image Clicked!",
                     //           Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                        intent.putExtra("movieObj",getItem(position));
                        v.getContext().startActivity(intent);
                    }


                });

                return convertView;

            case 1: //         High Rating

                int screenOrientationHigh = getContext().getResources().getConfiguration().orientation;

                ViewHolderHighRating viewHolderHighRating = new ViewHolderHighRating();
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie_high_rating, parent, false);
                    viewHolderHighRating.ivMovieIcon = (ImageView) convertView.findViewById(ivMovieIcon);
                    if (screenOrientationHigh == Configuration.ORIENTATION_LANDSCAPE) {
                        viewHolderHighRating.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                        viewHolderHighRating.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
                    }
                    convertView.setTag(viewHolderHighRating);
                } else {
                    viewHolderHighRating = (ViewHolderHighRating) convertView.getTag();
                }

                viewHolderHighRating.ivMovieIcon.setImageResource(0);

                if (screenOrientationHigh == Configuration.ORIENTATION_LANDSCAPE) {
                    viewHolderHighRating.tvTitle.setText(movie.getOriginalTitle());
                    viewHolderHighRating.tvOverview.setText(movie.getOverview());
                }

                if (position == 1) {
                    movie.setPosterPath("www.imageurlthatdoesnotexist.com/blah");
                    movie.setBackdropPath("www.imageurlthatdoesnotexist.com/blah");;
                }

                if (screenOrientationHigh == Configuration.ORIENTATION_PORTRAIT) {
                    Picasso.with(getContext())
                            .load(movie.getBackdropPath()).fit().transform(new RoundedCornersTransformation(10, 10))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.error)
                            .into(viewHolderHighRating.ivMovieIcon);

                } else if (screenOrientationHigh == Configuration.ORIENTATION_LANDSCAPE) {

                    Picasso.with(getContext())
                            .load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(10, 10))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.error)
                            .into(viewHolderHighRating.ivMovieIcon);
                }

                viewHolderHighRating.ivMovieIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //    Toast.makeText(getContext(), "Image Clicked!",
                        //           Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), MoviePlayActivity.class);
                        intent.putExtra("movieObj",getItem(position));
                        v.getContext().startActivity(intent);
                    }


                });


                return convertView;

            default:
                return null;
        }

    }

}