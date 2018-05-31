package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.tasks.GetIngredientsTask;

public class FragAddIngredients extends Fragment {

    View view;
    private RecyclerView myRecycle;
    private List<Ingredient> ingredients;
    private SwipeRefreshLayout recyclerContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.new_recipe_ingredients,container, false);

        ingredients = GetIngredientsTask.ings;

        myRecycle = (RecyclerView) view.findViewById(R.id.ingredients_recycle);
        recyclerContainer = (SwipeRefreshLayout) view.findViewById(R.id.ingredients_recycler_container);

        myRecycle.setNestedScrollingEnabled(false);
        final RecyclerViewAdapterIng myAdapter = new RecyclerViewAdapterIng(getContext(),ingredients);
        myRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecycle.setAdapter(myAdapter);

        myRecycle.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                myAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
