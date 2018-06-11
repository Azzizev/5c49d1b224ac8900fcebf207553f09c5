package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.classes.Ingredient;

public class GetIngredientsTask extends AsyncTask<String, Void, String> {

    private Context context;
    public static ArrayList<Ingredient> ings;

    public GetIngredientsTask(Context context)
    {
        this.context = context;
        ings = new ArrayList<Ingredient>();
    }

    @Override
    protected String doInBackground(String... strings) {

        try
        {
            String link = "https://www.dishwish.cloud/utility/grd.php";

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write("");
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {

                result.append(line);

                //Toast.makeText(context,line,Toast.LENGTH_SHORT).show();
                String name = line.split("@&@")[0];

                int amount = 0;
                String uri = line.split("@&@")[1];
                Bitmap picture = Bitmap.createScaledBitmap(new DownloadPicture().doInBackground(uri), 50,50,true);

                ings.add(new Ingredient(name,amount,picture));
            }

            return result.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
    }
}
