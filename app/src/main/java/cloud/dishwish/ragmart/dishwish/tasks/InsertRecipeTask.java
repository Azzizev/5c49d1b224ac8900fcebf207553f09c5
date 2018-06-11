package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.classes.Recipe;

public class InsertRecipeTask extends AsyncTask<String, String, String> {

    private Context context;
    private ArrayList<Ingredient> ingredients;

    public InsertRecipeTask(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public String doInBackground(String... args0) {

        String result = "";

        try{
            String username = args0[0];
            String password = args0[1];
            String fbToken = args0[2];
            String author = args0[3];
            String name = args0[4];
            String process = args0[5];
            String course = args0[6];

            String ings = "";

            for(int i = 0; i<ingredients.size(); i++) {
                ings += ingredients.get(i).getName() + "#"
                        + ingredients.get(i).getAmount() + "#"
                        + ingredients.get(i).getMeasureUnity() + "@&@";
            }
            //Not implemented function
            String image = "";

            String link = "https://www.dishwish.cloud/utility/rec_in.php";

            String data = URLEncoder.encode("UserEmail", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += URLEncoder.encode("Password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
            data += URLEncoder.encode("FBToken", "UTF-8") + "=" +
                    URLEncoder.encode(fbToken, "UTF-8");
            data += URLEncoder.encode("Author", "UTF-8") + "=" +
                    URLEncoder.encode(author, "UTF-8");
            data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" +
                    URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("Process", "UTF-8") + "=" +
                    URLEncoder.encode(process, "UTF-8");
            data += "&" + URLEncoder.encode("Image", "UTF-8") + "=" +
                    URLEncoder.encode(image, "UTF-8");
            data += "&" + URLEncoder.encode("Course", "UTF-8") + "=" +
                    URLEncoder.encode(course, "UTF-8");
            data += "&" + URLEncoder.encode("Ingredients", "UTF-8") + "=" +
                    URLEncoder.encode(ings, "UTF-8");

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

            result = sb.toString();

        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }

        return result;
    }
}
