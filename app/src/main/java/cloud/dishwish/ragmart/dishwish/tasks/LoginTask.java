package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.facebook.Profile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import cloud.dishwish.ragmart.dishwish.home.HomeActivity;
import cloud.dishwish.ragmart.dishwish.start.StartActivity;

/**
 * Created by Ayrton on 28/03/2018.
 */

public class LoginTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;
    private String username;
    private String password;
    private String fbUsername;
    private String fbToken;
    private String fbName;
    private String fbSurname;
    private String fbPicture;

    public LoginTask(Context context, SharedPreferences preferences, SharedPreferences.Editor editor) {
        this.context = context;
        this.preferences = preferences;
        this.editorPrefs = preferences.edit();
    }

    @Override
    protected String doInBackground(String... arg0) {

        try{
            username = arg0[0];
            password = arg0[1];
            fbUsername = arg0[2];
            fbToken = arg0[3];
            fbName = arg0[4];
            fbSurname = arg0[5];
            fbPicture = arg0[6];

            String link;
            String data;

            if(username.isEmpty() && password.isEmpty()) {
                link = "https://www.dishwish.cloud/signin/fblog.php";
                data  = URLEncoder.encode("FBEmail", "UTF-8") + "=" +
                        URLEncoder.encode(fbUsername, "UTF-8");
                data += "&" + URLEncoder.encode("FBToken", "UTF-8") + "=" +
                        URLEncoder.encode(fbToken, "UTF-8");
                data += "&" + URLEncoder.encode("FBName", "UTF-8") + "=" +
                        URLEncoder.encode(fbName, "UTF-8");
                data += "&" + URLEncoder.encode("FBSurname", "UTF-8") + "=" +
                        URLEncoder.encode(fbSurname, "UTF-8");
                data += "&" + URLEncoder.encode("FBPicture", "UTF-8") + "=" +
                        URLEncoder.encode(fbPicture, "UTF-8");
            }
            else {
                link = "https://www.dishwish.cloud/signin/log.php";
                data  = URLEncoder.encode("UserEmail", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");
            }

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

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        if(result.contains("SUCCESS")) {


            if(!fbUsername.isEmpty() && !fbToken.isEmpty()) {
                new GetRecipesTask(context).execute(username,password,"null");
                editorPrefs.putString("currentUser", username);
                editorPrefs.putString("password", password);
                editorPrefs.putString("imageUrl", "null");
            } else {
                new GetRecipesTask(context).execute("null","null",fbToken);
                editorPrefs.putString("currentUser",fbUsername);
                editorPrefs.putString("fbToken",fbToken);
                editorPrefs.putString("name", fbName);
                editorPrefs.putString("surname",fbSurname);
                editorPrefs.putString("email",fbUsername);
                editorPrefs.putString("imageUrl",fbPicture);
            }

            editorPrefs.commit();

            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, StartActivity.class);
            context.startActivity(intent);
        }
    }
}
