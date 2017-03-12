package com.codepath.flicksapp.activities;

import android.os.Bundle;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.codepath.flicksapp.activities.MovieDetailActivity.YT_API_KEY;

public class MoviePlayActivity extends YouTubeBaseActivity {
    Movie movie;
    String movieKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);
        movie = (Movie) getIntent().getSerializableExtra("movieObj");
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

                                    YouTubePlayerView youTubePlayerView =
                                            (YouTubePlayerView) findViewById(R.id.ytYouTubeDirect);

                                    youTubePlayerView.initialize(YT_API_KEY,
                                            new YouTubePlayer.OnInitializedListener() {

                                                @Override
                                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                                    YouTubePlayer youTubePlayer, boolean b) {
                                                    // do any work here to cue video, play video, etc.
                                                    if (movieKey != null) {
                                                        youTubePlayer.setFullscreen(true);
                                                        youTubePlayer.loadVideo(movieKey);
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
