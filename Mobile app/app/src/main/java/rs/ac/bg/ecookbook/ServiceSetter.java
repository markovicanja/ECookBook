package rs.ac.bg.ecookbook;

import android.content.Context;

import java.util.ArrayList;

import rs.ac.bg.ecookbook.models.CommentModel;
import rs.ac.bg.ecookbook.models.RecipeModel;
import rs.ac.bg.ecookbook.models.UserModel;

public interface ServiceSetter {
    Context getContext();

    default void setUserModel(UserModel userModel){}
    default void setRecipes(ArrayList<RecipeModel> recipeModels){}
    default void setSavedRecipes(ArrayList<RecipeModel> recipeModels){}
    default void setRecommendedRecipes(ArrayList<RecipeModel> recipeModels){}
    default void setComments(ArrayList<CommentModel> comments){}
    default void setRating(double rating){}
    default void setFollowings(ArrayList<String> followings){}
    default void setIsFollowing(boolean isFollowing){}
    default void setUserSavedRecipe(){}
    default void saveRecipe(){}
    default void removeSavedRecipe(){}
    default void addComment(String author, String date, String time, String body){}
    default void setRecipeVisibility(int visibility){}
    default void userFound(){}
}
