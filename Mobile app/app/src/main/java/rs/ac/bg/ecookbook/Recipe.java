package rs.ac.bg.ecookbook;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

public class Recipe {

    private String name;
    private int difficulty;
    private String category;
    private String cuisine;
    private String description;
    private String author;
    private int rating;
    private Drawable img1;
    private Drawable img2;
    private Drawable img3;

    public static Recipe createFromResources(Resources resources, int index) {
        String[] names = resources.getStringArray(R.array.recipe_name);
        int[] difficulty = resources.getIntArray(R.array.recipe_difficulty);
        String[] categories = resources.getStringArray(R.array.recipe_category);
        String[] cuisines = resources.getStringArray(R.array.recipe_cuisine);
        String[] descriptions = resources.getStringArray(R.array.recipe_description);
        String[] authors = resources.getStringArray(R.array.recipe_author);
        int[] ratings = resources.getIntArray(R.array.recipe_rating);

        TypedArray images1 = resources.obtainTypedArray(R.array.recipe_img_1);
        TypedArray images2 = resources.obtainTypedArray(R.array.recipe_img_2);
        TypedArray images3 = resources.obtainTypedArray(R.array.recipe_img_3);

        Recipe result = new Recipe(names[index], difficulty[index], categories[index], cuisines[index],
                descriptions[index], authors[index], images1.getDrawable(index), images2.getDrawable(index),
                images3.getDrawable(index), ratings[index]);

        images1.recycle();
        images2.recycle();
        images3.recycle();

        return result;
    }

    public Recipe(String name, int difficulty, String category, String cuisine, String description,
                  String author, Drawable img1, Drawable img2, Drawable img3, int rating) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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
