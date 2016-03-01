package foodadviser.com.foodadviser;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipiesActivity extends AppCompatActivity
    implements SelectedListener<Recipe> {

    private ProgressBar progressBar = null;
    private RecipiesRecyclerAdapter adapter = null;
    private RecyclerView recyclerView = null;

    void setupUI() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recipes_recycler_view);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        adapter = new RecipiesRecyclerAdapter(this, new ArrayList<Recipe>());
        adapter.setSelectListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerDividerDecorator(R.color.colorPrimary));

        //TODO: move to main activity
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);

        setupUI();

        Intent intent = getIntent();
        List<String> ingrs = intent.getStringArrayListExtra("INGRIDIENTS_EXTRA");

        try {
            LoadRecipiesTask task = new LoadRecipiesTask();
            task.attachActivity(this);
            task.execute(APIRequest.createSearchURL(ingrs));
        } catch (MalformedURLException e) {
            Log.e(TAG, "Corrupted request URL: " + e.getMessage());
        }
    }

    @Override
    public void onSelect(Recipe item) {
        Intent intent = new Intent(this, RecipeDetailsActiviti.class);

        intent.putExtra(RECIPE_ID_EXTRA, item.getId());

        startActivity(intent);
    }

    public static final String RECIPE_ID_EXTRA = "RECIPE_ED";

    private static class LoadRecipiesTask extends AsyncTask<URL, Void, DownloadState> {
        private DownloadState state = DownloadState.LOADING;
        private RecipiesActivity activity; //TODO: weak reference
        private List<Recipe> recipes = null;

        @Override
        protected DownloadState doInBackground(URL ... urls) {
            if (urls.length < 1) {
                Log.e(TAG, "No arguments passed");
                return DownloadState.FAILED;
            }

            InputStream in = null;
            //TODO: release resource
            try {
                in = Utils.openStreamForURL(urls[0]);
                Log.d(TAG, urls[0].toString());

                recipes = JSONParser.readJSON(in);
                if (recipes != null) {
                    for (Recipe recipe: recipes) {
                        Log.d(TAG, recipe.toString());
                    }
                } else {
                    Log.d(TAG, "Null came back from JSONParser");
                }
            } catch (IOException e) {
                Log.e(TAG, "Failed to load data: " + e.getMessage());
                return DownloadState.FAILED;
            }

            return DownloadState.SUCCESS;
        }

        @Override
        protected void onPostExecute(DownloadState state) {
            this.state = state;
            if (state == DownloadState.SUCCESS) {
                activity.progressBar.setVisibility(View.INVISIBLE);
                activity.adapter.addToDataSet(recipes);
            } else {
                Toast.makeText(activity, "Failed to load recipes", Toast.LENGTH_SHORT).show();
            }
        }

        public void attachActivity(RecipiesActivity activity) {
            this.activity = activity;
        }

        private static final String TAG = LoadRecipiesTask.class.getSimpleName();
    }

    private static final String TAG = RecipiesActivity.class.getSimpleName();
}

enum DownloadState {
    LOADING,
    SUCCESS,
    FAILED
}
