package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rs.ac.bg.ecookbook.databinding.ActivityMainBinding;
import rs.ac.bg.ecookbook.models.UserModel;

public class MainActivity extends AppCompatActivity implements ServiceSetter{

    private ActivityMainBinding binding;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("SP", 0);

        binding.loginButton.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.login_modal, null);

            alert.setView(view);

            AlertDialog dialog = alert.create();
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            EditText usernameText = view.findViewById(R.id.username_text);
            EditText passwordText = view.findViewById(R.id.password_text);
            Button loginButton = view.findViewById(R.id.login_button);

            loginButton.setOnClickListener(l -> {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                Service.getInstance().loginUser(this, username, password);

                dialog.dismiss();
            });

            dialog.show();
        });

        binding.registerButton.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.register_modal, null);

            alert.setView(view);

            EditText usernameText = view.findViewById(R.id.username_text);
            EditText emailText = view.findViewById(R.id.email_text);
            EditText passwordText = view.findViewById(R.id.password_text);
            EditText confirmPasswordText = view.findViewById(R.id.confirm_password_text);
            Button registerButton = view.findViewById(R.id.register_button);

            AlertDialog dialog = alert.create();
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.show();

            registerButton.setOnClickListener(l -> {
                String username = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();
                if(!password.equals(confirmPassword)){
                    Toast.makeText(this,
                            "Passwords do not match!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Service.getInstance().registerUser(this, username, password, email);

                dialog.dismiss();
            });
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
                    return true;
                case R.id.recipes_menu_item:
                    explicitIntent = new Intent(this, RecipesActivity.class);
                    startActivity(explicitIntent);
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

    @Override
    public void setUserModel(UserModel userModel){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged", true);
        editor.apply();

        invalidateOptionsMenu();
    }

    @Override
    public Context getContext() {
        return this;
    }
}