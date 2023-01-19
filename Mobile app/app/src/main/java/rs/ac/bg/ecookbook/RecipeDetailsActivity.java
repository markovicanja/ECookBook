package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import rs.ac.bg.ecookbook.databinding.ActivityRecipeDetailsBinding;
import rs.ac.bg.ecookbook.models.CommentModel;
import rs.ac.bg.ecookbook.models.RecipeModel;

public class RecipeDetailsActivity extends AppCompatActivity implements ServiceSetter {

    private ActivityRecipeDetailsBinding binding;
    private RecipeModel mRecipe;
    private CommentAdapter mCommentAdapter;

    private boolean alreadySaved = false;
    private ArrayList<String> mFollowings;

    private void toggleSaveRemoveButton() {
        if(alreadySaved){
            binding.saveButton.setText("SAVE");
        }
        else{
            binding.saveButton.setText("REMOVE");
        }
        alreadySaved = !alreadySaved;
    }

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

        binding.authorText.setOnClickListener(view -> {
            String newString = binding.authorText.getText().toString();
            newString = newString.substring(8);
            Service.getInstance().findUser(this, newString);
        });

        binding.buttonAddComment.setOnClickListener(v -> {
            Editable editable = Objects.requireNonNull(binding.addComment.getEditText()).getText();
            String commentBody = editable.toString();
            if("".equals(commentBody)) return;

            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime time = LocalDateTime.now();
            String dateString = formatterDate.format(time);
            String timeString = formatterTime.format(time);

            Service.getInstance().addComment(this, mRecipe.getName(), Service.getInstance().getLoggedUser().getUsername(), dateString, timeString, commentBody);
        });

        binding.saveButton.setOnClickListener(v -> {
            if(alreadySaved){
                Service.getInstance().removeSavedRecipe(this, mRecipe.getName());
            }
            else{
                Service.getInstance().saveRecipe(this, mRecipe.getName());
            }
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
                for (String user: mFollowings) {
                    if(username.equals(user)){
                        Service.getInstance().recommendRecipe(this, Service.getInstance().getLoggedUser().getUsername(), user, mRecipe.getName());
                        break;
                    }
                }
                dialog.dismiss();
            });

            dialog.show();
        });

        if(Service.getInstance().getLoggedUser().getUsername().equals(mRecipe.getAuthor())) {
            binding.visibilityButton.setOnClickListener(v -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.visibility_modal, null);

                alert.setView(view);

                AlertDialog dialog = alert.create();
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                Button changeVisibilityButton = view.findViewById(R.id.change_visibility_button);

                changeVisibilityButton.setOnClickListener(l -> {
                    RadioGroup radioButtonGroup = view.findViewById(R.id.radioGroup);
                    int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                    View radioButton = radioButtonGroup.findViewById(radioButtonID);
                    int visibility = radioButtonGroup.indexOfChild(radioButton);

                    Service.getInstance().changeVisiblity(this, mRecipe.getName(), visibility);

                    dialog.dismiss();
                });

                dialog.show();
            });
        }
        else{
            binding.visibilityButton.setEnabled(false);
        }

        binding.star1.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);

            Service.getInstance().rateRecipe(this, mRecipe.getName(), 1);
        });

        binding.star2.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);

            Service.getInstance().rateRecipe(this, mRecipe.getName(), 2);
        });

        binding.star3.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);

            Service.getInstance().rateRecipe(this, mRecipe.getName(), 3);
        });

        binding.star4.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_outline_24, 0, 0, 0);

            Service.getInstance().rateRecipe(this, mRecipe.getName(), 4);
        });

        binding.star5.setOnClickListener(v -> {
            binding.star1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);
            binding.star5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0);

            Service.getInstance().rateRecipe(this, mRecipe.getName(), 5);
        });
    }

    public void setContent() {
        mRecipe = Service.getInstance().getCurrentRecipe();

        binding.breadcrumb.setText("HOME / RECIPES / " + mRecipe.getName().toUpperCase());

        binding.textHeading.setText(mRecipe.getName());
        binding.recipeDetails.setText(mRecipe.getDescription());
        binding.img1.setImageDrawable(mRecipe.getImg1());
        binding.img2.setImageDrawable(mRecipe.getImg2());
        binding.img3.setImageDrawable(mRecipe.getImg3());
        binding.authorText.setText("Author: " + mRecipe.getAuthor());
        binding.difficultyText.setText("Difficulty: " + mRecipe.getDifficulty());
        binding.ratingText.setText("Rating: " + String.format("%.1f", mRecipe.getRating()));

        Service.getInstance().getComments(this, mRecipe.getName());
        Service.getInstance().getFollowings(this);
        Service.getInstance().findIfUserSavedRecipe(this, mRecipe.getName());
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

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setComments(ArrayList<CommentModel> comments) {
        mCommentAdapter = new CommentAdapter(comments);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(mCommentAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setRating(double rating){
        mRecipe.setRating(rating);
        binding.ratingText.setText("Rating: " + String.format("%.1f", mRecipe.getRating()));
    }

    @Override
    public void setFollowings(ArrayList<String> followings){
        mFollowings = followings;
    }

    @Override
    public void setUserSavedRecipe(){
        toggleSaveRemoveButton();
    }

    @Override
    public void saveRecipe(){
        toggleSaveRemoveButton();
    }

    @Override
    public void removeSavedRecipe(){
        toggleSaveRemoveButton();
    }

    @Override
    public void addComment(String author, String date, String time, String body){
        CommentModel commentModel = new CommentModel(author, date, time, body);
        mCommentAdapter.getComments().add(commentModel);
        mCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void userFound(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    public void setRecipeVisibility(int visibility){
        mRecipe.setVisibility(visibility);
    }
}