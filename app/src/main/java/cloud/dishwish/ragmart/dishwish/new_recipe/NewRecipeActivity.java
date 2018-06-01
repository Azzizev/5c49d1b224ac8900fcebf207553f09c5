package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.tasks.GetIngredientsTask;

public class NewRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private Fragment fragIngredients;
    private EditText txtAddIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recipe_activity);

        txtAddIngredient = (EditText) findViewById(R.id.new_recipe_ingredients);

        txtAddIngredient.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.new_recipe_ingredients:
                onClickAddIngredients();
                break;
        }
    }

    private void onClickAddIngredients() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,
                R.anim.bottom_in,R.anim.fade_out);
        fragmentTransaction.add(R.id.new_recipe_container, new FragAddIngredients());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
