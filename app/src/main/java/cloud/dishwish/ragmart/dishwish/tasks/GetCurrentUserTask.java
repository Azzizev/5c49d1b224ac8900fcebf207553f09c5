package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.classes.Recipe;

import static android.content.Context.MODE_PRIVATE;

public class GetCurrentUserTask extends AsyncTask<String, Void ,String> {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;

    public GetCurrentUserTask(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("prefs",MODE_PRIVATE);
        editorPrefs = preferences.edit();
    }

    @Override
    protected String doInBackground(String... args0) {
        String result;

        try{

            String username = (args0[0].equals("null")) ? "" : args0[0];
            String password = (args0[1].equals("null")) ? "" : args0[1];
            String fbToken = (args0[2].equals("null")) ? "" : args0[2];

            String link = "https://www.dishwish.cloud/utility/udata.php";

            String data  = URLEncoder.encode("UserEmail", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("FBToken", "UTF-8") + "=" +
                    URLEncoder.encode(fbToken, "UTF-8");

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
            int i = 0;

            // Read Server Response
            while((line = reader.readLine()) != null) {

                sb.append(line);
                String [] myUser = line.split("\\|\\|");

                //Toast.makeText(context,line,Toast.LENGTH_SHORT).show();

                String email = myUser[0];
                String name = myUser[1];
                String surname = myUser[2];
                String picture = myUser[3];

                if(!picture.startsWith("http"))
                    picture = "https://" + myUser[3];

                editorPrefs.putString("currentUser", email);
                editorPrefs.putString("fbToken", fbToken);
                editorPrefs.putString("password", password);
                editorPrefs.putString("name", name);
                editorPrefs.putString("surname", surname);
                editorPrefs.putString("email", email);
                editorPrefs.putString("imageUrl", picture);

                editorPrefs.commit();
            }

            result = sb.toString();

            //result = getFavRecipes(username,password,fbToken);
        } catch (Exception e) {
            result = e + "";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context,"GetCurrentTask: " + result, Toast.LENGTH_SHORT).show();
    }
}
