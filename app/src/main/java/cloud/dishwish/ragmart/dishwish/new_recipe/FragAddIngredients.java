package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.classes.SectionsPageAdapter;

public class FragAddIngredients extends Fragment {

    View view;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private SectionsPageAdapter mSectionsPageAdapter;
    private FragAllIngredients fragAllIngredients;
    public static FragSelectedIngs fragSelectedIngs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.new_recipe_ingredients,container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.new_recipe_ingredients_container);
        tabLayout = (TabLayout) view.findViewById(R.id.ingredients_tabs);

        mSectionsPageAdapter = new SectionsPageAdapter(getChildFragmentManager());
        tabLayout.setupWithViewPager(mViewPager);
        setupViewPager(mViewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragSelectedIngs.myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        fragAllIngredients = new FragAllIngredients();
        fragSelectedIngs = new FragSelectedIngs();
        mSectionsPageAdapter.addFragment(fragAllIngredients,getResources().getString(R.string.ings_tab_all));
        mSectionsPageAdapter.addFragment(fragSelectedIngs,getResources().getString(R.string.ings_tab_selected));

        viewPager.setAdapter(mSectionsPageAdapter);
    }
}
