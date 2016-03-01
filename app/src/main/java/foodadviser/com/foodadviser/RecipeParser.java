package foodadviser.com.foodadviser;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RecipeParser {
    public static void readJSON(InputStream in, Recipe recipe) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            readResult(reader, recipe);
        } catch (IOException e) {
            Log.e(TAG, "Failed to parse JSON: " + e.getMessage());
        }
    }

    private static void readResult(JsonReader reader, Recipe recipe) throws IOException {
        reader.beginObject();

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "recipe":
                    readRecipe(reader, recipe);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        reader.endObject();
    }

    private static void readRecipe(JsonReader reader, Recipe recipe) throws IOException {
        reader.beginObject();

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "title":
                    recipe.setName(reader.nextString());
                    break;
                case "social_rank":
                    recipe.setRank(reader.nextDouble());
                    break;
                case "image_url":
                    recipe.setImageURL(reader.nextString());
                    break;
                case "f2f_url":
                    recipe.setUrl(reader.nextString());
                    break;
                case "ingredients":
                    recipe.setIngridients(readIngridients(reader));
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        reader.endObject();
    }

    private static ArrayList<String> readIngridients(JsonReader reader) throws IOException {
        reader.beginArray();

        ArrayList<String> list = new ArrayList<>();

        while (reader.hasNext()) {
            list.add(reader.nextString());
        }

        reader.endArray();

        return list;
    }

    private static final String TAG = RecipeParser.class.getSimpleName();
}
