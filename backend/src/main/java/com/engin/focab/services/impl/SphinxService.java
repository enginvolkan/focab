package com.engin.focab.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import com.engin.focab.services.IndexedSearchService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component
public class SphinxService implements IndexedSearchService {

	@Override
	public HashSet<String> findIdiomsByWord(String word) {
		try {
			URL serverUrl = new URL(
					"http://localhost:9080/search?index=idiom&match=" + word.replace(" ", "%20") + "&limit=200");
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

			HashSet<String> resultSet = new HashSet<String>();

			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				ArrayList<String> arrayList = (ArrayList<String>) iterator.next();

				resultSet.add(arrayList.get(1));
			}
//			Gson gson = new Gson();
//			Type listType = new TypeToken<ArrayList<Map<Integer, String>>>() {
//			}.getType();
//
//			ArrayList<Map<Integer, String>> results = gson.fromJson(stringBuilder.toString(), listType);
			return resultSet;

		} catch (Exception e) {
			e.printStackTrace();
			return new HashSet<String>();
		}
	}

	@Override
	public HashSet<String> findPhrasalsByWord(String word) {
		try {
			URL serverUrl = new URL(
					"http://localhost:9080/search?index=phrasal&match=" + word.replace(" ", "%20") + "&limit=200");
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

			HashSet<String> resultSet = new HashSet<String>();

			for (Iterator iterator = results.iterator(); iterator.hasNext();) {
				ArrayList<String> arrayList = (ArrayList<String>) iterator.next();

				resultSet.add(arrayList.get(1));
			}
//				Gson gson = new Gson();
//				Type listType = new TypeToken<ArrayList<Map<Integer, String>>>() {
//				}.getType();
			//
//				ArrayList<Map<Integer, String>> results = gson.fromJson(stringBuilder.toString(), listType);
			return resultSet;

		} catch (Exception e) {
			e.printStackTrace();
			return new HashSet<String>();
		}

	}

}
