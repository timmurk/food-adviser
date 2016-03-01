package foodadviser.com.foodadviser;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class IngridientsActivity extends AppCompatActivity
    implements AddIngridientDialogFragment.AddIngridientDialogListener {

    private List<Ingridient> ingridients = new ArrayList<>();

    private RecyclerView ingridientsRecyclerView = null;
    private IngridientsRecyclerAdapter ingridientAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUI();

        /*String[] ingrs = {"Tomatoe", "Lemon", "Sault"};
        try {
            APIRequest.createSearchURL(ingrs);
            APIRequest.createRecipeURL("123456");
        } catch (MalformedURLException ignored) {

        }*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                Intent intent = new Intent(this, RecipiesActivity.class);
                ArrayList<String> ingrs = new ArrayList<>();
                for (Ingridient ingr: ingridientAdapter.ingridients) {
                    ingrs.add(ingr.getName());
                }
                intent.putExtra("INGRIDIENTS_EXTRA", ingrs);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDialogPositiveClick(String userInput) {
        Log.d(TAG, "onDialogPositiveClick");
        ingridientAdapter.addToDataSet(new Ingridient(userInput));
    }

    private void setupUI() {
        setContentView(R.layout.activity_ingridients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Add Clicked!", Toast.LENGTH_SHORT).show();

                DialogFragment newFragment = new AddIngridientDialogFragment();
                newFragment.onAttach(IngridientsActivity.this);
                newFragment.show(getFragmentManager(), "addIngridient");
            }
        });

        ingridientsRecyclerView = (RecyclerView) findViewById(R.id.ingridients_rv);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        ingridientsRecyclerView.setLayoutManager(manager);

        ingridientAdapter = new IngridientsRecyclerAdapter(this, new ArrayList<Ingridient>());
        ingridientsRecyclerView.setAdapter(ingridientAdapter);

        ingridientsRecyclerView.addItemDecoration(new RecyclerDividerDecorator(R.color.colorPrimary));
    }

    private static final String TAG = IngridientsActivity.class.getSimpleName();
}
