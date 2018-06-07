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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.RV_AdapterAllRecipes;
import cloud.dishwish.ragmart.dishwish.classes.Recipe;

public class FragFavoriteRecipes extends Fragment implements AdapterView.OnItemSelectedListener{

    View view;
    public static RecyclerView myRecyclerView;
    private Spinner recipeCategories;
    public static ArrayList<Recipe> favRecipes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_favorite_recipes_fragment,container,false);

        favRecipes = new ArrayList<Recipe>();

        myRecyclerView = (RecyclerView) view.findViewById(R.id.fav_recyclerView);
        recipeCategories = (Spinner) view.findViewById(R.id.fav_categories_spinner);

        RV_AdapterAllRecipes recyclerViewAdapter = new RV_AdapterAllRecipes(getContext(),favRecipes);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(),"Ciao mondo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
