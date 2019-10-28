package com.engin.focab.jpa.omdbapi;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Episode {

@SerializedName("Title")
@Expose
private String title;
@SerializedName("Released")
@Expose
private String released;
@SerializedName("Episode")
@Expose
private String episode;
@SerializedName("imdbRating")
@Expose
private String imdbRating;
@SerializedName("imdbID")
@Expose
private String imdbID;

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getReleased() {
return released;
}

public void setReleased(String released) {
this.released = released;
}

public String getEpisode() {
return episode;
}

public void setEpisode(String episode) {
this.episode = episode;
}

public String getImdbRating() {
return imdbRating;
}

public void setImdbRating(String imdbRating) {
this.imdbRating = imdbRating;
}

public String getImdbID() {
return imdbID;
}

public void setImdbID(String imdbID) {
this.imdbID = imdbID;
}

}

public class OmdbApiEpisodeSearchResult {

@SerializedName("Title")
@Expose
private String title;
@SerializedName("Season")
@Expose
private String season;
@SerializedName("totalSeasons")
@Expose
private String totalSeasons;
@SerializedName("Episodes")
@Expose
private List<Episode> episodes = null;
@SerializedName("Response")
@Expose
private String response;

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getSeason() {
return season;
}

public void setSeason(String season) {
this.season = season;
}

public String getTotalSeasons() {
return totalSeasons;
}

public void setTotalSeasons(String totalSeasons) {
this.totalSeasons = totalSeasons;
}

public List<Episode> getEpisodes() {
return episodes;
}

public void setEpisodes(List<Episode> episodes) {
this.episodes = episodes;
}

public String getResponse() {
return response;
}

public void setResponse(String response) {
this.response = response;
}

}