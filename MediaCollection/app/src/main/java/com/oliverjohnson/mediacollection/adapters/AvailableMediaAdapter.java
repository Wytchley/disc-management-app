package com.oliverjohnson.mediacollection.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.oliverjohnson.mediacollection.MediaQueryHandler;
import com.oliverjohnson.mediacollection.R;
import com.oliverjohnson.mediacollection.datastructures.AvailableMovie;
import com.oliverjohnson.mediacollection.datastructures.AvailableShow;
import com.oliverjohnson.mediacollection.datastructures.IAvailableMedia;
import com.oliverjohnson.mediacollection.fragments.DiscInspectionFragment;

import java.util.ArrayList;

public class AvailableMediaAdapter extends RecyclerView.Adapter<AvailableMediaAdapter.ViewHolder>
{
    private ArrayList<IAvailableMedia> mediaList;
    private FragmentActivity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView discTitleText;
        public TextView plexAvailability;
        public ImageView discPoster;
        public View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            discTitleText = itemView.findViewById(R.id.discTitle);
            plexAvailability = itemView.findViewById(R.id.streamingAvailabilityText);
            discPoster = itemView.findViewById(R.id.discPosterImage);
        }
    }

    public AvailableMediaAdapter(ArrayList<IAvailableMedia> givenMedia, FragmentActivity activity)
    {
        mediaList = givenMedia;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_media_layout, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder, int position) {
        final IAvailableMedia media = mediaList.get(position);

        holder.discPoster.setImageResource(R.drawable.missing_poster);
        holder.discPoster.getLayoutParams().height = 375;
        holder.discPoster.getLayoutParams().width = 250;


        if(media instanceof AvailableMovie) {
            final AvailableMovie movie = (AvailableMovie) media;

            holder.discTitleText.setText(movie.title);
            holder.plexAvailability.setText(movie.plexAvailabilityName);
            MediaQueryHandler.getPoster(MediaQueryHandler.ImageSize.w500, holder.discPoster, movie.posterPath, (Activity) holder.itemView.getContext());

            // Set On Click Listener for Movie
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.getIntent().putExtra(DiscInspectionFragment.DISC_SERIALIZABLE_NAME, movie);

                    Fragment fragment = new DiscInspectionFragment();
                    String fragmentTag = fragment.getTag();
                    FragmentManager fm = activity.getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment, fragmentTag);
                    transaction.addToBackStack(fragmentTag);
                    transaction.commit();
                }
            });
        }
        else if(media instanceof AvailableShow)
        {
            final AvailableShow show = (AvailableShow) media;

            holder.discTitleText.setText(show.title);
            holder.plexAvailability.setText("");
            MediaQueryHandler.getPoster(MediaQueryHandler.ImageSize.w500, holder.discPoster, show.posterPath, (Activity) holder.itemView.getContext());
        }
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }
}
