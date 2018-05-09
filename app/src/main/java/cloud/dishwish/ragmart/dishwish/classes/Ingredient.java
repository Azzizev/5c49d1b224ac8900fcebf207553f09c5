package cloud.dishwish.ragmart.dishwish.classes;

import android.graphics.Bitmap;

public class Ingredient {

    private String name;
    private Bitmap picture;
    private int amount;

    public Ingredient(String name, int amount, Bitmap picture) {
        this.name = name;
        this.amount = amount;
        this.picture = picture;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
