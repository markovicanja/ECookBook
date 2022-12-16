package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import rs.ac.bg.ecookbook.databinding.ActivityRecipesBinding;

public class RecipesActivity extends AppCompatActivity {

    private ActivityRecipesBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("SP", 0);

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

        initSortList();

        binding.allButton.setOnClickListener(v -> {
            // TODO
            resetButtonColors();
            binding.allButton.setBackgroundColor(getResources().getColor(R.color.green));
        });

        binding.breakfastButton.setOnClickListener(v -> {
            // TODO
            resetButtonColors();
            binding.breakfastButton.setBackgroundColor(getResources().getColor(R.color.green));
        });

        binding.dinnerButton.setOnClickListener(v -> {
            // TODO
            resetButtonColors();
            binding.dinnerButton.setBackgroundColor(getResources().getColor(R.color.green));
        });

        binding.dessertButton.setOnClickListener(v -> {
            // TODO
            resetButtonColors();
            binding.dessertButton.setBackgroundColor(getResources().getColor(R.color.green));
        });

        binding.saladButton.setOnClickListener(v -> {
            // TODO
            resetButtonColors();
            binding.saladButton.setBackgroundColor(getResources().getColor(R.color.green));
        });

        binding.arrowLeft.setOnClickListener(v -> {
            // TODO
        });

        binding.arrowRight.setOnClickListener(v -> {
            // TODO
        });

        binding.recipeImage.setOnClickListener(v -> {
            // TODO
        });

        binding.recipeDetails.setOnClickListener(v -> {
            // TODO
        });
    }

    private void resetButtonColors() {
        binding.allButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.breakfastButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.dinnerButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.dessertButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.saladButton.setBackgroundColor(getResources().getColor(R.color.orange));
    }

    private void initSortList() {
        ArrayList<String> sortList = new ArrayList<>();
        sortList.add("Difficulty asc");
        sortList.add("Difficulty desc");
        sortList.add("Rating asc");
        sortList.add("Rating desc");

        ArrayAdapter<String> adapter = new ArrayAdapter(RecipesActivity.this,
                android.R.layout.simple_spinner_item,
                sortList);

        binding.sortText.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        if (sharedPreferences.getBoolean("logged", false)) {
            inflater.inflate(R.menu.menu, menu);
        }
        else {
            inflater.inflate(R.menu.guest_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent explicitIntent;
        if (sharedPreferences.getBoolean("logged", false)) {
            switch(item.getItemId()){
                case R.id.home_menu_item:
                    explicitIntent = new Intent(this, MainActivity.class);
                    startActivity(explicitIntent);
                    return true;
                case R.id.recipes_menu_item:
                    return true;
                case R.id.add_recipe_menu_item:
                    // TODO
                    // return true;
                case R.id.profile_menu_item:
                    // TODO
                    // return true;
                case R.id.about_menu_item:
                    explicitIntent = new Intent(this, AboutUsActivity.class);
                    startActivity(explicitIntent);
                    return true;
                case R.id.logout_menu_item:
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("logged", false);
                    editor.apply();
                    invalidateOptionsMenu();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        else {
            switch(item.getItemId()){
                case R.id.home_menu_item:
                    explicitIntent = new Intent(this, MainActivity.class);
                    startActivity(explicitIntent);
                    return true;
                case R.id.recipes_menu_item:
                    return true;
                case R.id.about_menu_item:
                    explicitIntent = new Intent(this, AboutUsActivity.class);
                    startActivity(explicitIntent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }
}