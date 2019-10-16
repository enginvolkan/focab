package com.engin.focab.services.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.engin.focab.jpa.QuoDBResult;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class QuoDBResultDeserializer implements JsonDeserializer<ArrayList<QuoDBResult>> {

	@Override
	public ArrayList<QuoDBResult> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
        // Get the "content" element from the parsed JSON
        JsonElement docs = json.getAsJsonObject().get("docs");

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(docs, type);
	}

}
