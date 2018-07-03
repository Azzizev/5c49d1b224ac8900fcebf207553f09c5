package cloud.dishwish.ragmart.dishwish.start;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cloud.dishwish.ragmart.dishwish.home.HomeActivity;
import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.tasks.GetRecipesTask;

import static android.content.Context.MODE_PRIVATE;

public class FragStart extends Fragment implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private Fragment fragment;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.start_fragment,container,false);

        Button btnLogin = view.findViewById(R.id.start_btnLogin);
        Button btnSignUp = view.findViewById(R.id.start_btnSignUp);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        editorPrefs = preferences.edit();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();

        switch (id)
        {
            case R.id.start_btnLogin:
                fragment = new FragLogin();
                fragTransaction.setCustomAnimations(R.anim.bottom_in,R.anim.fade_out,R.anim.fade_in,R.anim.bottom_out);
                break;

            case R.id.start_btnSignUp:
                fragment = new FragSignup();
                fragTransaction.setCustomAnimations(R.anim.bottom_in,R.anim.fade_out,R.anim.fade_in,R.anim.bottom_out);
                break;
        }

        fragTransaction.replace(R.id.start_fragment,fragment);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();
    }

    @Override
    public void onStart() {

        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){

            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser currentUser) {

        editorPrefs.putString("currentUser",currentUser.getEmail());
        editorPrefs.putString("name", Profile.getCurrentProfile().getFirstName());
        editorPrefs.putString("surname",Profile.getCurrentProfile().getLastName());
        editorPrefs.putString("email",currentUser.getEmail());
        editorPrefs.putString("imageUrl",Profile.getCurrentProfile().getProfilePictureUri(150,150).toString());
        editorPrefs.commit();

        new GetRecipesTask(getContext()).execute("null","null",preferences.getString("fbToken",""));

        //Toast.makeText(getContext(), preferences.getString("fbToken",""), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getContext(),HomeActivity.class);
        startActivity(intent);
    }
}

