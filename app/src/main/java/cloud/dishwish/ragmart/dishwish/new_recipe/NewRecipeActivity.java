package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.tasks.GetIngredientsTask;

public class NewRecipeActivity extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener {

    private EditText txtTitle;
    private Button btnCreate;
    private EditText txtProcess;
    public static EditText txtAddIngredient;
    public static ArrayList<Ingredient> selectedIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recipe_activity);

        selectedIngredients = new ArrayList<Ingredient>();

        txtTitle = (EditText) findViewById(R.id.new_recipe_title);
        txtAddIngredient = (EditText) findViewById(R.id.new_recipe_ingredients);
        btnCreate = (Button) findViewById(R.id.new_recipe_create);
        txtProcess = (EditText) findViewById(R.id.new_recipe_process);

        txtTitle.setOnFocusChangeListener(this);
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    private void onClickAddIngredients() {

        Bundle bundle = new Bundle();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,
                R.anim.bottom_in,R.anim.fade_out);
        fragmentTransaction.add(R.id.new_recipe_container, new FragAddIngredients());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean checkTitle(String title){

        return checkCharOnly(title);
    }

    public boolean checkProcess(String process){

        boolean verifyProcess = true;

        return verifyProcess;

    }

    /**
     * Checks is the EditText contains a name
     * @param string EditText that refers to the user's name
     * @return true if the name contains only characters
     */
    public boolean checkCharOnly(String string) {

        string = string.toLowerCase();

        boolean verification = true;

        for(int i = 0; i<string.length();i++) {

            if(string.charAt(i)<'a' || string.charAt(i)>'z')
                verification = false;
        }

        return verification;
    }
}
