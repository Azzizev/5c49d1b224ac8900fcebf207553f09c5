package cloud.dishwish.ragmart.dishwish.classes;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Recipe {

    private int id;
    private String name;

    //Da cambiare tipo
    private int image;

    private String process;
    private List<String> ingredients;

    public Recipe(int id, String name, int image, String process, List<String> ingredients) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.process = process;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
