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

import java.util.List;

public class DiscAdapter extends ArrayAdapter<Disc> {
    private Context adapterContext;
    private List<Disc> discsList;

    // Constructor
    public DiscAdapter(@NonNull Context context, List<Disc> list) {
        super(context, 0, list);
        adapterContext = context;
        discsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = convertView;
        // Inflate View
        if(view == null && discsList.size() > 0)
            view = LayoutInflater.from(adapterContext).inflate(R.layout.discs_layout, parent,false);
        else
            return view;

        final Disc disc = discsList.get(position);

        TextView discTitleText = view.findViewById(R.id.discTitle);
        TextView indexNumberText = view.findViewById(R.id.discIndexText);
        ImageView discPoster = view.findViewById(R.id.discPosterImage);

        discTitleText.setText(disc.getTitle());
        MediaQueryHandler.getPoster(discPoster, disc.getMediaObject().getPosterPath(), (Activity) getContext());

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
