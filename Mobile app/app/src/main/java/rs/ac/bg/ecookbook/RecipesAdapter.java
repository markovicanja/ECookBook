package rs.ac.bg.ecookbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.ac.bg.ecookbook.databinding.ViewHolderRecipesBinding;
import rs.ac.bg.ecookbook.models.RecipeModel;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<RecipeModel> recipes;

    public List<RecipeModel> getRecipes(){
        return recipes;
    }

    public RecipesAdapter(List<RecipeModel> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipesAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderRecipesBinding viewHolderRecipesBinding = ViewHolderRecipesBinding.inflate(
                layoutInflater,
                parent,
                false);
        return new RecipesAdapter.RecipeViewHolder(viewHolderRecipesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.RecipeViewHolder holder, int position) {
        RecipeModel recipe = recipes.get(position);
        ViewHolderRecipesBinding binding = holder.binding;

        binding.recipeImage.setImageDrawable(recipe.getImg1());
        String recipeDetails = recipe.getName() + " | Difficulty: "
                + recipe.getDifficulty() + " | Rating: "
                + String.format("%.1f", recipe.getRating());
        binding.recipeDetails.setText(recipeDetails);

        binding.recipeImage.setOnClickListener(v -> {
            Context context = v.getContext();
            Service.getInstance().setCurrentRecipe(recipe);
            Intent explicitIntent = new Intent(context, RecipeDetailsActivity.class);
            context.startActivity(explicitIntent);
        });

        binding.recipeDetails.setOnClickListener(v -> {
            Context context = v.getContext();
            Service.getInstance().setCurrentRecipe(recipe);
            Intent explicitIntent = new Intent(context, RecipeDetailsActivity.class);
            context.startActivity(explicitIntent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public ViewHolderRecipesBinding binding;

        public RecipeViewHolder(@NonNull ViewHolderRecipesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
