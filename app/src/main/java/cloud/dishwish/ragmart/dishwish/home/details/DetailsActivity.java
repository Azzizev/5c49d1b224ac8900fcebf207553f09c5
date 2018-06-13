package cloud.dishwish.ragmart.dishwish.home.details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cloud.dishwish.ragmart.dishwish.R;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView txtPicture;
    private TextView txtAuthor;
    private TextView txtTitle;
    private TextView txtCourse;
    private TextView txtIngredients;
    private TextView txtProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

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

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.details_recipe_ingredients:
                onClickIngredients();
                break;
        }
    }

    public void onClickIngredients(){

    }
}
