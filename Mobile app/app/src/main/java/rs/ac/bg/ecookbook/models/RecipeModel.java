package rs.ac.bg.ecookbook.models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class RecipeModel implements Serializable {

    private String name;
    private int difficulty;
    private String category;
    private String cuisine;
    private String description;
    private String author;
    private double rating;
    private int visibility;
    private Drawable img1;
    private Drawable img2;
    private Drawable img3;

    public RecipeModel(String name, int difficulty, String category, String cuisine, String description,
                       String author, Drawable img1, Drawable img2, Drawable img3, double rating, int visibility) {
        this.name = name;
        this.difficulty = difficulty;
        this.category = category;
        this.cuisine = cuisine;
        this.description = description;
        this.author = author;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.rating = rating;
        this.visibility = visibility;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Drawable getImg1() {
        return img1;
    }

    public void setImg1(Drawable img1) {
        this.img1 = img1;
    }

    public Drawable getImg2() {
        return img2;
    }

    public void setImg2(Drawable img2) {
        this.img2 = img2;
    }

    public Drawable getImg3() {
        return img3;
    }

    public void setImg3(Drawable img3) {
        this.img3 = img3;
    }
}
