package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.tasks.GetIngredientsTask;

public class FragAllIngredients extends Fragment {

    View view;
    private RecyclerView myRecycle;
    private ArrayList<Ingredient> ingredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.new_recipe_ingredients_all_fragment,container,false);

        myRecycle = (RecyclerView) view.findViewById(R.id.ingredients_recycle);

        ingredients = GetIngredientsTask.ings;
        RV_AdapterAllIngredients myAdapter = new RV_AdapterAllIngredients(getContext(),ingredients, NewRecipeActivity.selectedIngredients);
        myRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecycle.setAdapter(myAdapter);

        return view;
    }
}
