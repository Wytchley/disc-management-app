package com.oliverjohnson.mediacollection.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oliverjohnson.mediacollection.MediaQueryHandler;
import com.oliverjohnson.mediacollection.R;
import com.oliverjohnson.mediacollection.adapters.AvailableMediaAdapter;
import com.oliverjohnson.mediacollection.datastructures.AvailableMovie;
import com.oliverjohnson.mediacollection.datastructures.AvailableMediaCallback;
import com.oliverjohnson.mediacollection.datastructures.AvailableShow;
import com.oliverjohnson.mediacollection.datastructures.Chunk;
import com.oliverjohnson.mediacollection.datastructures.IAvailableMedia;
import com.oliverjohnson.mediacollection.datastructures.VoidCallback;

import java.util.ArrayList;

public class CollectionFragment extends Fragment
{
    public static String tag = "COLLECTION_FRAGMENT";
    private static final ArrayList<IAvailableMedia> availableMedia = new ArrayList<>();


    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.collection_layout, container, false);

        final RecyclerView availableMediaListView = view.findViewById(R.id.available_media_list);
        final AvailableMediaAdapter mediaAdapter = new AvailableMediaAdapter(availableMedia, getActivity());



        ImageButton newDiscButton = view.findViewById(R.id.create_new_disc_button);
        newDiscButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CreateNewDiscFragment();
                String fragmentTag = fragment.getTag();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment, fragmentTag);
                transaction.commit();
            }
        });



        /*
        MediaQueryHandler.search("Meet the ", view.getContext(),
                new VolleyCallback() {
                    @Override
                    public void OnSuccess(MediaObject[] mediaObjects)
                    {
                        for (MediaObject mediaObj : mediaObjects)
                        {
                            Disc newDisc = new Disc(mediaObj.getTitle(), Disc.Format.Bluray, Disc.Format.Bluray, Disc.StreamingQuality.DVD_480p, 1, 1, mediaObj);
                            films.add(newDisc);
                            discListView.setAdapter(discAdapter);
                        }
                    }
                });
         */

        if (availableMedia.size() == 0) {
            final Chunk movieChunkInfo = new Chunk();
            MediaQueryHandler.getNumberOfAvailableMovieChunks(view.getContext(), movieChunkInfo, new VoidCallback() {
                @Override
                public void OnSuccess() {
                    for (int x = 0; x < movieChunkInfo.numberOfChunks; x++) {
                        MediaQueryHandler.getAllAvailableMovies(view.getContext(), (10 * x), new AvailableMediaCallback() {
                            @Override
                            public void OnSuccess(IAvailableMedia[] movies) {
                                for (AvailableMovie movie : (AvailableMovie[]) movies) {

                                    if (!availableMedia.contains(movie))
                                        availableMedia.add(movie);

                                    if(availableMediaListView.getAdapter() == null) {
                                        availableMediaListView.setAdapter(mediaAdapter);
                                        availableMediaListView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    }
                                    else
                                        mediaAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            });

            final Chunk showChunkInfo = new Chunk();
            MediaQueryHandler.getNumberOfAvailableShowsChunks(view.getContext(), showChunkInfo, new VoidCallback() {
                @Override
                public void OnSuccess() {
                    for (int x = 0; x < showChunkInfo.numberOfChunks; x++) {
                        MediaQueryHandler.getAllAvailableShows(view.getContext(), (10 * x), new AvailableMediaCallback() {
                            @Override
                            public void OnSuccess(IAvailableMedia[] shows) {
                                for (AvailableShow show : (AvailableShow[]) shows) {

                                    if (!availableMedia.contains(show))
                                        availableMedia.add(show);

                                    if(availableMediaListView.getAdapter() == null) {
                                        availableMediaListView.setAdapter(mediaAdapter);
                                        availableMediaListView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    }
                                    else
                                        mediaAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            });
        }
        else
        {
            availableMediaListView.setAdapter(mediaAdapter);
            availableMediaListView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return view;
    }

}
