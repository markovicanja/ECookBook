package rs.ac.bg.ecookbook;

import android.content.Context;

import rs.ac.bg.ecookbook.models.UserModel;

public interface ServiceSetter {
    Context getContext();

    default void setUserModel(UserModel userModel){}
}
