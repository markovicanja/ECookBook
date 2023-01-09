package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import rs.ac.bg.ecookbook.databinding.ActivityUserBinding;
import rs.ac.bg.ecookbook.models.RecipeModel;

public class UserActivity extends AppCompatActivity implements ServiceSetter {

    private ActivityUserBinding binding;
    private int index;

    private boolean mIsFollowing = false;
    private ArrayList<RecipeModel> recipeModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String username = Service.getInstance().getCurrentSelectedUser().getUsername();
        binding.breadcrumb.setText("HOME / " + username.toUpperCase());

        Service.getInstance().getUserRecipes(this, Service.getInstance().getCurrentSelectedUser().getUsername());
        Service.getInstance().getIsFollowing(this);

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
            Service.getInstance().setIsFollowing(this);
        });

        binding.unfollowButton.setOnClickListener(v -> {
            Service.getInstance().unfollow(this);
        });

        binding.arrowLeft.setOnClickListener(v -> {
            if (index == 0) index = recipeModels.size() - 1;
            else index--;
            setContent();
        });

        binding.arrowRight.setOnClickListener(v -> {
            if (index == recipeModels.size() - 1) index = 0;
            else index++;
            setContent();
        });

        binding.recipeImage.setOnClickListener(v -> {
            Service.getInstance().setCurrentRecipe(recipeModels.get(index));
            Intent explicitIntent = new Intent(this, RecipeDetailsActivity.class);
            startActivity(explicitIntent);
        });

        binding.recipeDetails.setOnClickListener(v -> {
            Service.getInstance().setCurrentRecipe(recipeModels.get(index));
            Intent explicitIntent = new Intent(this, RecipeDetailsActivity.class);
            startActivity(explicitIntent);
        });

    }

    private void setContent() {
        if(recipeModels.isEmpty()) return; // TODO - Ovde bi verovatno trebala da bude logika u slucaju da nema recepata za prikaz

        binding.recipeImage.setImageDrawable(recipeModels.get(index).getImg1());
        String recipeDetails = recipeModels.get(index).getName() + " | Difficulty: "
                + recipeModels.get(index).getDifficulty() + " | Rating: "
                + String.format("%.1f", recipeModels.get(index).getRating());
        binding.recipeDetails.setText(recipeDetails);
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

    private void toggleFollowingButton(){
        if(mIsFollowing){
            binding.followButton.setVisibility(View.INVISIBLE);
            binding.unfollowButton.setVisibility(View.VISIBLE);
        }
        else{
            binding.unfollowButton.setVisibility(View.INVISIBLE);
            binding.followButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setRecipes(ArrayList<RecipeModel> recipeModels) {
        this.recipeModels = recipeModels;
        setContent();
    }

    @Override
    public void setIsFollowing(boolean isFollowing){
        mIsFollowing = isFollowing;
        toggleFollowingButton();
    }
}