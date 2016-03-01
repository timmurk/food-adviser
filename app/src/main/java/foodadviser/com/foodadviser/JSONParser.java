package foodadviser.com.foodadviser;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    public static List<Recipe> readJSON(InputStream in) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            return readRecipiesArray(reader);
        } catch (IOException e) {
            Log.e(TAG, "Failed to parse JSON: " + e.getMessage());
            return null;
        }
    }

    private static List<Recipe> readRecipiesArray(JsonReader reader) throws IOException {
        reader.beginObject();
        List<Recipe> recipes = new ArrayList<>();
        while (reader.hasNext()) {
            String nextName = reader.nextName();
            switch (nextName) {
                case "recipes":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        recipes.add(readRecipe(reader));
                    }
                    reader.endArray();

                    break;
                default:
                    reader.skipValue();
            }
        }

        reader.endObject();
        return recipes;
    }

    private static Recipe readRecipe(JsonReader reader) throws IOException {
        reader.beginObject();

        String name = null;
        String imageURL = null;
        String url = null;
        String id = null;
        double rank = -1;
        Recipe recipe = null;

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "title":
                    name = reader.nextString();
                    break;
                case "image_url":
                    imageURL = reader.nextString();
                    break;
                case "social_rank":
                    rank = reader.nextDouble();
                    break;
                case "publisher_url":
                    url = reader.nextString();
                    break;
                case "recipe_id":
                    id = reader.nextString();
                    break;
                default:
                    reader.skipValue();
            }
        }

        if (name != null && imageURL != null && rank != -1) {
            recipe = new Recipe(name, imageURL, rank);

            recipe.setUrl(url);
            recipe.setId(id);
        }

        reader.endObject();

        return recipe;
    }

    private static final String TAG = JSONParser.class.getSimpleName();
}
