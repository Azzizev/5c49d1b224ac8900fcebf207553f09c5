package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.classes.Recipe;
import cloud.dishwish.ragmart.dishwish.home.HomeActivity;

public class InsertRecipeTask extends AsyncTask<String, String, String> {

    private Context context;
    private ArrayList<Ingredient> ingredients;
    private String result = "";
    private String username;
    private String password;
    private String fbToken;
    private String author;
    private String title;
    private String process;
    private String course;

    public InsertRecipeTask(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public String doInBackground(String... args0) {

        try{
            username = args0[0];
            password = args0[1];
            fbToken = args0[2];
            author = args0[3];
            title = args0[4];
            process = args0[5];
            course = args0[6];

            if(args0.length > 7)
                result = insertFavRecipe(username,password,fbToken,author, title,context,process,course,ingredients);
            else {

                course = getCourseITA(course);

                String ings = "";

                for (int i = 0; i < ingredients.size(); i++) {
                    ings += ingredients.get(i).getName() + "#"
                            + ingredients.get(i).getAmount() + "#"
                            + ingredients.get(i).getMeasureUnity();

                    if (i != (ingredients.size() - 1) || ingredients.size() == 1)
                        ings += "||";
                }
                //Not implemented function
                String image = "";

                if (!fbToken.isEmpty())
                    password = "";

                String link = "https://www.dishwish.cloud/utility/ircp";

                String data = URLEncoder.encode("UserEmail", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode("FBToken", "UTF-8") + "=" +
                        URLEncoder.encode(fbToken, "UTF-8");
                data += "&" + URLEncoder.encode("Author", "UTF-8") + "=" +
                        URLEncoder.encode(author, "UTF-8");
                data += "&" + URLEncoder.encode("Title", "UTF-8") + "=" +
                        URLEncoder.encode(title, "UTF-8");
                data += "&" + URLEncoder.encode("Process", "UTF-8") + "=" +
                        URLEncoder.encode(process, "UTF-8");
                data += "&" + URLEncoder.encode("Image", "UTF-8") + "=" +
                        URLEncoder.encode(image, "UTF-8");
                data += "&" + URLEncoder.encode("Category", "UTF-8") + "=" +
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
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                result = sb.toString();

                if (result.contains("SUCCESS")) {
                    GetRecipesTask.allRecs.add(new Recipe(author, title, BitmapFactory.decodeResource(context.getResources(), R.drawable.default_img), process, course, ingredients));
                }
            }
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.contains("done")) {
            GetRecipesTask.favRecs.add(new Recipe(author, title, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.default_img),500,250,false), process, course, ingredients));
            GetRecipesTask.getSelectedFavRecipes(GetRecipesTask.favRecs, course);
            HomeActivity.fragFavoriteRecipes.favRecyclerViewAdapter.notifyDataSetChanged();
        }
        else if(result.contains("SUCCESS")) {
            HomeActivity.fragHomePage.recyclerViewAdapter.notifyDataSetChanged();
            context.startActivity(new Intent(context, HomeActivity.class));
        }
        else
            Toast.makeText(context,"Error: " + result,Toast.LENGTH_SHORT).show();
    }

    private String insertFavRecipe(String username, String password, String fbToken,
                                         String author, String title, Context context, String process,
                                         String course, ArrayList<Ingredient> ingredients) {

        String result;

        try{

            if(!fbToken.isEmpty()) {
                password = "";
                username = "";
            }

            String link = "https://www.dishwish.cloud/utility/ifvr";

            String data = URLEncoder.encode("UserEmail", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("FBToken", "UTF-8") + "=" +
                    URLEncoder.encode(fbToken, "UTF-8");
            data += "&" + URLEncoder.encode("Title", "UTF-8") + "=" +
                    URLEncoder.encode(title, "UTF-8");

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
            }

            result = sb.toString();

            result = (result.contains("SUCCESS")) ? "done" : "ERR";
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }

        return result;
    }

    /**
     * Method to convert the course string in order the make it suitable for the server-side
     * @param course contains a the course written in italian
     * @return
     */
    private String getCourseITA(String course){

        String myCourse = "";

        if(course.equals("Antipasti"))
            myCourse = "ATP";
        else if(course.equals("Contorni"))
            myCourse = "CTN";
        else if(course.equals("Dolci"))
            myCourse = "DLC";
        else if(course.equals("Primi"))
            myCourse = "PRM";
        else if(course.equals("Secondi"))
            myCourse = "SCD";

        return myCourse;
    }

    public String getResult() { return result; }
}
