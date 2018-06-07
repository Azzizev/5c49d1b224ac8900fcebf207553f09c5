package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;

public class RV_AdapterAllIngredients extends RecyclerView.Adapter<RV_AdapterAllIngredients.AllIngredientVH> {

    private Context myContext;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Ingredient> selectedIngredients;

    public RV_AdapterAllIngredients(Context myContext, ArrayList<Ingredient> ingredientList, ArrayList<Ingredient> selectedIngredients) {
        this.myContext = myContext;
        this.ingredients = ingredientList;
        this.selectedIngredients = selectedIngredients;
    }

    @NonNull
    @Override
    public AllIngredientVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_recipe_ingredients_all_item, parent, false);
        AllIngredientVH vHolder = new AllIngredientVH(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AllIngredientVH holder, final int position) {

        holder.setName(ingredients.get(position).getName());
        holder.setPicture(ingredients.get(position).getPicture());

        for(int i = 0; i<selectedIngredients.size(); i++){
            if(holder.getName().equals(selectedIngredients.get(i)))
                holder.container.setBackgroundColor(Color.GRAY);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!holder.isPresent(selectedIngredients)){
                    holder.container.setBackgroundColor(Color.LTGRAY);
                    selectedIngredients.add(ingredients.get(position));

                    FragAddIngredients.fragSelectedIngs.myRecycle.getAdapter().notifyDataSetChanged();
                } else {
                    holder.container.setBackgroundColor(Color.WHITE);
                    selectedIngredients.remove(ingredients.get(position));

                   FragAddIngredients.fragSelectedIngs.myRecycle.getAdapter().notifyDataSetChanged();
                }
            }
        });

        if(!holder.isPresent(selectedIngredients)){
            holder.container.setBackgroundColor(Color.WHITE);
        } else {
            holder.container.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class AllIngredientVH extends RecyclerView.ViewHolder {

        private CardView container;
        private TextView name;
        private EditText amount;
        private EditText measureUnity;
        private ImageView picture;
        private int position;

        public AllIngredientVH(View itemView) {
            super(itemView);

            this.container = (CardView) itemView.findViewById(R.id.ingredients_container);
            this.name = (TextView) itemView.findViewById(R.id.ingredients_name);
            this.amount = (EditText) itemView.findViewById(R.id.ingredients_amount);
            this.measureUnity = (EditText) itemView.findViewById(R.id.ingredients_measure_unity);
            this.picture = (ImageView) itemView.findViewById(R.id.ingredients_picture);

            amount.setText("");
            measureUnity.setText("");
        }

        public boolean isPresent(ArrayList<Ingredient> ingredients){

            boolean isPresent = false;

            for(Ingredient ing: ingredients){

                if(getName().equals(ing.getName())){
                    isPresent = true;
                }
            }

            return isPresent;
        }

        public boolean isPresent(List<Ingredient> ingredientNames){

            boolean isPresent = false;

            for(Ingredient ingName: ingredientNames){

                if(getName().equals(ingName.getName())){
                    isPresent = true;
                }
            }

            return isPresent;
        }

        public CardView getContainer() {
            return container;
        }

        public void setContainer(CardView container) {
            this.container = container;
        }

        public String getName() {
            return name.getText().toString();
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public int getAmount() {
            return Integer.parseInt(amount.getText().toString());
        }

        public void setAmount(int amount) {
            this.amount.setText(amount + "");
        }

        public Bitmap getPicture() {

            byte [] img = Base64.decode(picture.getDrawable().toString(),Base64.DEFAULT);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(img,0,img.length);
            return myBitmap;
        }

        public void setPicture(Bitmap picture) {
            this.picture.setImageBitmap(picture);
        }
    }
}
