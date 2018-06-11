package cloud.dishwish.ragmart.dishwish.home;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.classes.Recipe;
import cloud.dishwish.ragmart.dishwish.classes.RV_AdapterAllRecipes;
import cloud.dishwish.ragmart.dishwish.tasks.GetIngredientsTask;
import cloud.dishwish.ragmart.dishwish.tasks.GetRecipesTask;

import static android.content.Context.MODE_PRIVATE;

public class FragHomePage extends Fragment implements AdapterView.OnItemSelectedListener{

    View view;
    private RecyclerView myRecyclerView;
    private Spinner recipeCategories;
    private List<Recipe> recipes;
    public static RV_AdapterAllRecipes recyclerViewAdapter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_recipes_fragment,container,false);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerView);
        recipeCategories = (Spinner) view.findViewById(R.id.home_categories_spinner);

        preferences = getActivity().getSharedPreferences("prefs",MODE_PRIVATE);
        editorPrefs = preferences.edit();

        recipes = GetRecipesTask.recs;

        recipes.add(new Recipe(preferences.getString("currentUser",""), "Ricetta boh",
                BitmapFactory.decodeResource(getResources(),R.drawable.lasagne),"Vai a caso","Contorno",Arrays.asList(new Ingredient("Latte", 1, BitmapFactory.decodeResource(getResources(),R.drawable.default_img)))));
        recyclerViewAdapter = new RV_AdapterAllRecipes(getContext(),recipes);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_recipe_categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        recipeCategories.setAdapter(adapter);

        recipeCategories.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getContext(),"Ciao mondo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
