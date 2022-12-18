package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import rs.ac.bg.ecookbook.databinding.ActivityProfileBinding;
import rs.ac.bg.ecookbook.databinding.ActivityUserBinding;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    private int index;
    private ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO
        String username = "Ognjen";
        binding.breadcrumb.setText("HOME / " + username.toUpperCase());

        // TODO
        recipes = getAllRecipes();
        setContent();

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

        binding.followButton.setOnClickListener(v -> {
            // TODO
            binding.followButton.setVisibility(View.INVISIBLE);
            binding.unfollowButton.setVisibility(View.VISIBLE);
        });

        binding.unfollowButton.setOnClickListener(v -> {
            // TODO
            binding.unfollowButton.setVisibility(View.INVISIBLE);
            binding.followButton.setVisibility(View.VISIBLE);
        });

        binding.arrowLeft.setOnClickListener(v -> {
            if (index == 0) index = recipes.size() - 1;
            else index--;
            setContent();
        });

        binding.arrowRight.setOnClickListener(v -> {
            if (index == recipes.size() - 1) index = 0;
            else index++;
            setContent();
        });

        binding.recipeImage.setOnClickListener(v -> {
            // TODO
        });

        binding.recipeDetails.setOnClickListener(v -> {
            // TODO
        });

    }

    private void setContent() {
        binding.recipeImage.setImageDrawable(recipes.get(index).getImg1());
        String recipeDetails = recipes.get(index).getName() + " | Difficulty: "
                + recipes.get(index).getDifficulty() + " | Rating: "
                + recipes.get(index).getRating();
        binding.recipeDetails.setText(recipeDetails);
    }

    private ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList();

        // TODO
        Recipe r = Recipe.createFromResources(getResources(), 1);
        recipes.add(r);
        r = Recipe.createFromResources(getResources(), 2);
        recipes.add(r);

        return recipes;
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
                explicitIntent = new Intent(this, ProfileActivity.class);
                startActivity(explicitIntent);
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
}