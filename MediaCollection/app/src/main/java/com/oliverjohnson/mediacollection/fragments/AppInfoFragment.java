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

public class AppInfoFragment  extends Fragment
{
    public static String DISC_SERIALIZABLE_NAME = "APP_INFO";

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.app_info_layout, container, false);
        return view;
    }
}
