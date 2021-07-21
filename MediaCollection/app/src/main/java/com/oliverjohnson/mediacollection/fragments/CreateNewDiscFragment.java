package com.oliverjohnson.mediacollection.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.oliverjohnson.mediacollection.MediaQueryHandler;
import com.oliverjohnson.mediacollection.R;
import com.oliverjohnson.mediacollection.adapters.DiscAdapter;
import com.oliverjohnson.mediacollection.adapters.TMDbMatchAdapter;
import com.oliverjohnson.mediacollection.datastructures.Disc;
import com.oliverjohnson.mediacollection.datastructures.MediaObject;
import com.oliverjohnson.mediacollection.datastructures.VolleyCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateNewDiscFragment extends Fragment
{
    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_disc_layout, container, false);

        final EditText titleEditText = view.findViewById(R.id.disc_title_edit_text);

        // Setup format spinner
        final Spinner formatSpinner = view.findViewById(R.id.format_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Disc.getFormatNames());
        formatSpinner.setAdapter(adapter);

        Button finaliseNewDiscButton = view.findViewById(R.id.finalise_new_disc_button);
        finaliseNewDiscButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String formatChosen = Disc.getFormatNames()[formatSpinner.getSelectedItemPosition()];

                if(!formatChosen.equals(Disc.Format.CD.toString()))
                {
                    // Create dialog box to display TMDb matches
                    final Dialog mediaMatchDialog = new Dialog(getContext());
                    mediaMatchDialog.setContentView(R.layout.media_match_selection_dialog_layout);
                    mediaMatchDialog.show();

                    // List, ListAdapter and ListView for displaying the list of matches on screen
                    final List<MediaObject> matches = new ArrayList<MediaObject>();
                    final ListAdapter matchAdapter = new TMDbMatchAdapter(view.getContext(), matches);
                    final ListView matchesListView = mediaMatchDialog.findViewById(R.id.media_to_match_list_view);

                    // Search for matches with the same title
                    MediaQueryHandler.search(titleEditText.getText().toString(), view.getContext(),
                            new VolleyCallback() {
                                // If 1 or more matches are found
                                @Override
                                public void OnSuccess(MediaObject[] mediaObjects)
                                {
                                    // copy in the matches
                                    matches.addAll(Arrays.asList(mediaObjects));
                                    // set the adapter to display them
                                    matchesListView.setAdapter(matchAdapter);
                                }
                            });

                    /*
                    matchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                        {
                            Toast.makeText(view.getContext(), films.get(i).getMediaObject().getTitle(), Toast.LENGTH_LONG);
                            //mediaMatchDialog.dismiss();
                        }
                    });
                    */
                }
            }
        });

        return view;
    }
}