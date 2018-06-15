package cloud.dishwish.ragmart.dishwish.tasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.telecom.Call;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.classes.Recipe;
import cloud.dishwish.ragmart.dishwish.home.details.DetailsActivity;

public class GetRecipeDetailsTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private Recipe recipe;

    public GetRecipeDetailsTask(Context context, Recipe recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @Override
    protected String doInBackground(String... args0) {
        String result;

        try{

            String username = (args0[0].equals("null")) ? "" : args0[0];
            String password = (args0[1].equals("null")) ? "" : args0[1];
            String fbToken = (args0[2].equals("null")) ? "" : args0[2];
            String title = recipe.getName();

            //Da modificare
            String link = "https://www.dishwish.cloud/utility/GETDETAILS";

            String data  = URLEncoder.encode("UserEmail", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("FBToken", "UTF-8") + "=" +
                    URLEncoder.encode(fbToken, "UTF-8");
            data += "&" + URLEncoder.encode("Title", "UTF-8") + "=" +
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

            // Read Server Response
            while((line = reader.readLine()) != null) {

                sb.append(line);

                String [] myRecipe= line.split("\\|\\|");
                String author = myRecipe[0] + " " + myRecipe[1];
                String name = myRecipe[2];
                String course = myRecipe[3];
                String uri = "https://" + myRecipe[4];
                Bitmap picture = Bitmap.createScaledBitmap(new DownloadPicture().doInBackground(uri), 500,250,true);
                String process = myRecipe[5];

                //myRecipe[6] is string containing ingredient1#amount#measureUnity@&@ingredient2#amount#measureUnity...
                String [] ingredients = myRecipe[6].split("@&@");

                ArrayList<Ingredient> ings = new ArrayList<Ingredient>();

                for(int i = 0; i<ingredients.length; i++) {
                    String nameIngredient = ingredients[i].split("#")[0];
                    int amount = Integer.valueOf(ingredients[i].split("#")[1]);
                    String unityMeasure = ingredients[i].split("#")[2];

                    //In recipe ingredients' details the picture isn't needed
                    Bitmap pic = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.default_img), 1,1, false);

                    Ingredient ingredient = new Ingredient(nameIngredient, amount, pic);
                    ingredient.setMeasureUnity(unityMeasure);

                    ings.add(ingredient);
                }

                recipe.setAuthor(author);
                recipe.setName(name);
                recipe.setCourse(course);
                recipe.setImage(picture);
                recipe.setProcess(process);
                recipe.setIngredients(ings);
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

        if(result.contains("SUCCESS")) {

            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("recipeTitle", recipe.getName());
            intent.putExtra("recipeAuthor",recipe.getAuthor());
            intent.putExtra("recipeCourse",recipe.getCourse());
            intent.putExtra("recipePicture",recipe.getImage().toString());
            intent.putExtra("recipeProcess",recipe.getProcess());

            String ings = "";

            List<Ingredient> ingredients = recipe.getIngredients();

            for(int i = 0; i<ingredients.size(); i++) {
                ings += ingredients.get(i).getName() + "#"
                        + ingredients.get(i).getAmount() + "#"
                        + ingredients.get(i).getMeasureUnity();

                if(i != (ingredients.size()-1) || ingredients.size() == 1)
                    ings += "||";
            }

            intent.putExtra("recipeIngredients",ings);

            context.startActivity(intent);
        }
    }
}
