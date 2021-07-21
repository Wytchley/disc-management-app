package com.oliverjohnson.mediacollection.datastructures;

import java.io.Serializable;

public class MediaObject implements Serializable
{
    //TMDB Information
    private int movieID;
    private String title;
    private String posterPath;
    private String backdropPath;
    private String[] genres;
    private String originalLanguage;
    private String description;
    private String releaseDate;
    private String tagline;
    private int runtime;
    private float averageVote;
    private int voteCount;
    private int budget;

    public void setMovieID(int movieID) { this.movieID = movieID; }
    public void setTitle(String title) { this.title = title; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }
    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }
    public void setTagline(String tagline) { this.tagline = tagline; }
    public void setOriginalLanguage(String originalLanguage) { this.originalLanguage = originalLanguage; }
    public void setDescription(String description) { this.description = description; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setRuntime(int runtime) { this.runtime = runtime; }
    public void setAverageVote(float averageVote) { this.averageVote = averageVote; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
    public void setBudget(int budget) { this.budget = budget; }

    public int getMovieID() { return movieID; }
    public String getTitle() { return title; }
    public String getPosterPath() { return posterPath; }
    public String getBackdropPath() { return backdropPath; }
    public String getTagline() { return tagline; }
    public String getOriginalLanguage() { return originalLanguage; }
    public String getDescription() { return description; }
    public String getReleaseDate() { return releaseDate; }
    public int getRuntime() { return runtime; }
    public float getAverageVote() { return averageVote; }
    public int getVoteCount() { return voteCount; }
    public int getBudget() { return budget; }
}
