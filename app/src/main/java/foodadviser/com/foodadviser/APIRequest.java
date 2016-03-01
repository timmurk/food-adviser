package foodadviser.com.foodadviser;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class APIRequest {
    private static final String API_KEY = "835bff2abc459092966255cc4f141f2f";

    private static final String SEARCH_URL = "http://food2fork.com/api/search";
    private static final String RECIPE_URL = "http://food2fork.com/api/get";

    private static final String PARAM_APIKEY = "key";
    private static final String PARAM_QUERY = "q";
    private static final String PARAM_RECIPEID = "rId";

    public static URL createSearchURL(List<String> ingridients) throws MalformedURLException {
        StringBuilder ingridientsBuffer = new StringBuilder();
        for (int i = 0; i < ingridients.size(); i++) {
            ingridientsBuffer.append(ingridients.get(i));
            if (i != ingridients.size() - 1) {
                ingridientsBuffer.append(",");
            }
        }

        Uri uri = Uri.parse(SEARCH_URL).buildUpon()
                .appendQueryParameter(PARAM_APIKEY, API_KEY)
                .appendQueryParameter(PARAM_QUERY, ingridientsBuffer.toString())
                .build();

        Log.d(TAG, "Search URL: " + uri.toString());

        return new URL(uri.toString());
    }

    public static URL createRecipeURL(String recipeId) throws MalformedURLException {
        Uri uri = Uri.parse(RECIPE_URL).buildUpon()
                .appendQueryParameter(PARAM_APIKEY, API_KEY)
                .appendQueryParameter(PARAM_RECIPEID, recipeId)
                .build();

        Log.d(TAG, "Recipe URL: " + uri.toString());

        return new URL(uri.toString());
    }

    private static final String TAG = APIRequest.class.getSimpleName();
}
