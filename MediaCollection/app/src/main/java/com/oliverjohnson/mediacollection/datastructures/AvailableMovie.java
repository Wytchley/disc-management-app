package com.oliverjohnson.mediacollection.datastructures;

import java.io.Serializable;

public class AvailableMovie implements Serializable, IAvailableMedia
{
    public int ID;
    public String title;
    public int tmdbID;
    public int plexAvailabilityID;
    public String plexAvailabilityName;

    //TMDB Information
    public String posterPath;
    public String backdropPath;
    public String[] genres;
    public String originalLanguage;
    public String description;
    public String releaseDate;
    public String tagline;
    public int runtime;
    public float averageVote;
    public int voteCount;
    public long budget;
}
