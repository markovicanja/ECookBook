package rs.ac.bg.ecookbook;

import android.content.res.Resources;

public class User {

    String username;
    String email;
    String password;

    public static User createFromResources(Resources resources, int index) {
        String[] usernames = resources.getStringArray(R.array.usernames);
        String[] emails = resources.getStringArray(R.array.emails);
        String[] passwords = resources.getStringArray(R.array.passswords);

        User result = new User(usernames[index], emails[index], passwords[index]);

        return result;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
