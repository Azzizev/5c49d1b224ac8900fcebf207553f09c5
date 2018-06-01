package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;
import cloud.dishwish.ragmart.dishwish.tasks.DiffUtilCallBack;

public class RecyclerViewAdapterIng extends RecyclerView.Adapter<RecyclerViewAdapterIng.MyViewHolder> {

    Context myContext;
    ArrayList<Ingredient> ingredientList;

    public RecyclerViewAdapterIng(Context myContext, ArrayList<Ingredient> ingredientList) {
        this.myContext = myContext;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterIng.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_recipe_ingredients_item, parent, false);
        final RecyclerViewAdapterIng.MyViewHolder vHolder = new RecyclerViewAdapterIng.MyViewHolder(v);

        vHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myContext,"Test Click " + ingredientList.size(), Toast.LENGTH_SHORT).show();

            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterIng.MyViewHolder holder, final int position) {

        holder.setName(ingredientList.get(position).getName());
        holder.setPicture(ingredientList.get(position).getPicture());

        /*new AsyncTask<MyViewHolder, Void, Bitmap>(){

            private MyViewHolder holder;

            @Override
            protected Bitmap doInBackground(MyViewHolder... myViewHolders) {
                holder = myViewHolders[0];
                return holder.getPicture();
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                if(holder.position == position){
                    holder.setName(ingredientList.get(position).getName());
                    holder.setPicture(ingredientList.get(position).getPicture());
                }
            }
        }.execute(holder);*/
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout container;
        private TextView name;
        private EditText amount;
        private ImageView picture;

        public MyViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView.findViewById(R.id.ingredients_container);
            name = (TextView) itemView.findViewById(R.id.ingredients_name);
            amount = (EditText) itemView.findViewById(R.id.ingredients_amount);
            picture = (ImageView) itemView.findViewById(R.id.ingredients_picture);
        }

        public RelativeLayout getContainer() {
            return container;
        }

        public void setContainer(RelativeLayout container) {
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

    public void updateIngredientListItems(List<Ingredient> ingredients)
    {
        final DiffUtilCallBack callBack = new DiffUtilCallBack(this.ingredientList, ingredients);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callBack);

        this.ingredientList.clear();
        this.ingredientList.addAll(ingredients);
        diffResult.dispatchUpdatesTo(this);
    }

}
