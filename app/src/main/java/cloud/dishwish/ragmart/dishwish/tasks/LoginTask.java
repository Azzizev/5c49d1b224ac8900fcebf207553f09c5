package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

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
    private String currentUser;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;

    public LoginTask(Context context, SharedPreferences preferences, SharedPreferences.Editor editor) {
        this.context = context;
        this.preferences = preferences;
        this.editorPrefs = preferences.edit();
    }

    @Override
    protected String doInBackground(String... arg0) {

        try{
            String username = currentUser = arg0[0];
            String password =  arg0[1];

            String link = "http://www.dishwish.cloud/signin/log.php";

            String data  = URLEncoder.encode("UserEmail", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
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

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        if(result.contains("SUCCESS")) {

            editorPrefs.putString("currentUser",currentUser);
            editorPrefs.putString("imageUrl","null");
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
