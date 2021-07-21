package com.oliverjohnson.mediacollection.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.oliverjohnson.mediacollection.MediaQueryHandler;
import com.oliverjohnson.mediacollection.R;
import com.oliverjohnson.mediacollection.adapters.DiscAdapter;
import com.oliverjohnson.mediacollection.datastructures.Disc;
import com.oliverjohnson.mediacollection.datastructures.MediaObject;
import com.oliverjohnson.mediacollection.datastructures.VolleyCallback;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends Fragment
{
    public static String tag = "COLLECTION_FRAGMENT";

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.collection_layout, container, false);

        final ListView discListView = view.findViewById(R.id.disc_list);

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

        final List<Disc> films = new ArrayList<Disc>();
        final ListAdapter discAdapter = new DiscAdapter(view.getContext(), films);

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

        discListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                final Disc discObject = films.get(i);
                MediaObject mediaObject = discObject.getMediaObject();

                MediaQueryHandler.getAllInfo(mediaObject, getContext(), new VolleyCallback() {
                    @Override
                    public void OnSuccess(MediaObject[] mediaObjects) {
                        getActivity().getIntent().putExtra(DiscInspectionFragment.DISC_SERIALIZABLE_NAME, discObject);

                        Fragment fragment = new DiscInspectionFragment();
                        String fragmentTag = fragment.getTag();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment, fragmentTag);
                        transaction.addToBackStack(fragmentTag);
                        transaction.commit();
                    }
                });

            }
        });


        return view;
    }
}
