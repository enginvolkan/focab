package com.engin.focab.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.SearchResult;
import com.engin.focab.services.DictionaryService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class DatamuseDictionaryService implements DictionaryService {

	@Override
	public List<SearchResult> search(String s) {
		try {
			URL url = new URL(buildURL(URLEncoder.encode(s, StandardCharsets.UTF_8.toString())));
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Accept", "application/json");

			// read the output from the server
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<SearchResult>>(){}.getType();
			return gson.fromJson(stringBuilder.toString(),listType);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SearchResult define(String s) {
		List<SearchResult> searchResult = search(s);
		return searchResult!=null?searchResult.get(0):null;

	}

	private String buildURL(final String word) {
		final String searchTerm = word.toLowerCase().trim().replaceAll("/ /","+");
		return "https://api.datamuse.com/words?sp=*" + searchTerm+"*&md=dp";
	}

}