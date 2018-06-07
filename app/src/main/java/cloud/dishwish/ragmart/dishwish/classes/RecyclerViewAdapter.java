package cloud.dishwish.ragmart.dishwish.classes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.new_recipe.NewRecipeActivity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context myContext;
    List<Recipe> recipeList;

    public RecyclerViewAdapter(Context myContext, List<Recipe> recipeList) {
        this.myContext = myContext;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(myContext).inflate(R.layout.home_recipes_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.txtNameRecipe.setText(recipeList.get(position).getName());
        holder.imageRecipe.setImageBitmap(recipeList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageRecipe;
        private TextView txtNameRecipe;
        private TextView txtLikeNo;
        private Button btnDetails;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtNameRecipe = (TextView) itemView.findViewById(R.id.recipe_name);
            imageRecipe = (ImageView) itemView.findViewById(R.id.recipe_image);
            txtLikeNo = (TextView) itemView.findViewById(R.id.recipe_likes);
            btnDetails = (Button) itemView.findViewById(R.id.recipe_btnDetails);

            txtLikeNo.setText("" + (int)(Math.random() + 0.5));
        }
    }
}
