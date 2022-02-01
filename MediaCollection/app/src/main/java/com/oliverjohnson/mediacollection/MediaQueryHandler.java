package com.oliverjohnson.mediacollection;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.oliverjohnson.mediacollection.datastructures.AvailableMovie;
import com.oliverjohnson.mediacollection.datastructures.AvailableMediaCallback;
import com.oliverjohnson.mediacollection.datastructures.AvailableShow;
import com.oliverjohnson.mediacollection.datastructures.Chunk;
import com.oliverjohnson.mediacollection.datastructures.MediaObject;
import com.oliverjohnson.mediacollection.datastructures.MovieDisc;
import com.oliverjohnson.mediacollection.datastructures.MovieDiscCallback;
import com.oliverjohnson.mediacollection.datastructures.VoidCallback;
import com.oliverjohnson.mediacollection.datastructures.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MediaQueryHandler
{
    private static String API_KEY = "a26fc33039b0fcce51d84c6004a85aa4";
    public final static int REQUEST_LIMIT = 10;

    public enum ImageSize
    {
        w500,
        w780,
        w1280,
        original
    }

    public static void getPoster(final ImageSize size, final ImageView imageView, final String posterPath, final Activity activity)
    {
        Thread getPosterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                try
                {
                    inputStream = (InputStream) new URL("https://image.tmdb.org/t/p/" + size.toString() + "/" + posterPath).getContent();
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

    public static void getNumberOfAvailableMovieChunks(Context context, final Chunk chunk, final VoidCallback callback)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Get Movies from DB
        String url = "http://192.168.0.4:5077/api/AvailableMovies/get/all/chunks/"+REQUEST_LIMIT;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    chunk.numberOfChunks = response.getInt("numberOfChunks");
                    chunk.totalElements = response.getInt("totalElements");
                    chunk.chunkSize = response.getInt("chunkSize");
                    callback.OnSuccess();
                }
                catch (Exception e){}
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(request);
    }

    public static void getNumberOfAvailableShowsChunks(Context context, final Chunk chunk, final VoidCallback callback)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Get Movies from DB
        String url = "http://192.168.0.4:5077/api/AvailableShows/get/all/chunks/"+REQUEST_LIMIT;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    chunk.numberOfChunks = response.getInt("numberOfChunks");
                    chunk.totalElements = response.getInt("totalElements");
                    chunk.chunkSize = response.getInt("chunkSize");
                    callback.OnSuccess();
                }
                catch (Exception e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    public static void getAllAvailableMovies(Context context, int offset, final AvailableMediaCallback callback)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Get Movies from DB
        String url = "http://192.168.0.4:5077/api/AvailableMovies/get/all/" + REQUEST_LIMIT + "/"+offset;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            AvailableMovie[] movies = new AvailableMovie[response.length()];
                            for(int x=0; x < response.length(); x++)
                            {
                                JSONObject jsonObj = response.getJSONObject(x);
                                AvailableMovie movie = new AvailableMovie();
                                movie.ID = jsonObj.getInt("id");
                                movie.title = jsonObj.getString("title");
                                movie.tmdbID = jsonObj.getInt("tmDbID");
                                movie.plexAvailabilityID = jsonObj.getInt("plexAvailabilityID");
                                movie.plexAvailabilityName = jsonObj.getString("plexAvailabilityName");
                                movie.posterPath = jsonObj.getString("posterPath");
                                movie.backdropPath = jsonObj.getString("backdropPath");

                                JSONArray jsonGenres = jsonObj.getJSONArray("genres");
                                movie.genres = new String[jsonGenres.length()];
                                for(int i=0; i < jsonGenres.length(); i++)
                                    movie.genres[i] = (String) jsonGenres.get(i);

                                movie.description = jsonObj.getString("overview");
                                movie.releaseDate = jsonObj.getString("releaseDate");
                                movie.tagline = jsonObj.getString("tagline");
                                movie.runtime = jsonObj.getInt("runtime");
                                movie.averageVote = (float) jsonObj.getDouble("voteAverage");
                                movie.voteCount = jsonObj.getInt("voteCount");
                                movie.budget = jsonObj.getLong("budget");
                                movies[x] = movie;
                            }
                            callback.OnSuccess(movies);
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

    public static void getAllAvailableShows(Context context, int offset, final AvailableMediaCallback callback)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Get Movies from DB
        String url = "http://192.168.0.4:5077/api/AvailableShows/get/all/" + REQUEST_LIMIT + "/"+offset;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            AvailableShow[] shows = new AvailableShow[response.length()];
                            for(int x=0; x < response.length(); x++)
                            {
                                JSONObject jsonObj = response.getJSONObject(x);
                                AvailableShow show = new AvailableShow();
                                show.ID = jsonObj.getInt("id");
                                show.title = jsonObj.getString("title");
                                show.tmdbID = jsonObj.getInt("tmDbID");
                                show.posterPath = jsonObj.getString("posterPath");
                                show.backdropPath = jsonObj.getString("backdropPath");
                                show.averageVote = (float) jsonObj.getDouble("voteAverage");
                                show.voteCount = jsonObj.getInt("voteCount");
                                show.firstAirDate = jsonObj.getString("firstAirDate");
                                show.lastAirDate = jsonObj.getString("lastAirDate");
                                show.numberOfEpisodes = jsonObj.getInt("numberOfEpisodes");
                                show.numberOfSeasons = jsonObj.getInt("numberOfSeasons");

                                JSONArray jsonGenres = jsonObj.getJSONArray("genres");
                                show.genres = new String[jsonGenres.length()];
                                for(int i=0; i < jsonGenres.length(); i++)
                                    show.genres[i] = (String) jsonGenres.get(i);

                                shows[x] = show;
                            }
                            callback.OnSuccess(shows);
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
    public static void getAllMovieDiscs(int movieID, Context context, final MovieDiscCallback callback)
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Get Movies from DB
        String url = "http://192.168.0.4:5077/api/Discs/get/movie/" + movieID;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {

                            MovieDisc[] discs = new MovieDisc[response.length()];
                            for(int x=0; x < response.length(); x++)
                            {
                                JSONObject jsonObj = response.getJSONObject(x);
                                MovieDisc disc = new MovieDisc();
                                disc.id = jsonObj.getInt("id");
                                disc.title = jsonObj.getString("title");
                                disc.boxID = jsonObj.getInt("boxID");
                                disc.boxName = jsonObj.getString("boxName");
                                disc.boxIndex = jsonObj.getInt("boxIndex");
                                disc.formatID = jsonObj.getInt("formatID");
                                disc.formatName = jsonObj.getString("formatName");
                                discs[x] = disc;
                            }
                            callback.OnSuccess(discs);
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
