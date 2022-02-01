package com.oliverjohnson.mediacollection.fragments;

import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliverjohnson.mediacollection.MediaQueryHandler;
import com.oliverjohnson.mediacollection.R;
import com.oliverjohnson.mediacollection.adapters.MovieDiscAdapter;
import com.oliverjohnson.mediacollection.datastructures.AvailableMovie;
import com.oliverjohnson.mediacollection.datastructures.Disc;
import com.oliverjohnson.mediacollection.datastructures.MediaObject;
import com.oliverjohnson.mediacollection.datastructures.MovieDisc;
import com.oliverjohnson.mediacollection.datastructures.MovieDiscCallback;

import java.util.ArrayList;

public class DiscInspectionFragment  extends Fragment
{
    public static String DISC_SERIALIZABLE_NAME = "DISC_TO_INSPECT";

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.disc_inspection_fragment, container, false);

        AvailableMovie movieToDisplay = (AvailableMovie) getActivity().getIntent().getSerializableExtra(DISC_SERIALIZABLE_NAME);

        TextView titleTextView = view.findViewById(R.id.disc_inspection_title_text);
        TextView taglineTextView = view.findViewById(R.id.disc_inspection_tagline_text);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        TextView runtimeTextView = view.findViewById(R.id.disc_inspection_runtime_text);
        TextView genresTextView = view.findViewById(R.id.genresTextView);

        ImageView posterImage = view.findViewById(R.id.disc_inspection_poster_image);
        posterImage.setImageResource(R.drawable.missing_poster);
        posterImage.getLayoutParams().height = 525;
        posterImage.getLayoutParams().width = 450;

        ImageView backdropImage = view.findViewById(R.id.disc_inspection_backdrop_image);
        backdropImage.setImageResource(R.drawable.missing_backdrop);

        MediaQueryHandler.getPoster(MediaQueryHandler.ImageSize.w780, posterImage, movieToDisplay.posterPath, (Activity) getContext());
        MediaQueryHandler.getPoster(MediaQueryHandler.ImageSize.w1280, backdropImage, movieToDisplay.backdropPath, (Activity) getContext());
        titleTextView.setText(movieToDisplay.title);
        taglineTextView.setText(movieToDisplay.tagline);
        descriptionTextView.setText(movieToDisplay.description);

        String genresString = "";
        for(int i=0; i < movieToDisplay.genres.length; i++)
        {
            genresString += movieToDisplay.genres[i];
            if(i != movieToDisplay.genres.length - 1)
                genresString +=  ", ";
        }
        genresTextView.setText(genresString);

        int runtimeHours = (int) Math.floor(movieToDisplay.runtime / 60);
        int minutes = movieToDisplay.runtime - (runtimeHours * 60);
        String runtime = runtimeHours + "h " + minutes + "m";
        runtimeTextView.setText("Runtime: " + runtime);

        float percentage = (movieToDisplay.averageVote / 10);
        int red = (int) (255 * (1 - percentage));
        int green = (int) (255 * percentage);

        ImageView voteBar = view.findViewById(R.id.disc_inspection_vote_bar);
        ImageViewCompat.setImageTintList(voteBar, ColorStateList.valueOf(Color.rgb(red, green, 0)));
        TextView averageVoteTextView = view.findViewById(R.id.disc_inspection_average_vote_text);
        averageVoteTextView.setText(String.valueOf(movieToDisplay.averageVote));

        // Get discs
        final ArrayList<MovieDisc> availableDiscs = new ArrayList<MovieDisc>();
        final MovieDiscAdapter adapter = new MovieDiscAdapter(availableDiscs);
        final RecyclerView discRV = view.findViewById(R.id.availableDiscsRecyclerView);
        MediaQueryHandler.getAllMovieDiscs(movieToDisplay.ID, getContext(), new MovieDiscCallback() {
            @Override
            public void OnSuccess(MovieDisc[] movieDiscs)
            {
                for(MovieDisc disc : movieDiscs)
                    availableDiscs.add(disc);
                discRV.setAdapter(adapter);
                discRV.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        return view;
    }
}
