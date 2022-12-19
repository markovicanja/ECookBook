package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import rs.ac.bg.ecookbook.databinding.ActivityProfileBinding;
import rs.ac.bg.ecookbook.databinding.ActivityRecipeDetailsBinding;

public class RecipeDetailsActivity extends AppCompatActivity {

    private ActivityRecipeDetailsBinding binding;
    private int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            Intent explicitIntent = new Intent(this, RecipesActivity.class);
            startActivity(explicitIntent);
        });

        binding.buttonAddComment.setOnClickListener(v -> {
            // TODO
        });

        binding.saveButton.setOnClickListener(v -> {
            // TODO
        });

        binding.recommendButton.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.recommend_modal, null);

            alert.setView(view);

            AlertDialog dialog = alert.create();
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            EditText usernameText = view.findViewById(R.id.username_text);
            Button recommendButton = view.findViewById(R.id.recommend_button);

            recommendButton.setOnClickListener(l -> {
                String username = usernameText.getText().toString();

                // TODO

                dialog.dismiss();
            });

            dialog.show();
        });

        binding.visibilityButton.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.visibility_modal, null);

            alert.setView(view);

            AlertDialog dialog = alert.create();
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            Button changeVisibilityButton = view.findViewById(R.id.change_visibility_button);

            changeVisibilityButton.setOnClickListener(l -> {
                // TODO

                dialog.dismiss();
            });

            dialog.show();
        });

        binding.star1.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);

            if (rating > 1) {
                rating -= 1;
            }
            else if (rating < 1) {
                rating += 1;
            }

            binding.ratingText.setText("Rating: " + rating);
            // TODO
            // update rating
        });

        binding.star2.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);

            if (rating > 2) {
                rating -= 1;
            }
            else if (rating < 2) {
                rating += 1;
            }

            binding.ratingText.setText("Rating: " + rating);
            // TODO
            // update rating
        });

        binding.star3.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);

            if (rating > 3) {
                rating -= 1;
            }
            else if (rating < 3) {
                rating += 1;
            }

            binding.ratingText.setText("Rating: " + rating);
            // TODO
            // update rating
        });

        binding.star4.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);

            if (rating > 4) {
                rating -= 1;
            }
            else if (rating < 4) {
                rating += 1;
            }

            binding.ratingText.setText("Rating: " + rating);
            // TODO
            // update rating
        });

        binding.star5.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);

            if (rating < 5) {
                rating += 1;
            }

            binding.ratingText.setText("Rating: " + rating);
            // TODO
            // update rating
        });
    }

    public void setContent() {
        // TODO
        Recipe recipe = Recipe.createFromResources(getResources(), 1);
        rating = recipe.getRating();
        binding.breadcrumb.setText("HOME / RECIPES / " + recipe.getName().toUpperCase());

        binding.textHeading.setText(recipe.getName());
        binding.recipeDetails.setText(recipe.getDescription());
        binding.img1.setImageDrawable(recipe.getImg1());
        binding.img2.setImageDrawable(recipe.getImg2());
        binding.img3.setImageDrawable(recipe.getImg3());
        binding.authorText.setText("Author: " + recipe.getAuthor());
        binding.difficultyText.setText("Difficulty: " + recipe.getDifficulty());
        binding.ratingText.setText("Rating: " + recipe.getRating());

        // TODO
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Nina", "19/12/2022", "18:07", "Favourite recipe"));
        comments.add(new Comment("Ogi", "10/2/2022", "20:12", "My go to christmas recipe!"));

        CommentAdapter commentAdapter = new CommentAdapter(comments);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(commentAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
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