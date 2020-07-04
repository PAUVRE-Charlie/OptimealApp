package com.example.optimealapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.optimealapp.ui.calendrier.CalendrierFragment;
import com.example.optimealapp.ui.calendrier.PlanificationDataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MyChangeMealAdapter extends RecyclerView.Adapter<MyChangeMealAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> plats_titles, plats_images, plats_ids;

    public MyChangeMealAdapter(Context context, ArrayList<String> plats_titles,
                               ArrayList<String> plats_images, ArrayList<String> plats_ids){
        this.context = context;
        this.plats_ids = plats_ids;
        this.plats_images = plats_images;
        this.plats_titles = plats_titles;
    }

    @NonNull
    @Override
    public MyChangeMealAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_change_meal_row,parent,false);
        return new MyChangeMealAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChangeMealAdapter.MyViewHolder holder, final int position) {
        holder.imageButton.setImageBitmap(loadImageFromStorageFromImageDir(plats_images.get(position)));
        holder.textView.setText(plats_titles.get(position));
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyTAG", ChangeMealActivity.category);
                PlanificationDataBase myPlanDB = new PlanificationDataBase(context);
                if (ChangeMealActivity.meal_type.equals(PlanificationDataBase.COLUMN_PTITDEJ)){
                    myPlanDB.updateDataPlan(CalendrierFragment.plan_id, Integer.toString(MainActivity.user.getId()),
                            CalendrierFragment.getDate().toString(),
                            plats_ids.get(position), CalendrierFragment.collation_id,
                            CalendrierFragment.dej_entree_id, CalendrierFragment.dej_plat_id,
                            CalendrierFragment.dej_dessert_id, CalendrierFragment.gouter_id,
                            CalendrierFragment.diner_entree_id, CalendrierFragment.diner_plat_id,
                            CalendrierFragment.diner_dessert_id);
                } else if (ChangeMealActivity.meal_type.equals(PlanificationDataBase.COLUMN_COLLATION)){
                    myPlanDB.updateDataPlan(CalendrierFragment.plan_id, Integer.toString(MainActivity.user.getId()),
                            CalendrierFragment.getDate().toString(),
                            CalendrierFragment.ptitdej_id, plats_ids.get(position),
                            CalendrierFragment.dej_entree_id, CalendrierFragment.dej_plat_id,
                            CalendrierFragment.dej_dessert_id, CalendrierFragment.gouter_id,
                            CalendrierFragment.diner_entree_id, CalendrierFragment.diner_plat_id,
                            CalendrierFragment.diner_dessert_id);
                } else if (ChangeMealActivity.meal_type.equals(PlanificationDataBase.COLUMN_DEJENTREE)){
                    myPlanDB.updateDataPlan(CalendrierFragment.plan_id, Integer.toString(MainActivity.user.getId()),
                            CalendrierFragment.getDate().toString(),
                            CalendrierFragment.ptitdej_id, CalendrierFragment.collation_id,
                            plats_ids.get(position),CalendrierFragment.dej_plat_id,
                            CalendrierFragment.dej_dessert_id, CalendrierFragment.gouter_id,
                            CalendrierFragment.diner_entree_id, CalendrierFragment.diner_plat_id,
                            CalendrierFragment.diner_dessert_id);
                } else if (ChangeMealActivity.meal_type.equals(PlanificationDataBase.COLUMN_DEJPLAT)){
                    myPlanDB.updateDataPlan(CalendrierFragment.plan_id, Integer.toString(MainActivity.user.getId()),
                            CalendrierFragment.getDate().toString(),
                            CalendrierFragment.ptitdej_id, CalendrierFragment.collation_id,
                            CalendrierFragment.dej_entree_id,plats_ids.get(position),
                            CalendrierFragment.dej_dessert_id, CalendrierFragment.gouter_id,
                            CalendrierFragment.diner_entree_id, CalendrierFragment.diner_plat_id,
                            CalendrierFragment.diner_dessert_id);
                } else if (ChangeMealActivity.meal_type.equals(PlanificationDataBase.COLUMN_DEJDESSERT)){
                    myPlanDB.updateDataPlan(CalendrierFragment.plan_id, Integer.toString(MainActivity.user.getId()),
                            CalendrierFragment.getDate().toString(),
                            CalendrierFragment.ptitdej_id, CalendrierFragment.collation_id,
                            CalendrierFragment.dej_entree_id, CalendrierFragment.dej_plat_id,
                            plats_ids.get(position), CalendrierFragment.gouter_id,
                            CalendrierFragment.diner_entree_id, CalendrierFragment.diner_plat_id,
                            CalendrierFragment.diner_dessert_id);
                } else if (ChangeMealActivity.meal_type.equals(PlanificationDataBase.COLUMN_GOUTER)){
                    myPlanDB.updateDataPlan(CalendrierFragment.plan_id, Integer.toString(MainActivity.user.getId()),
                            CalendrierFragment.getDate().toString(),
                            CalendrierFragment.ptitdej_id, CalendrierFragment.collation_id,
                            CalendrierFragment.dej_entree_id, CalendrierFragment.dej_plat_id,
                            CalendrierFragment.dej_dessert_id, plats_ids.get(position),
                            CalendrierFragment.diner_entree_id, CalendrierFragment.diner_plat_id,
                            CalendrierFragment.diner_dessert_id);
                } else if (ChangeMealActivity.meal_type.equals(PlanificationDataBase.COLUMN_DINERENTREE)){
                    myPlanDB.updateDataPlan(CalendrierFragment.plan_id, Integer.toString(MainActivity.user.getId()),
                            CalendrierFragment.getDate().toString(),
                            CalendrierFragment.ptitdej_id, CalendrierFragment.collation_id,
                            CalendrierFragment.dej_entree_id, CalendrierFragment.dej_plat_id,
                            CalendrierFragment.dej_dessert_id, CalendrierFragment.gouter_id,
                            plats_ids.get(position),CalendrierFragment.diner_plat_id,
                            CalendrierFragment.diner_dessert_id);
                } else if (ChangeMealActivity.meal_type.equals(PlanificationDataBase.COLUMN_DINERPLAT)){
                    myPlanDB.updateDataPlan(CalendrierFragment.plan_id, Integer.toString(MainActivity.user.getId()),
                            CalendrierFragment.getDate().toString(),
                            CalendrierFragment.ptitdej_id, CalendrierFragment.collation_id,
                            CalendrierFragment.dej_entree_id, CalendrierFragment.dej_plat_id,
                            CalendrierFragment.dej_dessert_id, CalendrierFragment.gouter_id,
                            CalendrierFragment.diner_entree_id,plats_ids.get(position),
                            CalendrierFragment.diner_dessert_id);
                } else if (ChangeMealActivity.meal_type.equals(PlanificationDataBase.COLUMN_DINERDESSERT)){
                    myPlanDB.updateDataPlan(CalendrierFragment.plan_id, Integer.toString(MainActivity.user.getId()),
                            CalendrierFragment.getDate().toString(),
                            CalendrierFragment.ptitdej_id, CalendrierFragment.collation_id,
                            CalendrierFragment.dej_entree_id, CalendrierFragment.dej_plat_id,
                            CalendrierFragment.dej_dessert_id, CalendrierFragment.gouter_id,
                            CalendrierFragment.diner_entree_id, CalendrierFragment.diner_plat_id,
                            plats_ids.get(position));
                } else{
                    Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plats_titles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageButton imageButton;
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.change_row_imageButton);
            textView = itemView.findViewById(R.id.change_row_textView);
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
