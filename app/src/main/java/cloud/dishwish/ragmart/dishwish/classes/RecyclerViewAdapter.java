package cloud.dishwish.ragmart.dishwish.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cloud.dishwish.ragmart.dishwish.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context myContext;
    List<Recipe> mData;
    //Dialog myDialog;

    public RecyclerViewAdapter(Context myContext, List<Recipe> mData) {
        this.myContext = myContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(myContext).inflate(R.layout.home_recipes_item, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        /*myDialog = new Dialog(myContext);
        myDialog.setContentView(R.layout.dialog_recipe);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

        vHolder.item_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myContext,"Test Click " + String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

                /*TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.dialog_name);
                TextView dialog_phone_tv = (TextView) myDialog.findViewById(R.id.dialog_phone);
                ImageView dialog_recipe_image = (ImageView) myDialog.findViewById(R.id.dialog_image);

                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());
                dialog_phone_tv.setText(mData.get(vHolder.getAdapterPosition()).getPhone());
                dialog_recipe_image.setImageResource(mData.get(vHolder.getAdapterPosition()).getPhoto());

                Button dialog_btn_call = (Button) myDialog.findViewById(R.id.dialog_btnCall);
                Button dialog_btn_message_ = (Button) myDialog.findViewById(R.id.dialog_btnMessage);

                myDialog.show();*/
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtNameRecipe.setText(mData.get(position).getName());
        holder.imageRecipe.setImageResource(mData.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout item_recipe;
        private ImageView imageRecipe;
        private TextView txtNameRecipe;


        public MyViewHolder(View itemView) {
            super(itemView);

            item_recipe = (RelativeLayout) itemView.findViewById(R.id.recipe_item_id);
            txtNameRecipe = (TextView) itemView.findViewById(R.id.recipe_name);
            imageRecipe = (ImageView) itemView.findViewById(R.id.recipe_image);
        }
    }
}
