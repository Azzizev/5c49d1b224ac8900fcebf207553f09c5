package cloud.dishwish.ragmart.dishwish.classes;

import android.graphics.Bitmap;

import java.util.List;

public class Recipe {

    private int id;
    private String name;

    //Da cambiare tipo
    private Bitmap image;

    private String author;
    private String process;
    private String course;
    private List<String> ingredients;

    public Recipe(String author, int id, String name, Bitmap image, String process, String course, List<String> ingredients) {
        this.author = author;
        this.id = id;
        this.name = name;
        this.image = image;
        this.process = process;
        this.course = course;
        this.ingredients = ingredients;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Bitmap getImage() { return image; }

    public void setImage(Bitmap image) {
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

    public boolean compareTo(Object o) {

        Recipe recipe = (Recipe) o;

        return (recipe.getName().equals(getName()) && recipe.getId() == getId()
                && recipe.getImage().equals(getImage()) && recipe.getIngredients().equals(getIngredients())
                && recipe.getProcess().equals(getProcess()));
    }
}
