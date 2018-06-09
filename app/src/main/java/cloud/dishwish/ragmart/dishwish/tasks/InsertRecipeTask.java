package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import cloud.dishwish.ragmart.dishwish.classes.Recipe;

public class InsertRecipeTask extends AsyncTask<String, String, String> {

    private Context context;

    public InsertRecipeTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... args0) {

        String result = "";

        try{
            String author = args0[0];
            String name = args0[1];
            String process = args0[2];

            //Not implemented function
            String image = "";
            String course = args0[3];

            String link = "http://www.dishwish.cloud/utility/rec_in.php";

            String data  = URLEncoder.encode("Author", "UTF-8") + "=" +
                    URLEncoder.encode(author, "UTF-8");
            data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" +
                    URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("Process", "UTF-8") + "=" +
                    URLEncoder.encode(process, "UTF-8");
            data += "&" + URLEncoder.encode("Image", "UTF-8") + "=" +
                    URLEncoder.encode(image, "UTF-8");
            data += "&" + URLEncoder.encode("Course", "UTF-8") + "=" +
                    URLEncoder.encode(course, "UTF-8");
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
