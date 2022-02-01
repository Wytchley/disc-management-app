package com.oliverjohnson.mediacollection.datastructures;

import java.io.Serializable;

public class AvailableShow implements Serializable, IAvailableMedia {
    public int ID;
    public String title;
    public int tmdbID;
    public String backdropPath;
    public String posterPath;
    public String[] genres;
    public int numberOfSeasons;
    public int numberOfEpisodes;
    public String firstAirDate;
    public String lastAirDate;
    public float averageVote;
    public int voteCount;
    public float popularity;
}
