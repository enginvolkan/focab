package com.engin.focab.services;

import java.util.List;

import com.engin.focab.jpa.omdbapi.OmdbApiEpisodeSearchResult;
import com.engin.focab.jpa.omdbapi.OmdbApiSearchResult;

public interface MovieInfoService {

	List<OmdbApiSearchResult> findMovieOrSeries(String s);

	List<OmdbApiEpisodeSearchResult> findEpisode(String imdbId, int season);

}