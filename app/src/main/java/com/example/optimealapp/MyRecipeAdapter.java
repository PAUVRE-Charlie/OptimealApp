package com.example.optimealapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> liste_images, liste_aliments, liste_quantités;
    private RecipeActivity recipeActivity;
    private RecyclerView myRecyclerView;

    public MyRecipeAdapter(RecipeActivity recipeActivity, RecyclerView myRecyclerView, Context context,
                          ArrayList<String> liste_images, ArrayList<String> liste_aliments,
                          ArrayList<String> liste_quantités){
        this.context = context;
        this.myRecyclerView = myRecyclerView;
        this.liste_images = liste_images;
        this.liste_aliments = liste_aliments;
        this.liste_quantités = liste_quantités;
        this.recipeActivity = recipeActivity;
    }

    @NonNull
    @Override
    public MyRecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_liste_row,parent,false);
        return new MyRecipeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeAdapter.MyViewHolder holder, final int position) {
        holder.image_ImageView.setImageBitmap(loadImageFromStorageFromImageDir(liste_images.get(position)));
        String str = (liste_aliments.get(position)).replace("_","'");
        holder.name_textView.setText((str.substring(0, 1).toUpperCase() + str.substring(1)));
        holder.quantity_textView.setText(liste_quantités.get(position));
        //Log.i("MyTAG","image "+liste_images.get(position));
        holder.delete_button.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return liste_aliments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_ImageView;
        private TextView name_textView, quantity_textView;
        private ImageButton delete_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_ImageView=itemView.findViewById(R.id.liste_row_image_view);
            name_textView=itemView.findViewById(R.id.liste_row_name);
            quantity_textView=itemView.findViewById(R.id.liste_row_quantity);
            delete_button=itemView.findViewById(R.id.liste_row_delete_imageButton);

        }
    }
    private Bitmap loadImageFromStorageFromImageDir(String name)
    {
        String path = context.getDir("imagedir", Context.MODE_PRIVATE).getAbsolutePath();
        Bitmap b = null;
        try {
            File f=new File(path, name);
            //Log.i("MyTAG",f.toString());
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            //Log.i("MyTAG","notFound");
        }
        finally {
            return b;
        }


    }


}
