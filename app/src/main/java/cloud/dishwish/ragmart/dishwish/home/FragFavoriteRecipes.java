package cloud.dishwish.ragmart.dishwish.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import cloud.dishwish.ragmart.dishwish.R;

public class FragFavoriteRecipes extends Fragment{

    private static final String TAG = "Fragment2";

    private Button btnTest;

    public FragFavoriteRecipes(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_favorite_recipes_fragment,container,false);

        btnTest = (Button) view.findViewById(R.id.test_button2);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Test 2",Toast.LENGTH_LONG).show();
            }
        });
        
        return view;
    }
}
