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
import java.net.URLEncoder;
import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.classes.Recipe;
import cloud.dishwish.ragmart.dishwish.home.HomeActivity;

public class GetRecipesTask extends AsyncTask<String, Void, String> {

    private Context context;
    public static ArrayList<Recipe> allRecs;
    public static ArrayList<Recipe> favRecs;
    public static ArrayList<Recipe> seletectedRecs;
    public static ArrayList<Recipe> seletectedFavRecs;

    public GetRecipesTask(Context context) {
        this.context = context;
        this.allRecs = new ArrayList<Recipe>();
        this.favRecs = new ArrayList<Recipe>();
        this.seletectedRecs = new ArrayList<Recipe>();
        this.seletectedFavRecs = new ArrayList<Recipe>();
    }

    @Override
    protected String doInBackground(String... args0) {

        String result;

        try{

            String username = (args0[0].equals("null")) ? "" : args0[0];
            String password = (args0[1].equals("null")) ? "" : args0[1];
            String fbToken = (args0[2].equals("null")) ? "" : args0[2];

            String link = "https://www.dishwish.cloud/utility/rcp";

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
                String [] myRecipe = line.split("\\|\\|");

                //Toast.makeText(context,line,Toast.LENGTH_SHORT).show();

                String author = myRecipe[0] + " " + myRecipe[1];
                String name = myRecipe[2];
                String course = myRecipe[3];
                String uri = "https://" + myRecipe[4];
                Bitmap picture = Bitmap.createScaledBitmap(new DownloadPicture().doInBackground(uri), 500,250,true);
                String process = "";

                allRecs.add(new Recipe(author,name,picture,process,course, new ArrayList<Ingredient>()));

                i++;
            }

            result = sb.toString();

            result = getFavRecipes(username,password,fbToken);

        } catch (Exception e) {
            result = e + "";
        }
        return result;
    }

    protected String getFavRecipes(String username, String password, String fbToken) {

        String result;

        try{

            String link = "https://www.dishwish.cloud/utility/fvr";

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
                String [] myRecipe = line.split("\\|\\|");

                //Toast.makeText(context,line,Toast.LENGTH_SHORT).show();

                String author = myRecipe[0] + " " + myRecipe[1];
                String name = myRecipe[2];
                String course = myRecipe[3];
                String uri = "https://" + myRecipe[4];
                Bitmap picture = Bitmap.createScaledBitmap(new DownloadPicture().doInBackground(uri), 500,250,true);
                String process = "";

                favRecs.add(new Recipe(author,name,picture,process,course, new ArrayList<Ingredient>()));

                i++;
            }

            result = sb.toString();

        } catch (Exception e) {
            result = e + "";
        }

        return result;
    }

    public static void getSelectedRecipes(ArrayList<Recipe> recs, String course) {

        seletectedRecs.clear();

        for(Recipe recipe: recs) {

            if(recipe.getCourse().equals(course))
                seletectedRecs.add(recipe);
        }

        if(HomeActivity.fragHomePage.recyclerViewAdapter != null)
            HomeActivity.fragHomePage.recyclerViewAdapter.notifyDataSetChanged();
    }

    public static void getSelectedFavRecipes(ArrayList<Recipe> recs, String course) {

        seletectedFavRecs.clear();

        for(Recipe recipe: recs) {

            if(recipe.getCourse().equals(course))
                seletectedFavRecs.add(recipe);
        }

        HomeActivity.fragFavoriteRecipes.favRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(String result) {

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        if(!result.contains("ERR"))
            getSelectedRecipes(allRecs, "Antipasti");

        if(HomeActivity.fragHomePage.recyclerViewAdapter != null)
            HomeActivity.fragHomePage.recyclerViewAdapter.notifyDataSetChanged();

        if(HomeActivity.fragFavoriteRecipes.favRecyclerViewAdapter != null)
            HomeActivity.fragFavoriteRecipes.favRecyclerViewAdapter.notifyDataSetChanged();
    }
}
