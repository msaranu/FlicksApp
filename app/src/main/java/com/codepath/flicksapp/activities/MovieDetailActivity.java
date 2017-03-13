package com.codepath.flicksapp.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flicksapp.R;
import com.codepath.flicksapp.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.squareup.picasso.Request;


public class MovieDetailActivity extends YouTubeBaseActivity {
    Movie movie;
    @BindView(R.id.tvMovieTitle) TextView tvTitle;
    @BindView(R.id.rbRating) RatingBar rbRating;
    @BindView(R.id.tvReleaseDate)TextView tvReleaseDate;
    TextView tvOverview;
    @BindView(R.id.ytYouTube)YouTubePlayerView youTubePlayerView;
    String movieKey;
    public static final String YT_API_KEY = "AIzaSyBzJxYSEc5CX83OJmTZb02f1QxfTLKtgpw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie = (Movie) getIntent().getSerializableExtra("movieObj");
        int screenOrientationHigh = this.getResources().getConfiguration().orientation;

        ButterKnife.bind(this);

        tvTitle.setText(movie.getOriginalTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        //Hide overview due to real estate limitation on Landscape.
        if (screenOrientationHigh == Configuration.ORIENTATION_PORTRAIT) {
            tvOverview = (TextView) findViewById(R.id.tvOverview);
            tvOverview.setText(movie.getOverview());
        }
        rbRating.setRating(movie.getPopularRating() / 2);

        String mvideoUrl = "https://api.themoviedb.org/3/movie/" + movie.getMovieId() + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(mvideoUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        JSONArray jArray = json.getJSONArray("results");
                        //get the first video available
                        movieKey = jArray.getJSONObject(0).getString("key");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    youTubePlayerView.initialize(YT_API_KEY,
                                            new YouTubePlayer.OnInitializedListener() {

                                                @Override
                                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                                    YouTubePlayer youTubePlayer, boolean b) {
                                                    // do any work here to cue video, play video, etc.
                                                    if (movieKey != null) {
                                                        youTubePlayer.cueVideo(movieKey);
                                                    }
                                                }

                                                @Override
                                                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                                    YouTubeInitializationResult youTubeInitializationResult) {

                                                }
                                            });


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (JSONException e) {
                    }
                }
            }
        });


        }
}
