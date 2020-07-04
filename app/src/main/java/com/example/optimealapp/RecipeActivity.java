package com.example.optimealapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import java.util.Arrays;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {


    RecyclerView myRecyclerView;
    private ImageButton change_meal_button;
    ImageView image_plat;
    TextView title_plat;

    private ArrayList<String> liste_ingredients_id, liste_aliments_id, liste_images, liste_quantités,
    liste_aliments_name, liste_titles;

    private String title, image, category;
    public static String meal_id, meal_type;

    private ArrayList<String> ing_ids, ing_aliments, ing_quantities;
    private ArrayList<String> plat_ids, plat_images, plat_titles, plat_categories, plat_ingredients;

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

        title_plat.setText(title);
        image_plat.setImageBitmap(loadImageFromStorageFromImageDir(image));

    }

    public void refreshDataListe() {
        liste_aliments_id = new ArrayList<>();
        liste_images = new ArrayList<>();
        liste_quantités = new ArrayList<>();
        liste_aliments_name = new ArrayList<>();
        //myPlanDB.deleteAllPlans();

        storePlanDataInArraysMeals();
        storePlanDataInArraysIngredients();
        storePlanDataInArraysAliments();

        List<String> liste_ingredients_id = Arrays.asList((plat_ingredients.get(plat_ids.indexOf(meal_id))).split(","));
        title = plat_titles.get(plat_ids.indexOf(meal_id)).replace("_","'");
        image = plat_images.get(plat_ids.indexOf(meal_id));
        category = plat_categories.get(plat_ids.indexOf(meal_id));

        for (String ingredient_id:liste_ingredients_id){
            //Log.i("MyTAG",ingredient_id);
            int indice = ing_ids.indexOf(ingredient_id);
            String aliment_id = ing_aliments.get(indice);
            String quantité = ing_quantities.get(indice);

            liste_aliments_id.add(aliment_id);
            liste_quantités.add(quantité);
            liste_images.add(aliment_images.get(aliment_ids.indexOf(aliment_id)));
            liste_aliments_name.add(aliment_names.get(aliment_ids.indexOf(aliment_id)));
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
                ChangeMealActivity.meal_type = meal_type;
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
        plat_ingredients = new ArrayList<>();

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
                plat_ingredients.add(cursor.getString(4));
            }
        }


    }
    void storePlanDataInArraysIngredients(){
        myPlanDB = new PlanificationDataBase(RecipeActivity.this);
        ing_ids = new ArrayList<>();
        ing_aliments = new ArrayList<>();
        ing_quantities = new ArrayList<>();

        Cursor cursor = myPlanDB.readAllDataIngredients();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            //Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                ing_ids.add(cursor.getString(0));
                ing_aliments.add(cursor.getString(1));
                ing_quantities.add(cursor.getString(2));
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
