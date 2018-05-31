package cloud.dishwish.ragmart.dishwish.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Base64;

import java.util.List;

import cloud.dishwish.ragmart.dishwish.classes.Ingredient;

public class DiffUtilCallBack extends DiffUtil.Callback {

    List<Ingredient> oldList;
    List<Ingredient> newList;

    public  DiffUtilCallBack(List<Ingredient> oldList, List<Ingredient> newList)
    {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return (oldList != null) ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return (newList != null) ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).compareTo(oldList.get(oldItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Ingredient newIngredient = newList.get(newItemPosition);
        Ingredient oldIngredient = oldList.get(oldItemPosition);

        Bundle diff = new Bundle();

        if(!newIngredient.getName().equals(oldIngredient.getName())) {
            diff.putString("ingredient_name", newIngredient.getName());
        }

        if(newIngredient.getAmount() != oldIngredient.getAmount()){
            diff.putInt("ingredient_amount", newIngredient.getAmount());
        }

        if(!newIngredient.getPicture().equals(oldIngredient.getPicture())){
            diff.putString("ingredient_picture", newIngredient.getPicture().toString());

            //byte [] prova = Base64.decode("prova",Base64.DEFAULT);
            //Bitmap myBm = BitmapFactory.decodeByteArray(prova,0,prova.length);
        }

        if(diff.size() == 0)
            return null;

        return diff;
    }
}
