package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;

import rs.ac.bg.ecookbook.databinding.ActivityRecipesBinding;
import rs.ac.bg.ecookbook.models.RecipeModel;

public class RecipesActivity extends AppCompatActivity implements ServiceSetter {

    private enum Categories{
        ALL, BREAKFAST, DINNER, DESSERT, SALAD
    }

    private static final HashMap<Integer, String> SORTLIST;
    static{
        SORTLIST = new HashMap<>();
        SORTLIST.put(0, "Difficulty asc");
        SORTLIST.put(1, "Difficulty desc");
        SORTLIST.put(2, "Rating asc");
        SORTLIST.put(3, "Rating desc");
    }

    private ActivityRecipesBinding binding;
    private SharedPreferences sharedPreferences;

    private CharSequence searchedQuery;
    private Categories currentCategory;
    private int sortListIndex;
    private ArrayList<RecipeModel> allRecipes, recipeModels;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("SP", 0);
        if(sharedPreferences.getBoolean("logged", false)){
            Service.getInstance().getAllRecipesByVisibility(this);
        }
        else{
            Service.getInstance().getAllRecipes(this);
        }
    }

    private void setContent() {
        if(recipeModels.isEmpty()) return; // TODO - Ovde bi verovatno trebala da bude logika u slucaju da nema recepata za prikaz

        binding.recipeImage.setImageDrawable(recipeModels.get(index).getImg1());
        String recipeDetails = recipeModels.get(index).getName() + " | Difficulty: "
                + recipeModels.get(index).getDifficulty() + " | Rating: "
                + String.format("%.1f", recipeModels.get(index).getRating());
        binding.recipeDetails.setText(recipeDetails);
    }

    private void resetButtonColors() {
        binding.allButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.breakfastButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.dinnerButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.dessertButton.setBackgroundColor(getResources().getColor(R.color.orange));
        binding.saladButton.setBackgroundColor(getResources().getColor(R.color.orange));
    }

    private void initSearch(){
        binding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchedQuery = charSequence.toString().toLowerCase();
                populateRecipes();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void initSortList() {
        ArrayList<String> sortList = new ArrayList<>();
        for(int i = 0; i < SORTLIST.size(); i++){
            sortList.add(SORTLIST.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(RecipesActivity.this,
                android.R.layout.simple_spinner_item,
                sortList);

        binding.sortText.setAdapter(adapter);
        binding.sortText.setOnItemClickListener((adapterView, view, j, l) -> {
            sortListIndex = j;
            populateRecipes();
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

    private void populateRecipes(){
        search();
        sortByCategory();
        sortByNum();
        index = 0;

        setContent();
    }

    private void search(){
        this.recipeModels = new ArrayList<>();
        if(searchedQuery.toString().equals("")){
            this.recipeModels.addAll(this.allRecipes);
        }
        else {
            for (int ind = 0; ind < allRecipes.size(); ind++) {
                String stringToLower = allRecipes.get(ind).getName().toLowerCase();
                if (stringToLower.contains(searchedQuery)) {
                    recipeModels.add(allRecipes.get(ind));
                }
            }
        }
    }

    private void sortByCategory(){
        String category = "";
        switch (currentCategory){
            case ALL:
                return;
            case BREAKFAST:
                category = "Breakfast";
                break;
            case DINNER:
                category = "Dinner";
                break;
            case DESSERT:
                category = "Dessert";
                break;
            case SALAD:
                category = "Salad";
                break;
        }
        for(int i = 0; i < recipeModels.size(); i++){
            if(!recipeModels.get(i).getCategory().equals(category)) {
                recipeModels.remove(i--);
            }
        }
    }

    private void sortByNum(){
        if(sortListIndex == -1) return;
        switch(sortListIndex){
            case 0:
                Collections.sort(recipeModels, (recipeModel, t1) -> recipeModel.getDifficulty() - t1.getDifficulty());
                break;
            case 1:
                Collections.sort(recipeModels, (recipeModel, t1) -> t1.getDifficulty() - recipeModel.getDifficulty());
                break;
            case 2:
                Collections.sort(recipeModels, (recipeModel, t1) -> (int)(recipeModel.getRating() - t1.getRating()));
                break;
            case 3:
                Collections.sort(recipeModels, (recipeModel, t1) -> (int)(t1.getRating() - recipeModel.getRating()));
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setRecipes(ArrayList<RecipeModel> recipes) {
        this.allRecipes = recipes;
        searchedQuery = "";
        currentCategory = Categories.ALL;
        sortListIndex = -1;
        populateRecipes();

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

        initSearch();
        initSortList();

        binding.allButton.setOnClickListener(v -> {
            currentCategory = Categories.ALL;
            populateRecipes();
            resetButtonColors();
            binding.allButton.setBackgroundColor(getResources().getColor(R.color.green));
        });

        binding.breakfastButton.setOnClickListener(v -> {
            currentCategory = Categories.BREAKFAST;
            populateRecipes();
            resetButtonColors();
            binding.breakfastButton.setBackgroundColor(getResources().getColor(R.color.green));
        });

        binding.dinnerButton.setOnClickListener(v -> {
            currentCategory = Categories.DINNER;
            populateRecipes();
            resetButtonColors();
            binding.dinnerButton.setBackgroundColor(getResources().getColor(R.color.green));
        });

        binding.dessertButton.setOnClickListener(v -> {
            currentCategory = Categories.DESSERT;
            populateRecipes();
            resetButtonColors();
            binding.dessertButton.setBackgroundColor(getResources().getColor(R.color.green));
        });

        binding.saladButton.setOnClickListener(v -> {
            currentCategory = Categories.SALAD;
            populateRecipes();
            resetButtonColors();
            binding.saladButton.setBackgroundColor(getResources().getColor(R.color.green));
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
}