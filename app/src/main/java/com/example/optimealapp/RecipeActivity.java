package com.example.optimealapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.optimealapp.ui.calendrier.PlanificationDataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {


    RecyclerView myRecyclerView;
    private ImageButton change_meal_button;
    ImageView image_plat;
    TextView title_plat;

    private ArrayList<String> liste_ingredients_id, liste_aliments_id, liste_images, liste_quantités,
    liste_aliments_name, liste_titles;

    private String title, image, category;
    public static String plat_id, plat_type;

    private ArrayList<String> ing_ids, ing_aliments, ing_quantities, ing_plat;
    private ArrayList<String> plat_ids, plat_images, plat_titles, plat_categories, plat_saison, plat_calories;

    private ArrayList<String> aliment_ids, aliment_names, aliment_images, aliment_calories;
    private MyRecipeAdapter myAdapter;
    PlanificationDataBase myPlanDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        myRecyclerView = findViewById(R.id.recipe_recyclerView);
        title_plat = findViewById(R.id.recipe_textView);
        image_plat = findViewById(R.id.recipe_imageView);

        refreshDataListe();

        title_plat.setText((title.substring(0, 1).toUpperCase() + title.substring(1)));
        image_plat.setImageBitmap(loadImageFromStorageFromImageDir(image));

    }

    public void refreshDataListe() {
        liste_aliments_id = new ArrayList<>();
        liste_images = new ArrayList<>();
        liste_quantités = new ArrayList<>();
        liste_aliments_name = new ArrayList<>();
        liste_ingredients_id = new ArrayList<>();

        //myPlanDB.deleteAllPlans();

        storePlanDataInArraysMeals();
        storePlanDataInArraysIngredients();
        storePlanDataInArraysAliments();

        for (int i = 0; i<ing_plat.size();i++){
            if (ing_plat.get(i).equals(plat_titles.get(plat_ids.indexOf(plat_id)))){
                //Log.i("MyTAG","yeah");
                liste_ingredients_id.add(ing_ids.get(i));
            }
        }


        title = plat_titles.get(plat_ids.indexOf(plat_id)).replace("_","'");
        image = plat_images.get(plat_ids.indexOf(plat_id));
        category = plat_categories.get(plat_ids.indexOf(plat_id));


        for (String ingredient_id:liste_ingredients_id){
            Log.i("MyTAG",ingredient_id);
            int indice = ing_ids.indexOf(ingredient_id);
            Log.i("MyTAG","indice"+indice);
            String aliment = ing_aliments.get(indice);
            String quantité = ing_quantities.get(indice);

            liste_quantités.add(quantité);
            Log.i("MyTAG",aliment);
            liste_images.add(aliment_images.get(aliment_names.indexOf(aliment)));
            liste_aliments_name.add(aliment);
        }

        myAdapter = new MyRecipeAdapter(this, myRecyclerView, RecipeActivity.this,
                liste_images, liste_aliments_name, liste_quantités);
        //Log.i("MyTAG",getContext().toString());
        myRecyclerView.setLayoutManager(new LinearLayoutManager(RecipeActivity.this));
        //Log.i("MyTAG","layout");
        myRecyclerView.setAdapter(myAdapter);
        //Log.i("MyTAG","set");
        //myRecyclerView.scrollToPosition(current_position - 1);

        change_meal_button = findViewById(R.id.recipe_change_meal_imageButton);
        change_meal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, ChangeMealActivity.class);
                ChangeMealActivity.category = category;
                ChangeMealActivity.meal_type = plat_type;
                startActivity(intent);
            }
        });

    }
    void storePlanDataInArraysMeals(){
        myPlanDB = new PlanificationDataBase(RecipeActivity.this);
        plat_ids = new ArrayList<>();
        plat_images = new ArrayList<>();
        plat_titles = new ArrayList<>();
        plat_categories = new ArrayList<>();
        plat_saison = new ArrayList<>();
        plat_calories = new ArrayList<>();

        Cursor cursor = myPlanDB.readAllDataMeals();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            //Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                plat_ids.add(cursor.getString(0));
                plat_images.add(cursor.getString(1));
                plat_titles.add(cursor.getString(2));
                plat_categories.add(cursor.getString(3));
                plat_saison.add(cursor.getString(4));
                plat_calories.add(cursor.getString(5));
            }
        }


    }
    void storePlanDataInArraysIngredients(){
        myPlanDB = new PlanificationDataBase(RecipeActivity.this);
        ing_ids = new ArrayList<>();
        ing_aliments = new ArrayList<>();
        ing_quantities = new ArrayList<>();
        ing_plat = new ArrayList<>();

        Cursor cursor = myPlanDB.readAllDataIngredients();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            //Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                ing_ids.add(cursor.getString(0));
                ing_aliments.add(cursor.getString(1));
                ing_quantities.add(cursor.getString(2));
                ing_plat.add(cursor.getString(3));
            }
        }
    }

    void storePlanDataInArraysAliments(){
        myPlanDB = new PlanificationDataBase(RecipeActivity.this);
        aliment_ids = new ArrayList<>();
        aliment_names = new ArrayList<>();
        aliment_images = new ArrayList<>();
        aliment_calories = new ArrayList<>();

        Cursor cursor = myPlanDB.readAllDataAliments();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            //Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                aliment_ids.add(cursor.getString(0));
                aliment_names.add(cursor.getString(1));
                aliment_images.add(cursor.getString(2));
                aliment_calories.add(cursor.getString(3));
            }
        }
    }

    private Bitmap loadImageFromStorageFromImageDir(String name)
    {
        String path = this.getDir("imagedir", Context.MODE_PRIVATE).getAbsolutePath();
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
