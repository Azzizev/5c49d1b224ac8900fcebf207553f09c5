package cloud.dishwish.ragmart.dishwish.home.details;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView Picture;
    private CircleImageView profilePic;
    private TextView txtAuthor;
    private TextView txtTitle;
    private TextView txtCourse;
    private Button btnIngredients;
    private TextView txtProcess;
    private ListView myIngredients;
    private ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        ingredients = new ArrayList<Ingredient>();

        Picture = (ImageView) findViewById(R.id.details_recipe_image);
        profilePic = (CircleImageView) findViewById(R.id.details_recipe_profile_pic);
        txtAuthor = (TextView) findViewById(R.id.details_recipe_author);
        txtTitle = (TextView) findViewById(R.id.details_recipe_title);
        txtCourse = (TextView) findViewById(R.id.details_recipe_course);
        btnIngredients = (Button) findViewById(R.id.details_recipe_ingredients);
        txtProcess = (TextView) findViewById(R.id.details_recipe_process);

        Intent intent = getIntent();
        loadIngredientsFromString(intent.getStringExtra("recipeIngredients"));

        txtAuthor.setText(txtAuthor.getText() + ": " + intent.getStringExtra("recipeAuthor"));
        txtTitle.setText(txtTitle.getText() + ": " + intent.getStringExtra("recipeTitle"));
        txtCourse.setText(txtCourse.getText() + ": " + intent.getStringExtra("recipeCourse"));
        txtProcess.setText(txtProcess.getText() + ":\n " + intent.getStringExtra("recipeProcess"));

        btnIngredients.setOnClickListener(this);
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
                showIngredientsDialog();
                break;
        }
    }

    private void showIngredientsDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.details_ingredients);
        myIngredients = (ListView) dialog.findViewById(R.id.details_ingredients_list);

        IngredientListAdapter adapter = new IngredientListAdapter(this, ingredients);
        myIngredients.setAdapter(adapter);

        dialog.show();
    }

    protected class IngredientListAdapter extends BaseAdapter {

        private ArrayList<Ingredient> ingredients;
        private Context context;

        public IngredientListAdapter (Context context, ArrayList<Ingredient> ingredients) {
            this.context = context;
            this.ingredients = ingredients;
        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public Object getItem(int i) {
            return ingredients.get(i);
        }

        @Override
        public long getItemId(int i) {
            return ingredients.indexOf(ingredients.get(i));
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater myInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = myInflater.inflate(R.layout.details_ingredients_item, null);

            TextView name = (TextView) view.findViewById(R.id.details_ingredients_name);
            TextView amount = (TextView) view.findViewById(R.id.details_ingredients_amount);
            TextView unityMeasure = (TextView) view.findViewById(R.id.details_ingredients_measure_unity);

            name.setText(ingredients.get(i).getName());
            amount.setText(ingredients.get(i).getAmount() + "");
            unityMeasure.setText(ingredients.get(i).getMeasureUnity() + "");

            return view;
        }
    }
}
