package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cloud.dishwish.ragmart.dishwish.classes.Ingredient;

public class GetIngredients extends AsyncTask<String, Void, String> {

    private Context context;
    public static List<Ingredient> ings;

    public GetIngredients(Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        try
        {
            String link = "http://www.dishwish.cloud/utility/ing.php";

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null || !line.equals("")) {

                String name = line.split("#")[0];
                int amount = 0;
                String uri = line.split("#")[1];
                Bitmap picture = new DownloadPicture().doInBackground(uri);

                ings.add(new Ingredient(name,amount,picture));
            }

            return null;
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }
}
