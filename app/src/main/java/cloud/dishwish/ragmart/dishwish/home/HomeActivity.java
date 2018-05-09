package cloud.dishwish.ragmart.dishwish.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.SectionsPageAdapter;
import cloud.dishwish.ragmart.dishwish.classes.TypefaceSpan;
import cloud.dishwish.ragmart.dishwish.new_recipe.NewRecipeActivity;
import cloud.dishwish.ragmart.dishwish.start.StartActivity;

public class HomeActivity extends AppCompatActivity {

    private Toolbar topToolBar;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private static final String TAG = "HomeActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_activity);

        Log.d(TAG, "onCreate: Starting.");
        mViewPager = (ViewPager) findViewById(R.id.container);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        setupViewPager(mViewPager);


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_red);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_favoriterec_grey);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_profile_grey);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){

                    case 0:
                        tab.setIcon(R.drawable.ic_home_red);
                        break;

                    case 1:
                        tab.setIcon(R.drawable.ic_favoriterec_red);
                        break;

                    case 2:
                        tab.setIcon(R.drawable.ic_profile_red);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                switch (tab.getPosition()){

                    case 0:
                        tab.setIcon(R.drawable.ic_home_grey);
                        break;

                    case 1:
                        tab.setIcon(R.drawable.ic_favoriterec_grey);
                        break;

                    case 2:
                        tab.setIcon(R.drawable.ic_profile_grey);
                        break;
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //Change application bar's family font
        SpannableString appName = new SpannableString(getResources().getString(R.string.app_name).toString());
        appName.setSpan(new TypefaceSpan(this,"berkshire_swash.ttf"),0,appName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        topToolBar = (Toolbar) findViewById(R.id.home_toolBar);
        topToolBar.setTitle(appName);
        setSupportActionBar(topToolBar);

        preferences = getSharedPreferences("prefs",MODE_PRIVATE);
        editorPrefs = preferences.edit();

        mAuth = FirebaseAuth.getInstance();
    }

    private void setupViewPager(ViewPager viewPager) {

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragHomePage(),"");
        adapter.addFragment(new FragFavoriteRecipes(),"");
        adapter.addFragment(new FragProfile(),"");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int res_id = item.getItemId();

        switch (res_id) {
            case  R.id.menu_search:
                Toast.makeText(getApplicationContext(),"Search selected unavailable",Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_new_recipe:
                onClickNewRecipe();
                break;
            case R.id.menu_contact:
                Toast.makeText(getApplicationContext(),"Contact us selected unavailable",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_logout:
                onClickLogout();
                break;
        }
        return true;
    }

    private void onClickLogout() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        updateUI();
    }

    private void onClickNewRecipe(){
        Intent intent = new Intent(HomeActivity.this, NewRecipeActivity.class);
        startActivity(intent);
    }
    @Override
    public void onStart() {

        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null && preferences.getString("currentUser","") == null){
            updateUI();
        }
        else if(currentUser == null)
            Toast.makeText(this,"Welcome " + preferences.getString("currentUser",""),Toast.LENGTH_LONG).show();
    }

    private void updateUI() {

        Toast.makeText(this, "See ya", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(HomeActivity.this,StartActivity.class);
        startActivity(intent);
        finish();
    }
}
