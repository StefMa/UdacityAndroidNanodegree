package com.udacity.sandwichclub.utils;

import android.support.annotation.Nullable;
import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    @Nullable
    public static Sandwich parseSandwichJson(String json) {
        try {
            final JSONObject sandwitchJson = new JSONObject(json);
            final JSONObject jsonName = sandwitchJson.getJSONObject("name");
            final String mainName = jsonName.getString("mainName");
            final List<String> knownAs = jsonArrayToList(jsonName.getJSONArray("alsoKnownAs"));
            final String placeOfOrigin = sandwitchJson.getString("placeOfOrigin");
            final String description = sandwitchJson.getString("description");
            final String image = sandwitchJson.getString("image");
            final List<String> ingredients = jsonArrayToList(sandwitchJson.getJSONArray("ingredients"));

            return new Sandwich(mainName, knownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(JsonUtils.class.getSimpleName(), "Can not parse json: " + json);
            return null;
        }
    }

    private static <T> List<T> jsonArrayToList(final JSONArray jsonArray) throws JSONException {
        final List<T> list = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            //noinspection unchecked
            list.add((T) jsonArray.get(i));
        }
        return list;
    }


}
