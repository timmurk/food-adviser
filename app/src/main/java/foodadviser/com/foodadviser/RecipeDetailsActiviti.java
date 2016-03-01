package foodadviser.com.foodadviser;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecipeDetailsActiviti extends AppCompatActivity {

    private Recipe recipe = new Recipe();
    private String recipeId;

    private ImageView photoImageView = null;
    private TextView nameTextView = null;
    private TextView ingridientsTextView = null;
    private TextView rankTextView = null;
    private ScrollView contentView = null;
    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details_activiti);

        setupUI();

        Intent intent = getIntent();
        String recipeId = intent.getStringExtra(RecipiesActivity.RECIPE_ID_EXTRA);

        if (recipeId != null) {
            this.recipeId = recipeId;
            RecipeDetailsTask task = new RecipeDetailsTask();
            task.setActivity(this);
            task.execute();
        }
    }

    private void setupUI() {
        nameTextView = (TextView) findViewById(R.id.recipe_name_text_view);
        ingridientsTextView = (TextView) findViewById(R.id.recipe_ingridients_text_view);
        rankTextView = (TextView) findViewById(R.id.recipe_rank_text_view);
        photoImageView = (ImageView) findViewById(R.id.recipe_image_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar2);
        contentView = (ScrollView) findViewById(R.id.scroll_view);

        contentView.setVisibility(View.INVISIBLE);
    }

    public void onMoreClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getUrl()));
        startActivity(intent);
    }

    private void updateUI() {
        Log.d(TAG, "recipe set: " +
                recipe.getName() + "\n" +
                recipe.getImageURL() + "\n" +
                recipe.getUrl());

        nameTextView.setText(recipe.getName());

        StringBuilder builder = new StringBuilder();
        for (String ing: recipe.getIngridients()) {
            builder.append(ing + "\n");
        }
        ingridientsTextView.setText(builder.toString());
        rankTextView.setText(String.valueOf(recipe.getRank()));

        ImageLoader.getInstance().displayImage(recipe.getImageURL(), photoImageView);

        progressBar.setVisibility(View.INVISIBLE);
        contentView.setVisibility(View.VISIBLE);
    }

    private static class RecipeDetailsTask extends AsyncTask<Void, Void, DownloadState> {

        //TODO: weak reference
        private RecipeDetailsActiviti activity;

        private DownloadState state;

        @Override
        protected DownloadState doInBackground(Void ... params) {
            InputStream in = null;
            //TODO: release resources
            try {
                in = Utils.openStreamForURL(APIRequest.createRecipeURL(activity.recipeId));

                RecipeParser.readJSON(in, activity.recipe);
            } catch (MalformedURLException e) {
                Log.e(TAG, "Malformed recipe detailes URL");
                return DownloadState.FAILED;
            } catch (IOException e) {
                Log.e(TAG, "Failed to get recepies details");
                return DownloadState.FAILED;
            }

            return DownloadState.SUCCESS;
        }

        public void setActivity(RecipeDetailsActiviti activity) {
            this.activity = activity;
        }

        @Override
        protected void onPostExecute(DownloadState state) {
            this.state = state;
            if (state == DownloadState.SUCCESS) {
                activity.updateUI();
            }
        }
    }

    private static final String TAG = RecipeDetailsActiviti.class.getSimpleName();
}
