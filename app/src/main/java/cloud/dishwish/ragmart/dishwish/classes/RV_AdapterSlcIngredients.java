package cloud.dishwish.ragmart.dishwish.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class RV_AdapterSlcIngredients extends RecyclerView.Adapter<RV_AdapterSlcIngredients.SlcIngredientVH> {

    private Context context;
    private ArrayList<Ingredient> selectedIngredients;

    public RV_AdapterSlcIngredients(Context context, ArrayList<Ingredient> selectedIngredients){
        this.context = context;
        this.selectedIngredients = selectedIngredients;
    }

    @NonNull
    @Override
    public SlcIngredientVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_recipe_ingredients_selected_item, parent, false);
        SlcIngredientVH vHolder = new SlcIngredientVH(v);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SlcIngredientVH holder, int position) {

        holder.setName(selectedIngredients.get(position).getName());
        holder.setPicture(selectedIngredients.get(position).getPicture());

        holder.amount.setText("0");
        holder.measureUnity.setText("m. u");
    }

    @Override
    public int getItemCount() {
        return selectedIngredients.size();
    }

    public static class SlcIngredientVH extends RecyclerView.ViewHolder {


        private CardView container;
        private TextView name;
        private EditText amount;
        private EditText measureUnity;
        private ImageView picture;
        private int position;

        public SlcIngredientVH(View itemView) {
            super(itemView);

            this.container = (CardView) itemView.findViewById(R.id.ingredients_selected_container);
            this.name = (TextView) itemView.findViewById(R.id.ingredients_selected_name);
            this.amount = (EditText) itemView.findViewById(R.id.ingredients_selected_amount);
            this.measureUnity = (EditText) itemView.findViewById(R.id.ingredients_selected_measure_unity);
            this.picture = (ImageView) itemView.findViewById(R.id.ingredients_selected_picture);
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
