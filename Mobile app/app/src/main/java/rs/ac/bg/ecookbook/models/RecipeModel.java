package rs.ac.bg.ecookbook.models;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    private String name, category, cuisine, img, description, author;
    private int difficulty, visibility;
    private double rating;

    public RecipeModel(JSONObject object){
        try {
            name = object.getString("name");
            difficulty = object.getInt("difficulty");
            category = object.getString("category");
            cuisine = object.getString("cuisine");
            img = object.getString("img");
            description = object.getString("description");
            author = object.getString("author");
            visibility = object.getInt("visibility");
            rating = object.getDouble("rating");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
