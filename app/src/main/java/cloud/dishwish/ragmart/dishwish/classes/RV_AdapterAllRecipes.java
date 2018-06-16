package cloud.dishwish.ragmart.dishwish.classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.home.FragFavoriteRecipes;
import cloud.dishwish.ragmart.dishwish.home.FragHomePage;
import cloud.dishwish.ragmart.dishwish.home.details.DetailsActivity;
import cloud.dishwish.ragmart.dishwish.tasks.GetRecipeDetailsTask;
import cloud.dishwish.ragmart.dishwish.tasks.GetRecipesTask;
import cloud.dishwish.ragmart.dishwish.tasks.InsertRecipeTask;

import static android.content.Context.MODE_PRIVATE;

public class RV_AdapterAllRecipes extends RecyclerView.Adapter<RV_AdapterAllRecipes.MyViewHolder> {

    Context myContext;
    List<Recipe> recipeList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editorPrefs;
    private String username;
    private String password;
    private String fbToken;

    public RV_AdapterAllRecipes(Context myContext, List<Recipe> recipeList) {
        this.myContext = myContext;
        this.recipeList = recipeList;
        preferences = myContext.getSharedPreferences("prefs",MODE_PRIVATE);
        editorPrefs = preferences.edit();
        username = preferences.getString("currentUser","");
        password = preferences.getString("password","");
        fbToken = preferences.getString("fbToken","");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(myContext).inflate(R.layout.home_recipes_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final String author = recipeList.get(position).getAuthor();
        final String title = recipeList.get(position).getName();
        final String process = recipeList.get(position).getProcess();
        final String course = recipeList.get(position).getCourse();
        final ArrayList<Ingredient> ingredients = recipeList.get(position).getIngredients();
        Bitmap picture = recipeList.get(position).getImage();

        holder.txtNameRecipe.setText(title);
        holder.imageRecipe.setImageBitmap(picture);

        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetRecipeDetailsTask(myContext, recipeList.get(position)).execute(username,password,fbToken);
            }
        });

        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.isPresent(FragFavoriteRecipes.favRecipes)){
                    FragFavoriteRecipes.favRecipes.remove(recipeList.get(position));
                    FragFavoriteRecipes.myRecyclerView.getAdapter().notifyDataSetChanged();
                    holder.setFavIcon(R.drawable.ic_favoriterec_grey);
                } else {

                    new InsertRecipeTask(myContext, ingredients).execute(username,password,fbToken,author,title,process,course,"insertFavRec");

                        //FragFavoriteRecipes.favRecipes.add(recipeList.get(position));
                        FragFavoriteRecipes.myRecyclerView.getAdapter().notifyDataSetChanged();
                        holder.setFavIcon(R.drawable.ic_favoriterec_red);
                }
            }
        });

        if(holder.isPresent(FragFavoriteRecipes.favRecipes)) {
            holder.setFavIcon(R.drawable.ic_favoriterec_red);
        } else {
            holder.setFavIcon(R.drawable.ic_favoriterec_grey);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageRecipe;
        private TextView txtNameRecipe;
        private TextView favIcon;
        private Button btnDetails;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtNameRecipe = (TextView) itemView.findViewById(R.id.recipe_name);
            imageRecipe = (ImageView) itemView.findViewById(R.id.recipe_image);
            favIcon = (TextView) itemView.findViewById(R.id.recipe_favorite);
            btnDetails = (Button) itemView.findViewById(R.id.recipe_btnDetails);
        }

        public boolean isPresent(ArrayList<Recipe> recipes){

            boolean isPresent = false;

            for(Recipe recipe: recipes){
                if(getNameRecipe().equals(recipe.getName())) {
                    isPresent = true;
                }
            }

            return isPresent;
        }

        public Bitmap getImageRecipe() {
            byte [] img = Base64.decode(this.imageRecipe.getDrawable().toString(),Base64.DEFAULT);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(img,0,img.length);
            return myBitmap;
        }

        public void setImageRecipe(Bitmap image) {
            this.imageRecipe.setImageBitmap(image);
        }

        public String getNameRecipe() {
            return txtNameRecipe.getText().toString();
        }

        public void setNameRecipe(String nameRecipe) {
            this.txtNameRecipe.setText(nameRecipe);
        }

        public Drawable getFavIcon() {
            return favIcon.getCompoundDrawables()[0];
        }

        public void setFavIcon(int favIcon) {
            this.favIcon.setCompoundDrawablesWithIntrinsicBounds(favIcon,0,0,0);
        }
    }
}
