package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.start.FragStart;

public class NewRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recipe_activity);
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

        Dialog ingredientsDialog = new Dialog(this);
        ingredientsDialog.setContentView(R.layout.new_recipe_ingredients);

        List<Ingredient> ingredients = new ArrayList<>();

        for(int i = 0; i<20; i++)
            ingredients.add(new Ingredient("Ingredient " + (i+1), (i*100), BitmapFactory.decodeResource(getResources(),R.drawable.icon_eye)));

        RecyclerView ingredientsRecycler = (RecyclerView) ingredientsDialog.findViewById(R.id.ingredients_recycle);
        RecyclerViewAdapterIng myAdapter = new RecyclerViewAdapterIng(this,ingredients);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
        ingredientsRecycler.setAdapter(myAdapter);

        ingredientsDialog.show();

    }
}
