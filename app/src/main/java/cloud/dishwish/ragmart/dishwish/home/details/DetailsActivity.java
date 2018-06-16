package cloud.dishwish.ragmart.dishwish.home.details;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView txtPicture;
    private TextView txtAuthor;
    private TextView txtTitle;
    private TextView txtCourse;
    private TextView txtIngredients;
    private TextView txtProcess;
    private ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        ingredients = new ArrayList<Ingredient>();

        txtPicture = (ImageView) findViewById(R.id.details_recipe_image);
        txtAuthor = (TextView) findViewById(R.id.details_recipe_author);
        txtTitle = (TextView) findViewById(R.id.details_recipe_title);
        txtCourse = (TextView) findViewById(R.id.details_recipe_course);
        txtIngredients = (TextView) findViewById(R.id.details_recipe_ingredients);
        txtProcess = (TextView) findViewById(R.id.details_recipe_process);

        Intent intent = getIntent();

        txtAuthor.setText(intent.getStringExtra("recipeAuthor"));
        txtTitle.setText(intent.getStringExtra("recipeTitle"));
        txtCourse.setText(intent.getStringExtra("recipeCourse"));
        txtProcess.setText(intent.getStringExtra("recipeProcess"));

        txtIngredients.setOnClickListener(this);


    }

    private void loadIngredientsFromString(String ings) {

        String [] data = ings.split("\\|\\|");

        for(String element: data) {
            String name = element.split("#")[0];
            int amount = Integer.parseInt(element.split("#")[1]);
            String measureUnity = element.split("#")[2];

            ingredients.add(new Ingredient(name,amount, measureUnity, this));
        }
    }
    
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.details_recipe_ingredients:
                showRadioButtonDialog();
                break;
        }
    }

    private void showRadioButtonDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.details_ingredients);

        ListView myIngredients = (ListView) findViewById(R.id.details_ingredients_list);

        dialog.show();
    }
}
