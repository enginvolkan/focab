package com.engin.focab.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.engin.focab.jpa.SearchResult;
import com.engin.focab.services.DictionaryService;

public class OxfordDictionary implements DictionaryService {

	@Override
	public List<SearchResult> search(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResult define(String s) {

		final String app_id = "9cb8ab6d";
		final String app_key = "f20c42adb7aafb6c3744e875fde593de";

		try {
			URL url = new URL(buildURL(s));
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestProperty("app_id", app_id);
			urlConnection.setRequestProperty("app_key", app_key);
			// read the output from the server
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String buildURL(final String word) {
		final String language = "en";
		final String word_id = word.toLowerCase();
		return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id;
	}

}