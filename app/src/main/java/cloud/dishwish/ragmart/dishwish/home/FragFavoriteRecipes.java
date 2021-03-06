package cloud.dishwish.ragmart.dishwish.home;

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

import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.RV_AdapterAllRecipes;
import cloud.dishwish.ragmart.dishwish.classes.Recipe;
import cloud.dishwish.ragmart.dishwish.tasks.GetRecipesTask;

public class FragFavoriteRecipes extends Fragment implements AdapterView.OnItemSelectedListener{

    View view;
    private RecyclerView myRecyclerView;
    private Spinner recipeCategories;
    public static ArrayList<Recipe> favRecipes;
    public static RV_AdapterAllRecipes favRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_favorite_recipes_fragment,container,false);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.fav_recyclerView);
        recipeCategories = (Spinner) view.findViewById(R.id.fav_categories_spinner);

        favRecipes = GetRecipesTask.seletectedFavRecs;
        favRecyclerViewAdapter = new RV_AdapterAllRecipes(getContext(), favRecipes);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(favRecyclerViewAdapter);

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getContext(),"Ciao mondo", Toast.LENGTH_SHORT).show();

        String course = recipeCategories.getSelectedItem().toString();
        if(GetRecipesTask.favRecs != null && GetRecipesTask.favRecs.size() > 0) {
            GetRecipesTask.getSelectedFavRecipes(GetRecipesTask.favRecs,course);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
