package foodadviser.com.foodadviser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RecipiesRecyclerAdapter extends RecyclerView.Adapter<RecipiesRecyclerAdapter.RecipiesViewHolder>
    implements View.OnClickListener {
    private final LayoutInflater layoutInflater;
    private List<Recipe> recipies;
    private SelectedListener<Recipe> selectedListener;

    public RecipiesRecyclerAdapter(Context context, List<Recipe> recipies) {
        layoutInflater = LayoutInflater.from(context);
        this.recipies = recipies;
    }

    public void setSelectListener(SelectedListener<Recipe> selectedListener) {
        this.selectedListener = selectedListener;
    }

    @Override
    public RecipiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_recipe, parent, false);
        view.setOnClickListener(this);
        return new RecipiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipiesViewHolder holder, int position) {
        Recipe recipe = recipies.get(position);

        holder.recipeNameTextView.setText(recipe.getName());

        holder.recipeThumbnailImage.setImageBitmap(null);
        ImageLoader.getInstance().displayImage(recipe.getImageURL(), holder.recipeThumbnailImage);
        holder.recipeLayout.setTag(R.id.tag_recipe, recipe);
    }

    @Override
    public void onClick(View view) {
        Recipe recipe = (Recipe) view.getTag(R.id.tag_recipe);
        if (recipe != null && selectedListener != null) {
            selectedListener.onSelect(recipe);
        }
    }

    @Override
    public int getItemCount() {
        if (recipies != null) {
            return recipies.size();
        } else {
            return 0;
        }
    }

    public void addToDataSet(List<Recipe> recipes) {
        recipies.addAll(recipes);
        notifyDataSetChanged();
    }

    static class RecipiesViewHolder extends RecyclerView.ViewHolder {
        final TextView recipeNameTextView;
        final ImageView recipeThumbnailImage;
        final LinearLayout recipeLayout;

        public RecipiesViewHolder(View itemView){
            super(itemView);

            recipeThumbnailImage = (ImageView) itemView.findViewById(R.id.recipe_thumbnail_image);
            recipeNameTextView = (TextView) itemView.findViewById(R.id.recipe_name_text);
            recipeLayout = (LinearLayout) itemView.findViewById(R.id.recipe_layout);
        }
    }
}
