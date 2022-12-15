package rs.ac.bg.ecookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import rs.ac.bg.ecookbook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static boolean logged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

                // TODO
                Toast.makeText(this, username + " " + password, Toast.LENGTH_LONG).show();
                logged = true;

                invalidateOptionsMenu();
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

            registerButton.setOnClickListener(l -> {
                String username = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();

                // TODO
                Toast.makeText(this,
                        username + ", " + email + ", " + password + ", " + confirmPassword,
                        Toast.LENGTH_LONG).show();
            });

            AlertDialog dialog = alert.create();
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        if (logged) {
            inflater.inflate(R.menu.menu, menu);
        }
        else {
            inflater.inflate(R.menu.guest_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (logged) {
            switch(item.getItemId()){
                case R.id.home_menu_item:
                    // TODO
                    return true;
                case R.id.recipes_menu_item:
                    // TODO
                    // return true;
                case R.id.add_recipe_menu_item:
                    // TODO
                    // return true;
                case R.id.profile_menu_item:
                    // TODO
                    // return true;
                case R.id.about_menu_item:
                    // TODO
                    // return true;
                case R.id.logout_menu_item:
                    // TODO
                    // return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        else {
            switch(item.getItemId()){
                case R.id.home_menu_item:
                    // TODO
                    return true;
                case R.id.recipes_menu_item:
                    // TODO
                    // return true;
                case R.id.about_menu_item:
                    // TODO
                    // return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }
}