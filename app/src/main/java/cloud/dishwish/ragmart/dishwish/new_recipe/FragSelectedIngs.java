package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.tasks.GetIngredientsTask;

public class FragSelectedIngs extends Fragment{

    View view;
    public RecyclerView myRecycle;
    public static RV_AdapterSlcIngredients myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_recipe_ingredients_selected_fragment,container,false);

        myRecycle = (RecyclerView) view.findViewById(R.id.ingredients_selected_recycle);

        myAdapter = new RV_AdapterSlcIngredients(getContext(), NewRecipeActivity.selectedIngredients);
        myRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecycle.setAdapter(myAdapter);

        return view;
    }
}
