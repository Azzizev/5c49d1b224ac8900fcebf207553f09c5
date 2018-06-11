package cloud.dishwish.ragmart.dishwish.start;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import cloud.dishwish.ragmart.dishwish.home.HomeActivity;
import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.tasks.LoginTask;
import cloud.dishwish.ragmart.dishwish.tasks.SignupTask;

import static android.content.Context.MODE_PRIVATE;
import static cloud.dishwish.ragmart.dishwish.start.StartActivity.hideKeyboard;
import static com.facebook.FacebookSdk.getApplicationContext;

public class FragLogin extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    private CallbackManager mCallbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private static final String TAG = "FACELOG";
    private FirebaseAuth mAuth;
    private Button btnFBLogin;
    private Button btnLogin;
    private EditText txtUserEmail;
    private EditText txtPassword;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.start_login_fragment,container,false);

        btnFBLogin = (Button) view.findViewById(R.id.login_btnFBLogin);
        btnLogin = (Button) view.findViewById(R.id.login_btnLogin);
        txtUserEmail = (EditText) view.findViewById(R.id.login_txtUserEmail);
        txtPassword = (EditText) view.findViewById(R.id.login_txtPassword);


        preferences = getActivity().getSharedPreferences("prefs",MODE_PRIVATE);
        editorPrefs = preferences.edit();

        if(preferences.getString("currentUser","") != null)
            txtUserEmail.setText(preferences.getString("currentUser",""));

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken currentToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        txtUserEmail.setOnFocusChangeListener(this);
        txtPassword.setOnFocusChangeListener(this);

        btnFBLogin.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.login_btnLogin:
                onClickLogin(v);
                break;

            case R.id.login_btnFBLogin:
                onClickFacebookLogin(v);
                break;
        }
    }

    public void onClickFacebookLogin(View v) {

        btnFBLogin.setEnabled(false);

        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("email", "public_profile","user_friends"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Profile profile = Profile.getCurrentProfile();
                nextActivity(profile);
                Toast.makeText(getApplicationContext(),"Login...",Toast.LENGTH_SHORT).show();

                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    public void onClickLogin (View view) {

        String userEmail = txtUserEmail.getText().toString();
        String password = txtPassword.getText().toString();
        new LoginTask(getContext(), preferences, editorPrefs).execute(userEmail,password,"","","","","");
        getActivity().finish();
    }

    private void nextActivity(Profile profile) {

        if(profile != null && mAuth.getCurrentUser() != null) {

            Intent intent = new Intent(getContext(),HomeActivity.class);
            editorPrefs.putString("name",profile.getFirstName());
            editorPrefs.putString("surname",profile.getLastName());
            editorPrefs.putString("email",mAuth.getCurrentUser().getEmail());
            editorPrefs.putString("imageUrl",profile.getProfilePictureUri(150,150).toString());

            new SignupTask(getContext(), getActivity(),"FACEBOOK",preferences,editorPrefs)
                    .execute(profile.getFirstName(),profile.getLastName(),"00/00/0000","O",mAuth.getCurrentUser().getEmail(),"FCB_USR_PSW");
        }
    }

    @Override
    public void onStart() {

        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){

            updateUI(currentUser, preferences.getString("fbToken",""));
        }
    }

    private void updateUI(FirebaseUser currentUser, String token) {

        new LoginTask(getContext(),preferences,editorPrefs).execute("","",
                currentUser.getEmail(),
                token,
                Profile.getCurrentProfile().getFirstName(),
                Profile.getCurrentProfile().getLastName(),
                Profile.getCurrentProfile().getProfilePictureUri(150,150).toString());

        Intent intent = new Intent(getContext(),HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        Toast.makeText(getContext(),token.getUserId().toString().length() + "",Toast.LENGTH_LONG).show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            btnFBLogin.setEnabled(true);

                            editorPrefs.putString("fbToken",token.getUserId().toString());
                            editorPrefs.commit();
                            updateUI(user, token.getUserId().toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            btnFBLogin.setEnabled(true);

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus && !txtUserEmail.isFocused() && !txtPassword.isFocused())
            hideKeyboard(v);
    }
}

