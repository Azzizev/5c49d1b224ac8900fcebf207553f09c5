package cloud.dishwish.ragmart.dishwish.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import cloud.dishwish.ragmart.dishwish.R;

public class Ingredient{

    private String name;
    private Bitmap picture;
    private int amount;
    private String measureUnity;

    public Ingredient(String name, int amount, Bitmap picture) {
        this.name = name;
        this.amount = amount;
        this.picture = picture;
        this.measureUnity = "";
    }

    public Ingredient(String name, int amount, String measureUnity, Context context) {
        this.name = name;
        this.amount = amount;
        this.picture = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.default_img),1,1, false);
        this.measureUnity = measureUnity;
    }

    public String getMeasureUnity() {
        return measureUnity;
    }

    public void setMeasureUnity(String measureUnity) {
        this.measureUnity = measureUnity;
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

    public boolean compareTo(Object o) {

        Ingredient ingredient = (Ingredient) o;

        return (ingredient.getName().equals(getName()) && ingredient.getAmount() == getAmount()
                && ingredient.getPicture().equals(getPicture()));
    }
}
