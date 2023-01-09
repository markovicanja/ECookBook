package rs.ac.bg.ecookbook;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import rs.ac.bg.ecookbook.models.CommentModel;
import rs.ac.bg.ecookbook.models.RecipeModel;
import rs.ac.bg.ecookbook.models.UserModel;

public class Service {
    private static Service mInstance = null;
    private AsyncHttpClient mClient;
    private String mURL, mImagesURL;
    private UserModel mCurrentLoggedUser;
    private UserModel mCurrentSelectedUser;
    private RecipeModel mCurrentRecipe;

    private Service(){
        mClient = new AsyncHttpClient();
        mURL = "http://192.168.1.11:1234/";
        mImagesURL = "http://192.168.1.11:4200/assets/recipes/";
    }

    public static Service getInstance(){
        if(mInstance == null){
            mInstance = new Service();
        }
        return mInstance;
    }

    public UserModel getCurrentSelectedUser(){
        return mCurrentSelectedUser;
    }

    public void setCurrentSelectedUser(UserModel selectedUser){
        mCurrentSelectedUser = selectedUser;
    }

    public UserModel getLoggedUser(){
        return mCurrentLoggedUser;
    }

    public void loginUser(UserModel loggedUser){
        mCurrentLoggedUser = loggedUser;
    }

    public void logoutUser(){
        mCurrentLoggedUser = null;
    }

    public void setCurrentRecipe(RecipeModel recipeModel){
        mCurrentRecipe = recipeModel;
    }

    public RecipeModel getCurrentRecipe(){
        return mCurrentRecipe;
    }

    private RecipeModel createRecipe(ServiceSetter setter, JSONObject object){
        try {
            String name = object.getString("name");
            int difficulty = object.getInt("difficulty");
            String category = object.getString("category");
            String cuisine = object.getString("cuisine");
            String description = object.getString("description");
            String author = object.getString("author");
            int visibility = object.getInt("visibility");
            double rating = object.getDouble("rating");

            String img = object.getString("img");
            String filePath = setter.getContext().getExternalFilesDir(null).getPath() + File.separator;
            Drawable img1 = Drawable.createFromPath(filePath + img + "_1.jpg");
            Drawable img2 = Drawable.createFromPath(filePath + img + "_2.jpg");
            Drawable img3 = Drawable.createFromPath(filePath + img + "_3.jpg");

            return new RecipeModel(name, difficulty, category, cuisine, description, author, img1, img2, img3, rating, visibility);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class ImageTask extends AsyncTask<File, String, String> {
        private JSONArray mArray;
        private String mImagesURL;
        public ImageTask(JSONArray array, String imagesURL){
            mArray = array;
            mImagesURL = imagesURL;
        }

        @Override
        protected String doInBackground(File... filesDir) {
            String filePath = filesDir[0].getPath() + File.separator;
            try {
                for(int i = 0; i < mArray.length(); i++) {
                    String currentImage = (String)mArray.get(i);
                    File file = new File(filePath, currentImage);
                    if(file.exists()){
                        continue;
                    }

                    URL url = new URL(mImagesURL + currentImage);
                    InputStream in = new BufferedInputStream(url.openStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int n = in.read(buf);
                    while (n != -1) {
                        out.write(buf, 0, n);
                        n = in.read(buf);
                    }
                    out.close();
                    in.close();

                    byte[] response = out.toByteArray();

                    String filePathToSave = filePath + currentImage;
                    FileOutputStream fos = new FileOutputStream(filePathToSave);
                    fos.write(response);
                    fos.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return filePath;
        }
    }

    public void downloadImages(ServiceSetter setter){
        String url = mURL + "getAllImagesNames";
        mClient.get(setter.getContext(), url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    new ImageTask(response.getJSONArray("files"), mImagesURL).execute(setter.getContext().getExternalFilesDir(null));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginUser(ServiceSetter setter, String username, String password){
        String url = mURL + "loginUser";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", username);
            params.put("password", password);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 1) {
                        JSONObject object = response.getJSONObject("poruka");
                        mCurrentLoggedUser = new UserModel(object);
                        setter.setUserModel(mCurrentLoggedUser);

                        Toast.makeText(setter.getContext(), "You logged in.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(setter.getContext(), "Log in failed.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(setter.getContext(), "Log in failed.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void registerUser(ServiceSetter setter, String username, String password, String email){
        String url = mURL + "registerUser";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", username);
            params.put("password", password);
            params.put("email", email);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    String poruka = response.getString("poruka");
                    if(status == 1) {
                        mCurrentLoggedUser = new UserModel(username, password, email);
                        setter.setUserModel(mCurrentLoggedUser);
                    }

                    Toast.makeText(setter.getContext(), poruka, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(setter.getContext(), "Register failed.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void findUser(ServiceSetter setter, String username){
        String url = mURL + "findUser";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", username);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 1) {
                        String username = response.getString("username");
                        String email = response.getString("email");
                        String password = "";
                        mCurrentSelectedUser = new UserModel(username, password, email);

                        setter.userFound();
                    }
                } catch (JSONException e) {
                    Toast.makeText(setter.getContext(), "Register failed.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAllRecipes(ServiceSetter setter){
        String url = mURL + "getAllRecipes";
        mClient.get(setter.getContext(), url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        setter.setRecipes(new ArrayList<>());
                        return;
                    }
                    JSONArray array = response.getJSONArray("poruka");
                    ArrayList<RecipeModel> recipes = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        recipes.add(createRecipe(setter, object));
                    }
                    setter.setRecipes(recipes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAllRecipesByVisibility(ServiceSetter setter){
        String url = mURL + "getAllRecipesByVisibility";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        setter.setRecipes(new ArrayList<>());
                        return;
                    }
                    JSONArray array = response.getJSONArray("poruka");
                    ArrayList<RecipeModel> recipes = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        recipes.add(createRecipe(setter, object));
                    }
                    setter.setRecipes(recipes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAllSavedRecipes(ServiceSetter setter){
        String url = mURL + "getAllSavedRecipes";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        setter.setSavedRecipes(new ArrayList<>());
                        return;
                    }
                    JSONArray array = response.getJSONArray("poruka");
                    ArrayList<RecipeModel> recipes = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        recipes.add(createRecipe(setter, object));
                    }
                    setter.setSavedRecipes(recipes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAllRecommendedRecipes(ServiceSetter setter){
        String url = mURL + "getAllRecommendedRecipes";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        setter.setRecommendedRecipes(new ArrayList<>());
                        return;
                    }
                    JSONArray array = response.getJSONArray("poruka");
                    ArrayList<RecipeModel> recipes = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        recipes.add(createRecipe(setter, object));
                    }
                    setter.setRecommendedRecipes(recipes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getUserRecipes(ServiceSetter setter, String username){
        String url = mURL + "getUserRecipes";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", username);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        setter.setRecipes(new ArrayList<>());
                        return;
                    }
                    JSONArray array = response.getJSONArray("poruka");
                    ArrayList<RecipeModel> recipes = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        recipes.add(createRecipe(setter, object));
                    }
                    setter.setRecipes(recipes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getComments(ServiceSetter setter, String recipe){
        String url = mURL + "getComments";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("recipe", recipe);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        setter.setComments(new ArrayList<>());
                        return;
                    }
                    JSONArray array = response.getJSONArray("poruka");
                    ArrayList<CommentModel> comments = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        comments.add(new CommentModel(object.getString("author"), object.getString("date"),
                                object.getString("time"), object.getString("body")));
                    }
                    setter.setComments(comments);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void rateRecipe(ServiceSetter setter, String name, double rating) {
        String url = mURL + "rateRecipe";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("name", name);
            params.put("rating", rating);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 0) {
                        Toast.makeText(setter.getContext(), "Rating failed.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    setter.setRating(response.getDouble("poruka"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getFollowings(ServiceSetter setter){
        String url = mURL + "getFollowings";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 0){
                        setter.setFollowings(new ArrayList<>());
                        return;
                    }
                    JSONArray array = response.getJSONArray("poruka");
                    ArrayList<String> followings = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        String string = array.getString(i);
                        followings.add(string);
                    }
                    setter.setFollowings(followings);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getIsFollowing(ServiceSetter setter){
        String url = mURL + "getIsFollowing";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            params.put("following", mCurrentSelectedUser.getUsername());
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 1){
                        setter.setIsFollowing(true);
                    }
                    else{
                        setter.setIsFollowing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setIsFollowing(ServiceSetter setter){
        String url = mURL + "setIsFollowing";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            params.put("following", mCurrentSelectedUser.getUsername());
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 1){
                        setter.setIsFollowing(true);
                    }
                    else{
                        setter.setIsFollowing(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void unfollow(ServiceSetter setter){
        String url = mURL + "unfollow";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            params.put("following", mCurrentSelectedUser.getUsername());
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 1){
                        setter.setIsFollowing(false);
                    }
                    else{
                        setter.setIsFollowing(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void findIfUserSavedRecipe(ServiceSetter setter, String recipeName){
        String url = mURL + "findIfUserSavedRecipe";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            params.put("recipeName", recipeName);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if(status == 1){
                        setter.setUserSavedRecipe();
                        return;
                    }

                    Toast.makeText(setter.getContext(), "User didn't save the recipe.", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveRecipe(ServiceSetter setter, String recipeName) {
        String url = mURL + "saveRecipe";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            params.put("recipeName", recipeName);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 0) {
                        Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    setter.saveRecipe();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void removeSavedRecipe(ServiceSetter setter, String recipeName) {
        String url = mURL + "removeSavedRecipe";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", mCurrentLoggedUser.getUsername());
            params.put("recipeName", recipeName);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 0) {
                        Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    setter.removeSavedRecipe();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addComment(ServiceSetter setter, String recipe, String author, String date, String time, String body) {
        String url = mURL + "addComment";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("recipe", recipe);
            params.put("author", author);
            params.put("date", date);
            params.put("time", time);
            params.put("body", body);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 1) {
                        setter.addComment(author, date, time, body);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addRecipe(ServiceSetter setter, String name, int difficulty, String category, String cuisine, String description,
                          String author, String img, double rating, int visibility) {
        String url = mURL + "addRecipe";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("name", name);
            params.put("difficulty", difficulty);
            params.put("category", category);
            params.put("cuisine", cuisine);
            params.put("img", img);
            params.put("description", description);
            params.put("author", author);
            params.put("visibility", visibility);
            params.put("rating", rating);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(setter.getContext(), response.getString("poruka"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void changeVisiblity(ServiceSetter setter, String name, int visibility) {
        String url = mURL + "changeVisiblity";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("name", name);
            params.put("visibility", visibility);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 1) {
                        setter.setRecipeVisibility(visibility);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void recommendRecipe(ServiceSetter setter, String username, String usernameOfFollowing, String recipeName) {
        String url = mURL + "recommendRecipe";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", username);
            params.put("usernameOfFollowing", usernameOfFollowing);
            params.put("recipeName", recipeName);
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 1) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
