package com.engin.focab.jpa;

//********************************************
//Generated by http://www.jsonschema2pojo.org/
//********************************************


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuoDBResult {

@SerializedName("rating_imdb")
@Expose
private Double ratingImdb;
@SerializedName("image")
@Expose
private String image;
@SerializedName("year")
@Expose
private Integer year;
@SerializedName("title_id")
@Expose
private Integer titleId;
@SerializedName("phrase_profanity")
@Expose
private Boolean phraseProfanity;
@SerializedName("title")
@Expose
private String title;
@SerializedName("uuid")
@Expose
private String uuid;
@SerializedName("duration")
@Expose
private Integer duration;
@SerializedName("title_profanity")
@Expose
private Boolean titleProfanity;
@SerializedName("imdb")
@Expose
private Integer imdb;
@SerializedName("phrase")
@Expose
private String phrase;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("votes_imdb")
@Expose
private Integer votesImdb;
@SerializedName("phrase_id")
@Expose
private Integer phraseId;
@SerializedName("time")
@Expose
private Integer time;
@SerializedName("genres")
@Expose
private List<String> genres = null;
@SerializedName("episode")
@Expose
private Integer episode;
@SerializedName("root_imdb")
@Expose
private Integer rootImdb;
@SerializedName("season")
@Expose
private Integer season;
@SerializedName("score")
@Expose
private Integer score;

public Double getRatingImdb() {
return ratingImdb;
}

public void setRatingImdb(Double ratingImdb) {
this.ratingImdb = ratingImdb;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public Integer getYear() {
return year;
}

public void setYear(Integer year) {
this.year = year;
}

public Integer getTitleId() {
return titleId;
}

public void setTitleId(Integer titleId) {
this.titleId = titleId;
}

public Boolean getPhraseProfanity() {
return phraseProfanity;
}

public void setPhraseProfanity(Boolean phraseProfanity) {
this.phraseProfanity = phraseProfanity;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getUuid() {
return uuid;
}

public void setUuid(String uuid) {
this.uuid = uuid;
}

public Integer getDuration() {
return duration;
}

public void setDuration(Integer duration) {
this.duration = duration;
}

public Boolean getTitleProfanity() {
return titleProfanity;
}

public void setTitleProfanity(Boolean titleProfanity) {
this.titleProfanity = titleProfanity;
}

public Integer getImdb() {
return imdb;
}

public void setImdb(Integer imdb) {
this.imdb = imdb;
}

public String getPhrase() {
return phrase;
}

public void setPhrase(String phrase) {
this.phrase = phrase;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public Integer getVotesImdb() {
return votesImdb;
}

public void setVotesImdb(Integer votesImdb) {
this.votesImdb = votesImdb;
}

public Integer getPhraseId() {
return phraseId;
}

public void setPhraseId(Integer phraseId) {
this.phraseId = phraseId;
}

public Integer getTime() {
return time;
}

public void setTime(Integer time) {
this.time = time;
}

public List<String> getGenres() {
return genres;
}

public void setGenres(List<String> genres) {
this.genres = genres;
}

public Integer getEpisode() {
return episode;
}

public void setEpisode(Integer episode) {
this.episode = episode;
}

public Integer getRootImdb() {
return rootImdb;
}

public void setRootImdb(Integer rootImdb) {
this.rootImdb = rootImdb;
}

public Integer getSeason() {
return season;
}

public void setSeason(Integer season) {
this.season = season;
}

public Integer getScore() {
return score;
}

public void setScore(Integer score) {
this.score = score;
}

}