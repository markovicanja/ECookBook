package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import rs.ac.bg.ecookbook.databinding.ActivityAboutUsBinding;
import rs.ac.bg.ecookbook.databinding.ActivityMainBinding;

public class AboutUsActivity extends AppCompatActivity {

    private ActivityAboutUsBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
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
                    explicitIntent = new Intent(this, RecipesActivity.class);
                    startActivity(explicitIntent);
                    return true;
                case R.id.add_recipe_menu_item:
                    explicitIntent = new Intent(this, AddRecipeActivity.class);
                    startActivity(explicitIntent);
                    return true;
                case R.id.profile_menu_item:
                    // TODO
                    // return true;
                case R.id.about_menu_item:
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
                    explicitIntent = new Intent(this, RecipesActivity.class);
                    startActivity(explicitIntent);
                    return true;
                case R.id.about_menu_item:
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }
}