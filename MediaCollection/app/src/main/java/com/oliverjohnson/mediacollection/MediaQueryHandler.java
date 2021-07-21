package com.oliverjohnson.mediacollection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.oliverjohnson.mediacollection.datastructures.MediaObject;
import com.oliverjohnson.mediacollection.datastructures.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MediaQueryHandler
{
    private static String API_KEY = "a26fc33039b0fcce51d84c6004a85aa4";

    public static void getPoster(final ImageView imageView, final String posterPath, final Activity activity)
    {
        Thread getPosterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                try
                {
                    inputStream = (InputStream) new URL("https://image.tmdb.org/t/p/w500" + posterPath).getContent();
                    final Drawable drawable = Drawable.createFromStream(inputStream, "Poster");
                    activity.runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            imageView.setImageDrawable(drawable);
                        }
                    });
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        getPosterThread.start();
    }

    public static void getAllInfo(final MediaObject mediaObject, Context context, final VolleyCallback callback)
    {
        String url = "https://api.themoviedb.org/3/movie/" + mediaObject.getMovieID() + "?api_key=" + API_KEY;
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            mediaObject.setTagline(response.getString("tagline"));
                            mediaObject.setOriginalLanguage(response.getString("original_language"));
                            mediaObject.setDescription(response.getString("overview"));
                            mediaObject.setReleaseDate(response.getString("release_date"));
                            mediaObject.setRuntime(response.getInt("runtime"));
                            mediaObject.setAverageVote((float) response.getDouble("vote_average"));
                            mediaObject.setVoteCount(response.getInt("vote_count"));
                            mediaObject.setBudget(response.getInt("budget"));
                            callback.OnSuccess(null);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(request);


    }

    public static void search(final String searchTitle, Context context, final VolleyCallback callback) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY
                      + "&language=en-UK&query=" + searchTitle.replace(" ", "%20") + "&page=1&include_adult=false";
        final MediaObject mediaObject = new MediaObject();
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray results = response.getJSONArray("results");
                            MediaObject[] mediaObjects = new MediaObject[results.length()];
                            for(int x=0; x < results.length(); x++)
                            {
                                MediaObject media = new MediaObject();
                                responseToMediaObject(media, results.getJSONObject(x));
                                mediaObjects[x] = media;
                            }
                            callback.OnSuccess(mediaObjects);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(request);
    }

    private static void responseToMediaObject(final MediaObject mediaObject, JSONObject json)
    {
        try
        {
            mediaObject.setTitle(json.getString("original_title"));
            mediaObject.setMovieID(Integer.parseInt(json.getString("id")));
            mediaObject.setPosterPath(json.getString("poster_path"));
            mediaObject.setBackdropPath(json.getString("backdrop_path"));
            mediaObject.setDescription(json.getString("overview"));
        }
        catch(org.json.JSONException exception)
        {
            exception.printStackTrace();
        }
    }
}
