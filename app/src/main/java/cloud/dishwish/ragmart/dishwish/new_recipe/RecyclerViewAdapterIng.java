package cloud.dishwish.ragmart.dishwish.new_recipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;
import cloud.dishwish.ragmart.dishwish.classes.Ingredient;

public class RecyclerViewAdapterIng extends RecyclerView.Adapter<RecyclerViewAdapterIng.MyViewHolder> {

    Context myContext;
    List<Ingredient> ingredientList;

    public RecyclerViewAdapterIng(Context myContext, List<Ingredient> ingredientList) {
        this.myContext = myContext;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterIng.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(myContext).inflate(R.layout.new_recipe_ingredients_item, parent, false);
        final RecyclerViewAdapterIng.MyViewHolder vHolder = new RecyclerViewAdapterIng.MyViewHolder(v);


        vHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myContext,"Test Click " + String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterIng.MyViewHolder holder, int position) {

        holder.name.setText(ingredientList.get(position).getName());
        holder.picture.setImageBitmap(ingredientList.get(position).getPicture());
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
    }
}
