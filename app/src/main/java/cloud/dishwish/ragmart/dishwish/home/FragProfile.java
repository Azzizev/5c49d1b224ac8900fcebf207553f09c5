package cloud.dishwish.ragmart.dishwish.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.tasks.DownloadPicture;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class FragProfile extends Fragment{

    private static final String TAG = "Fragment3";

    private String name;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;
    private CircleImageView profilePic;
    private TextView txtName;
    private TextView txtEmail;

    public FragProfile(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_profile_fragment,container,false);

        profilePic = (CircleImageView)view.findViewById(R.id.profile_picture);
        preferences = getActivity().getSharedPreferences("prefs",MODE_PRIVATE);
        txtName = (TextView)view.findViewById(R.id.profile_name);
        txtEmail = (TextView)view.findViewById(R.id.profile_email);

        txtName.setText(txtName.getText() + ": " + preferences.getString("name","") + " " + preferences.getString("surname",""));
        txtEmail.setText(txtEmail.getText() + ": " + preferences.getString("email",""));

        //Toast.makeText(getActivity(),preferences.getString("imageUrl",""),Toast.LENGTH_LONG).show();
        new DownloadPicture(profilePic).execute(preferences.getString("imageUrl",""));

        return view;
    }
}
