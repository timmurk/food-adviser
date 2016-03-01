package foodadviser.com.foodadviser;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    public void setName(String name) {
        this.name = name;
    }

    public Recipe() {}

    private String name;

    public void setIngridients(ArrayList<String> ingridients) {
        this.ingridients = ingridients;
    }

    public ArrayList<String> getIngridients() {
        return ingridients;
    }

    private ArrayList<String> ingridients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    private String imageURL;

    public void setRank(double rank) {
        this.rank = rank;
    }

    private double rank;

    public Recipe(String name,  String imageURL, double rank) {
        this.name = name;
        this.imageURL = imageURL;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImageURL() {
        return imageURL;
    }

    public double getRank() {
        return rank;
    }

    public String toString() {
        return "Recipe: " + name;
    }
}
