package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.tasks.GetIngredientsTask;
import cloud.dishwish.ragmart.dishwish.tasks.InsertRecipeTask;

public class NewRecipeActivity extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {

    private EditText txtTitle;
    private Button btnCreate;
    private EditText txtProcess;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;
    private Spinner recipeCategories;
    public static EditText txtAddIngredient;
    public static ArrayList<Ingredient> selectedIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recipe_activity);

        preferences = getSharedPreferences("prefs",MODE_PRIVATE);
        editorPrefs = preferences.edit();

        selectedIngredients = new ArrayList<Ingredient>();

        txtTitle = (EditText) findViewById(R.id.new_recipe_title);
        txtAddIngredient = (EditText) findViewById(R.id.new_recipe_ingredients);
        btnCreate = (Button) findViewById(R.id.new_recipe_create);
        txtProcess = (EditText) findViewById(R.id.new_recipe_process);

        txtTitle.setOnFocusChangeListener(this);
        txtProcess.setOnFocusChangeListener(this);
        txtAddIngredient.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_recipe_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        recipeCategories.setAdapter(adapter);

        recipeCategories.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.new_recipe_ingredients:
                onClickAddIngredients();
                break;
            case R.id.new_recipe_create:
                onClickCreate();
                break;
        }
    }

    private void onClickCreate() {

        String username = preferences.getString("currentUser","");
        String password = preferences.getString("password","");
        String fbToken = preferences.getString("fbToken","");
        String author = preferences.getString("Name","");
        String title = txtTitle.getText().toString();
        String process = txtProcess.getText().toString();
        String course = (String) recipeCategories.getSelectedItem();

        InsertRecipeTask insertion = new InsertRecipeTask(this, selectedIngredients);

        insertion.execute(username, password, fbToken, author, title, process, course);

        if(insertion.doInBackground().contains("SUCCESS"))
            onBackPressed();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getId()) {
            case R.id.new_recipe_title:
                checkTitle();
                break;
            case R.id.new_recipe_process:
                checkProcess(txtProcess.getText().toString());
                break;
        }
    }

    private void checkTitle() {
        if(!checkCharOnly(txtTitle.getText().toString()))
            showTitleError();
        else
            dismissTitleError();
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

    public boolean checkProcess(String process){

        boolean verifyProcess = true;

        for(int i = 0; i<process.length(); i++)
        {
            char c = process.charAt(i);

            if(c == '@')
                verifyProcess = false;
        }

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

    private void showTitleError(){
        txtTitle.setTextColor(getResources().getColor(R.color.colorRed));
        txtTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_alert, 0);
    }

    private void dismissTitleError() {
        txtTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        txtTitle.setTextColor(getResources().getColor(R.color.colorText));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
