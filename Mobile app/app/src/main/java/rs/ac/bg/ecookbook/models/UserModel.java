package rs.ac.bg.ecookbook.models;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModel {

    private String username, password, email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserModel(JSONObject object){
        try {
            username = object.getString("username");
            password = object.getString("password");
            email = object.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UserModel(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
