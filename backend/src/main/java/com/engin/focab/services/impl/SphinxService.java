package com.engin.focab.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.engin.focab.services.IndexedSearchService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component
public class SphinxService implements IndexedSearchService {

	@Override
	public HashSet<String> findIdiomsByWord(String word) {

		String url = "http://localhost:9080/search?index=idiom&match=" + word.replace(" ", "%20") + "&limit=200";
		HashSet<String> resultSet = new HashSet<String>(runSphinxQuery(url, 2));
		return resultSet;
	}

	@Override
	public HashMap<String, String> findIdiomsByWordWithRegex(String word) {

		String url = "http://localhost:9080/search?index=idiom&match=" + word.replace(" ", "%20") + "&limit=200";
		return runSphinxQuery(url, 1, 2);
	}
	@Override
	public HashSet<String> findPhrasalsByWord(String word) {

		String url = "http://localhost:9080/search?index=phrasal&match=" + word.replace(" ", "%20") + "&limit=200";
		HashSet<String> resultSet = new HashSet<String>(runSphinxQuery(url, 1));
		return resultSet;
	}

	@Override
	public List<String> findCommonWordsByLevel(int level) {

		String url = "http://localhost:9080/sql?query=select%20*%20from%20commonwords%20where%20level%3C" + level
				+ "%20limit%20" + level + ";";
		ArrayList<String> results = runSphinxQuery(url, 1);
		return results;
	}

	private ArrayList<String> runSphinxQuery(String url, int columnOrder) {
		try {
			URL serverUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
			urlConnection.setRequestProperty("Accept", "application/json");

			// read the output from the server
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			Type listType = new TypeToken<ArrayList<ArrayList<String>>>() {
			}.getType();
			Gson gson = new GsonBuilder().registerTypeAdapter(listType, new SphinxResultDeserializer()).create();

			ArrayList<ArrayList<String>> results = gson.fromJson(stringBuilder.toString().trim(), listType);

			ArrayList<String> resultList = new ArrayList<String>();

			for (Iterator<ArrayList<String>> iterator = results.iterator(); iterator.hasNext();) {
				ArrayList<String> arrayList = iterator.next();

				resultList.add(arrayList.get(columnOrder).toLowerCase());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	private HashMap<String, String> runSphinxQuery(String url, int columnOrder1, int columnOrder2) {
		try {
			URL serverUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
			urlConnection.setRequestProperty("Accept", "application/json");

			// read the output from the server
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			Type listType = new TypeToken<ArrayList<ArrayList<String>>>() {
			}.getType();
			Gson gson = new GsonBuilder().registerTypeAdapter(listType, new SphinxResultDeserializer()).create();

			ArrayList<ArrayList<String>> results = gson.fromJson(stringBuilder.toString().trim(), listType);

			HashMap<String, String> resultList = new HashMap<>();

			for (Iterator<ArrayList<String>> iterator = results.iterator(); iterator.hasNext();) {
				ArrayList<String> arrayList = iterator.next();

				resultList.put(arrayList.get(columnOrder1), arrayList.get(columnOrder2).replace("\\\\", "\\"));
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String>();
		}
	}
}
