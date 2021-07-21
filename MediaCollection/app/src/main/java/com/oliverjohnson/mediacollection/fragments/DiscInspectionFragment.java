package com.oliverjohnson.mediacollection.fragments;

import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliverjohnson.mediacollection.MediaQueryHandler;
import com.oliverjohnson.mediacollection.R;
import com.oliverjohnson.mediacollection.datastructures.Disc;
import com.oliverjohnson.mediacollection.datastructures.MediaObject;

public class DiscInspectionFragment  extends Fragment
{
    public static String DISC_SERIALIZABLE_NAME = "DISC_TO_INSPECT";

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.disc_inspection_fragment, container, false);

        Disc discToDisplay = (Disc) getActivity().getIntent().getSerializableExtra(DISC_SERIALIZABLE_NAME);
        MediaObject mediaObject = discToDisplay.getMediaObject();

        TextView titleTextView = view.findViewById(R.id.disc_inspection_title_text);
        TextView taglineTextView = view.findViewById(R.id.disc_inspection_tagline_text);
        TextView runtimeTextView = view.findViewById(R.id.disc_inspection_runtime_text);
        ImageView posterImage = view.findViewById(R.id.disc_inspection_poster_image);
        ImageView backdropImage = view.findViewById(R.id.disc_inspection_backdrop_image);

        MediaQueryHandler.getPoster(posterImage, mediaObject.getPosterPath(), (Activity) getContext());
        MediaQueryHandler.getPoster(backdropImage, mediaObject.getBackdropPath(), (Activity) getContext());
        titleTextView.setText(discToDisplay.getTitle());
        taglineTextView.setText(mediaObject.getTagline());

        int runtimeHours = (int) Math.floor(mediaObject.getRuntime() / 60);
        int minutes = mediaObject.getRuntime() - (runtimeHours * 60);
        String runtime = runtimeHours + "h " + minutes + "m";
        runtimeTextView.setText("Runtime: " + runtime);

        float percentage = (mediaObject.getAverageVote() / 10);
        int red = (int) (255 * (1 - percentage));
        int green = (int) (255 * percentage);

        ImageView voteBar = view.findViewById(R.id.disc_inspection_vote_bar);
        ImageViewCompat.setImageTintList(voteBar, ColorStateList.valueOf(Color.rgb(red, green, 0)));
        TextView averageVoteTextView = view.findViewById(R.id.disc_inspection_average_vote_text);
        averageVoteTextView.setText(String.valueOf(mediaObject.getAverageVote()));
        return view;
    }
}
