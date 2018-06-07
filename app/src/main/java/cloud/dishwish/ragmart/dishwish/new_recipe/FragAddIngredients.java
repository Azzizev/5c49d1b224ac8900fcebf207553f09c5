package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.SectionsPageAdapter;
import cloud.dishwish.ragmart.dishwish.classes.TypefaceSpan;

public class FragAddIngredients extends Fragment {

    View view;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Toolbar toolBar;
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

        //Change application bar's family font
        SpannableString appName = new SpannableString(getResources().getString(R.string.app_name).toString());
        appName.setSpan(new TypefaceSpan(getContext(),"berkshire_swash.ttf"),0,appName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolBar = (Toolbar) view.findViewById(R.id.ingredients_toolBar);
        toolBar.setTitle(appName);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolBar);

        setHasOptionsMenu(true);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        fragAllIngredients = new FragAllIngredients();
        fragSelectedIngs = new FragSelectedIngs();
        mSectionsPageAdapter.addFragment(fragAllIngredients,getResources().getString(R.string.ings_tab_all));
        mSectionsPageAdapter.addFragment(fragSelectedIngs,getResources().getString(R.string.ings_tab_selected));

        viewPager.setAdapter(mSectionsPageAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_ingredients, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.menu_ingredients_add:
                getActivity().onBackPressed();
                break;
        }

        return true;
    }
}
