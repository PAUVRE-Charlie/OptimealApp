package com.example.optimealapp.ui.liste;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.optimealapp.R;
import com.example.optimealapp.ui.calendrier.PlanificationDataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MyListeAdapter extends RecyclerView.Adapter<MyListeAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> liste_ing_bonus_ids, liste_images, liste_aliments, liste_quantités;
    private ListeFragment fragment;
    private RecyclerView myRecyclerView;

    public MyListeAdapter(ListeFragment fragment, RecyclerView myRecyclerView, Context context, ArrayList<String> liste_ing_bonus_ids,
                          ArrayList<String> liste_images, ArrayList<String> liste_aliments,
                          ArrayList<String> liste_quantités){
        this.context = context;
        this.myRecyclerView = myRecyclerView;
        this.liste_ing_bonus_ids = liste_ing_bonus_ids;
        this.liste_images = liste_images;
        this.liste_aliments = liste_aliments;
        this.liste_quantités = liste_quantités;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyListeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_liste_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListeAdapter.MyViewHolder holder, final int position) {
        holder.image_ImageView.setImageBitmap(loadImageFromStorageFromImageDir(liste_images.get(position)));
        holder.name_textView.setText((liste_aliments.get(position)).replace("_","'"));
        holder.quantity_textView.setText(liste_quantités.get(position));
        //Log.i("MyTAG","image "+liste_images.get(position));
        holder.delete_button.setVisibility(View.VISIBLE);
        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanificationDataBase mydb = new PlanificationDataBase(context);
                if (position < liste_images.size() - liste_ing_bonus_ids.size()){
                    mydb.addIngredientToList(PlanificationDataBase.TABLE_NAME_INGREDIENTS_MODIF,
                            liste_aliments.get(position),"0");
                } else {
                    mydb.deleteRow(PlanificationDataBase.TABLE_NAME_INGREDIENTS_BONUS,
                            liste_ing_bonus_ids.get(position-liste_images.size()+liste_ing_bonus_ids.size()));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fragment.refreshDataListe();
                    myRecyclerView.scrollToPosition(position - 1);
                }
            }
        });
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
            Log.i("MyTAG","notFound");
        }
        finally {
            return b;
        }


    }


}
