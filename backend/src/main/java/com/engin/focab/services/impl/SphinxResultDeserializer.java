package com.engin.focab.services.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class SphinxResultDeserializer implements JsonDeserializer<ArrayList<ArrayList<String>>> {

	@Override
	public ArrayList<ArrayList<String>> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		// Get the "content" element from the parsed JSON
		JsonElement matches = json.getAsJsonObject().get("matches");

		// Deserialize it. You use a new instance of Gson to avoid infinite recursion
		// to this deserializer
//		ArrayList<ArrayList<String>> results = new IdiomModel[((ArrayList<Object>) matches.getAsJsonObject()).size()];
		return new Gson().fromJson(matches, type);
	}

}
