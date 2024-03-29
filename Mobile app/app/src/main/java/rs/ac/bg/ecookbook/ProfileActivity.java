package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import rs.ac.bg.ecookbook.databinding.ActivityProfileBinding;
import rs.ac.bg.ecookbook.models.RecipeModel;

public class ProfileActivity extends AppCompatActivity implements ServiceSetter{

    private ActivityProfileBinding binding;

    private ArrayList<RecipeModel> recipeModels; // pointer
    private ArrayList<RecipeModel> userRecipes, savedRecipes, recommendedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String username = Service.getInstance().getLoggedUser().getUsername();
        binding.breadcrumb.setText("HOME / " + username.toUpperCase());

        Service.getInstance().getUserRecipes(this, Service.getInstance().getLoggedUser().getUsername());
        Service.getInstance().getAllSavedRecipes(this);
        Service.getInstance().getAllRecommendedRecipes(this);
        Service.getInstance().getFollowings(this);

        binding.followingLayout.setVisibility(View.INVISIBLE);

        binding.name.setOnClickListener(v -> {
            Intent explicitIntent = new Intent(this, MainActivity.class);
            startActivity(explicitIntent);
        });

        binding.logo.setOnClickListener(v -> {
            Intent explicitIntent = new Intent(this, MainActivity.class);
            startActivity(explicitIntent);
        });

        binding.breadcrumb.setOnClickListener(v -> {
            Intent explicitIntent = new Intent(this, MainActivity.class);
            startActivity(explicitIntent);
        });

        binding.myRecipesButton.setOnClickListener(v -> {
            this.recipeModels = userRecipes;
            setContent();

            resetButtonColors();
            binding.myRecipesButton.setBackgroundColor(getResources().getColor(R.color.green));
            binding.followingLayout.setVisibility(View.INVISIBLE);
        });

        binding.savedRecipesButton.setOnClickListener(v -> {
            this.recipeModels = savedRecipes;
            setContent();

            resetButtonColors();
            binding.savedRecipesButton.setBackgroundColor(getResources().getColor(R.color.green));
            binding.followingLayout.setVisibility(View.INVISIBLE);
        });

        binding.recommendedRecipesButton.setOnClickListener(v -> {
            this.recipeModels = recommendedRecipes;
            setContent();

            resetButtonColors();
            binding.recommendedRecipesButton.setBackgroundColor(getResources().getColor(R.color.green));
            binding.followingLayout.setVisibility(View.INVISIBLE);
        });

        binding.followingButton.setOnClickListener(v -> {
            resetButtonColors();
            binding.followingButton.setBackgroundColor(getResources().getColor(R.color.green));
            binding.scrollView.setVisibility(View.INVISIBLE);
            binding.noRecipesLayout.setVisibility(View.INVISIBLE);
            binding.followingLayout.setVisibility(View.VISIBLE);
        });
    }

    private void setContent() {
        if (recipeModels == null) return;
        if(recipeModels.isEmpty()) {
            binding.noRecipesLayout.setVisibility(View.VISIBLE);
            binding.scrollView.setVisibility(View.INVISIBLE);
            return;
        }

        RecipesAdapter recipesAdapter = new RecipesAdapter(recipeModels);
        binding.recipesRecyclerView.setHasFixedSize(true);
        binding.recipesRecyclerView.setAdapter(recipesAdapter);
        binding.recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.noRecipesLayout.setVisibility(View.INVISIBLE);
        binding.scrollView.setVisibility(View.VISIBLE);
    }

    private void resetButtonColors() {
        binding.myRecipesButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.savedRecipesButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.recommendedRecipesButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.followingButton.setBackgroundColor(getResources().getColor(R.color.orange));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent explicitIntent;
        switch(item.getItemId()){
            case R.id.home_menu_item:
                explicitIntent = new Intent(this, MainActivity.class);
                startActivity(explicitIntent);
                return true;
            case R.id.recipes_menu_item:
                explicitIntent = new Intent(this, RecipesActivity.class);
                startActivity(explicitIntent);
                return true;
            case R.id.add_recipe_menu_item:
                explicitIntent = new Intent(this, AddRecipeActivity.class);
                startActivity(explicitIntent);
                return true;
            case R.id.profile_menu_item:
                return true;
            case R.id.about_menu_item:
                explicitIntent = new Intent(this, AboutUsActivity.class);
                startActivity(explicitIntent);
                return true;
            case R.id.logout_menu_item:
                SharedPreferences sharedPreferences = getSharedPreferences("SP", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("logged", false);
                editor.apply();
                explicitIntent = new Intent(this, MainActivity.class);
                startActivity(explicitIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setRecipes(ArrayList<RecipeModel> userRecipes) {
        this.userRecipes = userRecipes;
        this.recipeModels = userRecipes;
        setContent();
    }

    @Override
    public void setSavedRecipes(ArrayList<RecipeModel> savedRecipes) {
        this.savedRecipes = savedRecipes;
    }

    @Override
    public void setRecommendedRecipes(ArrayList<RecipeModel> recommendedRecipes) {
        this.recommendedRecipes = recommendedRecipes;
    }

    @Override
    public void setFollowings(ArrayList<String> followings){
        FollowingAdapter mFollowingAdapter = new FollowingAdapter(followings);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(mFollowingAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}