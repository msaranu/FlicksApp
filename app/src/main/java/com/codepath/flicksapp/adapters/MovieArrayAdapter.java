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

import com.codepath.flicksapp.R;
import com.codepath.flicksapp.activities.MovieDetailActivity;
import com.codepath.flicksapp.activities.MoviePlayActivity;
import com.codepath.flicksapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flicksapp.R.id.ivMovieIcon;


/**
 * Created by Saranu on 3/7/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public static int POPULAR_RATING = 5;
    public static int POPULAR_RATING_YES = 1;
    public static int POPULAR_RATING_NO = 0;
    public static int NUMBER_VIEWS = 2;
    public static String NON_EXISTENT_URL ="www.imageurlthatdoesnotexist.com/blah";


    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).popularRating >= POPULAR_RATING){
            return POPULAR_RATING_YES;
        } else {
            return POPULAR_RATING_NO;
        }
    }

    @Override
    public int getViewTypeCount() {
        return NUMBER_VIEWS;
    }


    private static class ViewHolderLowRating {
        ImageView ivMovieIcon;
        TextView tvTitle;
        TextView tvOverview;
        TextView tvRatingMain;
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
                int screenOrientationLow = getContext().getResources().getConfiguration().orientation;

                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
                    viewHolderLowRating.ivMovieIcon = (ImageView) convertView.findViewById(ivMovieIcon);
                    viewHolderLowRating.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                    viewHolderLowRating.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
                    if (screenOrientationLow == Configuration.ORIENTATION_PORTRAIT) {
                        viewHolderLowRating.tvRatingMain = (TextView) convertView.findViewById(R.id.tvRatingMain);
                    }
                    convertView.setTag(viewHolderLowRating);
                } else {
                    viewHolderLowRating = (ViewHolderLowRating) convertView.getTag();
                }

                viewHolderLowRating.ivMovieIcon.setImageResource(0);
                viewHolderLowRating.tvTitle.setText(movie.getOriginalTitle());
                viewHolderLowRating.tvOverview.setText(movie.getOverview());
                if (screenOrientationLow == Configuration.ORIENTATION_PORTRAIT) {
                    viewHolderLowRating.tvRatingMain.setText(Float.toString(movie.getPopularRating()));
                }

                if (position == 6) { //Show error image for the view in position 6
                    movie.setPosterPath(NON_EXISTENT_URL);
                    movie.setBackdropPath(NON_EXISTENT_URL);;
                }

                //check for screen orientation and display the views accordingly.

                if (screenOrientationLow == Configuration.ORIENTATION_PORTRAIT) {
                    Picasso.with(getContext())
                            .load(movie.getPosterPath()).transform(new RoundedCornersTransformation(10, 10))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.error)
                            .into(viewHolderLowRating.ivMovieIcon);

                } else if (screenOrientationLow == Configuration.ORIENTATION_LANDSCAPE) {

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
                    viewHolderHighRating.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

                    if (screenOrientationHigh == Configuration.ORIENTATION_LANDSCAPE) {
                        viewHolderHighRating.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
                    }
                    convertView.setTag(viewHolderHighRating);
                } else {
                    viewHolderHighRating = (ViewHolderHighRating) convertView.getTag();
                }

                viewHolderHighRating.ivMovieIcon.setImageResource(0);
                viewHolderHighRating.tvTitle.setText(movie.getOriginalTitle());

                //check for screen orientation and display the views accordingly.
                if (screenOrientationHigh == Configuration.ORIENTATION_LANDSCAPE) {
                    viewHolderHighRating.tvOverview.setText(movie.getOverview());
                }

                if (position == 6) { //Show error image for the view in position 6
                    movie.setPosterPath(NON_EXISTENT_URL);
                    movie.setBackdropPath(NON_EXISTENT_URL);;
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