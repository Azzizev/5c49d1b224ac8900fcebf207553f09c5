package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import cloud.dishwish.ragmart.dishwish.home.HomeActivity;
import cloud.dishwish.ragmart.dishwish.start.StartActivity;

public class SignupTask extends AsyncTask <String, Integer, String>{

    private Context context;
    private String currentUser;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;
    private String signupMethod;

    public SignupTask(Context context, String signupMethod, SharedPreferences preferences, SharedPreferences.Editor editor) {

        this.context = context;
        this.preferences = preferences;
        this.editorPrefs = editor;
        this.signupMethod = signupMethod;
    }

    @Override
    protected String doInBackground(String... arg0) {

        try{
            String name =  arg0[0];
            String surname =  arg0[1];
            String birthDate = arg0[2].split("/")[2] + "/" + arg0[2].split("/")[1] + "/" + arg0[2].split("/")[0]; //Changes date format 'DD/MM/YYYY' to 'YYYY/MM/DD'
            String gender = arg0[3];
            String userEmail = currentUser = arg0[4];
            String password = arg0[5];

            String link="http://www.dishwish.cloud/signin/reg.php";

            String data  = URLEncoder.encode("Name", "UTF-8") + "=" +
                    URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("Surname", "UTF-8") + "=" +
                    URLEncoder.encode(surname, "UTF-8");
            data += "&" + URLEncoder.encode("BirthDate", "UTF-8") + "=" +
                    URLEncoder.encode(birthDate, "UTF-8");
            data += "&" + URLEncoder.encode("Gender", "UTF-8") + "=" +
                    URLEncoder.encode(gender, "UTF-8");
            data += "&" + URLEncoder.encode("UserEmail", "UTF-8") + "=" +
                    URLEncoder.encode(userEmail, "UTF-8");
            data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return sb.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result){

        Toast.makeText(context,result,Toast.LENGTH_LONG).show();

        if(result.contains("SUCCESS")) {

            editorPrefs.putString("currentUser",currentUser);
            editorPrefs.commit();

            if(FirebaseAuth.getInstance() != null)
                FirebaseAuth.getInstance().signOut();

            if(LoginManager.getInstance() != null)
                LoginManager.getInstance().logOut();

            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);

        } else if(signupMethod.equals("FACEBOOK")){
            editorPrefs.putString("currentUser",currentUser);
            editorPrefs.commit();

            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }
        else {
            editorPrefs.putString("currentUser","");
            editorPrefs.commit();
        }
    }
}