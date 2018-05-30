package cloud.dishwish.ragmart.dishwish.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Recipe;
import cloud.dishwish.ragmart.dishwish.classes.RecyclerViewAdapter;

public class FragHomePage extends Fragment{

    View view;
    private RecyclerView myRecyclerView;
    private List<Recipe> recipes;

    public FragHomePage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_recipes_fragment,container,false);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),recipes);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipes = new ArrayList<>();

        for(int i = 0; i< 20; i++)
            recipes.add(new Recipe(i,"Lasagna: " + (i+1), R.drawable.lasagne,"", Arrays.asList("Carne","Olio")));
    }
}
