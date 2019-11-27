package com.engin.focab.services;

import com.engin.focab.jpa.omdbapi.OmdbApiEpisodeSearchResult;
import com.engin.focab.jpa.omdbapi.OmdbApiSearchResult;
import com.engin.focab.jpa.omdbapi.OmdbMovieOrSeriesDetailModel;

public interface MovieInfoService {

	OmdbApiSearchResult findMovieOrSeries(String s);

	OmdbApiEpisodeSearchResult findEpisode(String imdbId, int season);

	OmdbMovieOrSeriesDetailModel getMovieOrEpisodeDetails(String imdbId);

}