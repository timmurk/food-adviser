package foodadviser.com.foodadviser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class IngridientsRecyclerAdapter extends RecyclerView.Adapter<IngridientsRecyclerAdapter.IngridientsViewHolder> {
    private final LayoutInflater layoutInflater;
    public List<Ingridient> ingridients;

    public IngridientsRecyclerAdapter(Context context, List<Ingridient> ingridients) {
        layoutInflater = LayoutInflater.from(context);
        this.ingridients = ingridients;
    }

    @Override
    public IngridientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_ingridient, parent, false);
        return new IngridientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngridientsViewHolder holder, int position) {
        Ingridient category = ingridients.get(position);

        holder.categoryNameTextView.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        if (ingridients != null) {
            return ingridients.size();
        } else {
            return 0;
        }
    }

    public void addToDataSet(Ingridient ingridient) {
        ingridients.add(ingridient);
        notifyDataSetChanged();
    }

    static class IngridientsViewHolder extends RecyclerView.ViewHolder {
        final TextView categoryNameTextView;

        public IngridientsViewHolder(View itemView){
            super(itemView);

            categoryNameTextView = (TextView) itemView.findViewById(R.id.ingridient_name_text);
        }
    }
}