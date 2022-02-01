package com.oliverjohnson.mediacollection.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.oliverjohnson.mediacollection.MediaQueryHandler;
import com.oliverjohnson.mediacollection.R;
import com.oliverjohnson.mediacollection.datastructures.Disc;
import com.oliverjohnson.mediacollection.datastructures.MediaObject;

import org.w3c.dom.Text;

import java.util.List;

public class TMDbMatchAdapter extends ArrayAdapter<MediaObject> {
    private Context adapterContext;
    private List<MediaObject> matchesList;
    private static int descriptionCharLimit = 300;

    // Constructor
    public TMDbMatchAdapter(@NonNull Context context, List<MediaObject> list) {
        super(context, 0, list);
        adapterContext = context;
        matchesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = convertView;

        // Inflate View
        if(view == null && matchesList.size() > 0)
            view = LayoutInflater.from(adapterContext).inflate(R.layout.tmdb_matches_layout, parent, false);
        else
            return view;

        // Get the media object that is the match from TMDb
        MediaObject match = matchesList.get(position);

        // Get the views to update in the layout
        TextView title = view.findViewById(R.id.tmdbMatchTitle);
        TextView description = view.findViewById(R.id.tmdbMatchDescription);
        ImageView poster = view.findViewById(R.id.tmdbMatchPoster);

        // Display information from match
        title.setText(match.getTitle());
        // If needed, reduce description so that it is within the char limit
        if(match.getDescription().length() >= descriptionCharLimit)
            description.setText(match.getDescription().substring(0, descriptionCharLimit-1) + "...");
        else
            description.setText(match.getDescription());
        MediaQueryHandler.getPoster(MediaQueryHandler.ImageSize.w500, poster, match.getPosterPath(), (Activity) getContext());

        return view;
    }

    @Override
    public int getViewTypeCount()
    {
        return getCount();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}
