package com.engin.focab.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.omdbapi.OmdbApiEpisodeSearchResult;
import com.engin.focab.jpa.omdbapi.OmdbApiSearchResult;
import com.engin.focab.services.MovieInfoService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class OmdbApiService implements MovieInfoService {

	private static String BASE_URL = "http://www.omdbapi.com/?i=tt0944947&Season=1&apikey=57af8e34"; 
	/* (non-Javadoc)
	 * @see com.engin.focab.services.impl.MovieInfoService#findMovieOrSeries(java.lang.String)
	 */
	@Override
	public List<OmdbApiSearchResult> findMovieOrSeries(String s) {
		try {
			URL serverUrl = new URL(BASE_URL+"&s="+s);
			HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
			urlConnection.setRequestProperty("Accept", "application/json");

			// read the output from the server
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<OmdbApiSearchResult>>(){}.getType();
			return gson.fromJson(stringBuilder.toString(),listType);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.engin.focab.services.impl.MovieInfoService#findEpisode(java.lang.String, int)
	 */
	@Override
	public List<OmdbApiEpisodeSearchResult> findEpisode(String imdbId, int season) {
		try {
			URL serverUrl = new URL(BASE_URL+"&i="+imdbId + "&Season="+season);
			HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
			urlConnection.setRequestProperty("Accept", "application/json");

			// read the output from the server
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<OmdbApiSearchResult>>(){}.getType();
			return gson.fromJson(stringBuilder.toString(),listType);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
