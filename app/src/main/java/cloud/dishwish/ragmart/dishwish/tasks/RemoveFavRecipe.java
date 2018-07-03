package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import cloud.dishwish.ragmart.dishwish.classes.Recipe;
import cloud.dishwish.ragmart.dishwish.home.HomeActivity;

public class RemoveFavRecipe extends AsyncTask<String, String, String> {

    private Context context;
    private ArrayList<Recipe> favRecs;
    private Recipe recipe;

    public RemoveFavRecipe(Context context, ArrayList<Recipe> favRecs, Recipe recipe) {
        this.context = context;
        this.favRecs = favRecs;
        this.recipe = recipe;
    }

    @Override
    protected String doInBackground(String... args0) {

        String result;

        try{
            String username = args0[0];
            String password = args0[1];
            String fbToken = args0[2];
            String title = recipe.getName();

            if(!fbToken.isEmpty()) {
                username = "";
                password = "";
            }

            String link = "https://www.dishwish.cloud/utility/rfvr";

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

        } catch (Exception e) {

            result = "Exception: " + e;
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.contains("SUCCESS")) {
            int i = findRecipeIndex(GetRecipesTask.favRecs, recipe);
            if(i >= 0) {
                GetRecipesTask.favRecs.remove(i);
            }
            GetRecipesTask.getSelectedFavRecipes(GetRecipesTask.favRecs, recipe.getCourse());
            HomeActivity.fragFavoriteRecipes.favRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Error: " + result, Toast.LENGTH_SHORT).show();
        }
    }

    public int findRecipeIndex(ArrayList<Recipe> recipes, Recipe recipe) {

        int index = -1;

        for(int i = 0; i<recipes.size(); i++) {

            if(recipes.get(i).getName().equals(recipe.getName()))
                index = i;
        }

        return index;
    }
}
