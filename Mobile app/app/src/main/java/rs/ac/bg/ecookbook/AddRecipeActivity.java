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
import android.widget.Toast;

import rs.ac.bg.ecookbook.databinding.ActivityAddRecipeBinding;

public class AddRecipeActivity extends AppCompatActivity implements ServiceSetter {

    private ActivityAddRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.addButton.setOnClickListener(v -> {
            String name = binding.nameText.getText().toString();
            int difficulty = 0;
            try {
                difficulty = Integer.parseInt(binding.difficultyText.getText().toString());
            } catch(NumberFormatException e) {
                Toast.makeText(this, "Invalid data", Toast.LENGTH_LONG).show();
                return;
            }
            String category = binding.categoryText.getText().toString();
            String cuisine = binding.cuisineText.getText().toString();
            String imgURL = binding.imageText.getText().toString();
            String description = binding.descriptionText.getText().toString();

            if("".equals(name) || "".equals(category) || "".equals(cuisine) || "".equals(imgURL) || "".equals(description)){
                Toast.makeText(this, "Morate popuniti sva polja.", Toast.LENGTH_LONG).show();
            }
            else{
                Service.getInstance().addRecipe(this, name, difficulty, category, cuisine, description,
                        Service.getInstance().getLoggedUser().getUsername(), imgURL, 3, 0);
            }
        });
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

    @Override
    public Context getContext() {
        return this;
    }
}