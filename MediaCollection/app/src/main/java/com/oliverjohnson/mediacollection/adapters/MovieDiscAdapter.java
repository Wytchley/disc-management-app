package com.oliverjohnson.mediacollection.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.oliverjohnson.mediacollection.MediaQueryHandler;
import com.oliverjohnson.mediacollection.R;
import com.oliverjohnson.mediacollection.datastructures.AvailableMovie;
import com.oliverjohnson.mediacollection.datastructures.MovieDisc;

import java.util.ArrayList;

public class MovieDiscAdapter extends RecyclerView.Adapter<MovieDiscAdapter.ViewHolder>
{
    private ArrayList<MovieDisc> discList;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView discTitleText;
        public TextView boxText;
        public TextView indexText;
        public TextView formatText;
        public ImageView discFormatImage;
        public View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            discTitleText = itemView.findViewById(R.id.discTitle);
            boxText = itemView.findViewById(R.id.boxText);
            indexText = itemView.findViewById(R.id.indexText);
            formatText = itemView.findViewById(R.id.formatText);
            discFormatImage = itemView.findViewById(R.id.discFormatImage);
        }
    }

    public MovieDiscAdapter(ArrayList<MovieDisc> givenDiscs)
    {
        discList = givenDiscs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disc_layout, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder, int position) {
        final MovieDisc disc = discList.get(position);

        holder.discTitleText.setText(disc.title);
        holder.formatText.setText("Format: " + disc.formatName);
        holder.boxText.setText("Box: " + disc.boxName);
        holder.indexText.setText("Index: " + disc.boxIndex);
        holder.discFormatImage.setImageResource(disc.getFormatImageResourceID());
        holder.discFormatImage.getLayoutParams().height = 300;
        holder.discFormatImage.getLayoutParams().width = 300;
    }

    @Override
    public int getItemCount() {
        return discList.size();
    }
}
