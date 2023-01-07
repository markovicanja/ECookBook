package rs.ac.bg.ecookbook;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import rs.ac.bg.ecookbook.models.RecipeModel;
import rs.ac.bg.ecookbook.models.UserModel;

public class Service {
    private static Service mInstance = null;
    private AsyncHttpClient mClient;
    private String mURL;

    private Service(){
        mClient = new AsyncHttpClient();
        mURL = "http://192.168.1.11:1234/";
    }

    public static Service getInstance(){
        if(mInstance == null){
            mInstance = new Service();
        }
        return mInstance;
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
                        UserModel userModel = new UserModel(object);
                        setter.setUserModel(userModel);

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
                        UserModel userModel = new UserModel(username, password, email);
                        setter.setUserModel(userModel);
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

    public void getAllRecipesByVisibility(ServiceSetter setter){
        String url = mURL + "getAllRecipesByVisibility";
        JSONObject params = new JSONObject();
        StringEntity entity = null;
        try {
            params.put("username", "ogi");
            entity = new StringEntity(params.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mClient.post(setter.getContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("poruka");
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        RecipeModel recipe = new RecipeModel(object);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(setter.getContext(), "Request made successfully.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Toast.makeText(setter.getContext(), "Request failed.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
