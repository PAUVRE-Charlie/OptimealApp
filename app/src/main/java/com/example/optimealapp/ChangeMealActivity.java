package com.example.optimealapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.optimealapp.ui.calendrier.PlanificationDataBase;

import java.util.ArrayList;

public class ChangeMealActivity extends AppCompatActivity {

    public static String category;
    public static String meal_type;

    private RecyclerView myRecyclerView;
    private MyChangeMealAdapter myAdapter;
    private PlanificationDataBase myPlanDB;

    private ArrayList<String> plat_ids, plat_titles, plat_categories, plat_ingredients, plat_images;

    private ArrayList<String> liste_plats_ids, liste_plats_images, liste_plats_titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_meal);

        myRecyclerView = findViewById(R.id.change_recyclerView);
        myPlanDB = new PlanificationDataBase(ChangeMealActivity.this);

        refreshMealsData();
    }

    void refreshMealsData(){

        liste_plats_ids = new ArrayList<>();
        liste_plats_images = new ArrayList<>();
        liste_plats_titles = new ArrayList<>();

        storePlanDataInArraysMeals();

        for (int position = 0; position<plat_ids.size(); position++){
            if (!plat_ids.get(position).equals("0") && plat_categories.get(position).equals(category)) {
                liste_plats_ids.add(plat_ids.get(position));
                liste_plats_images.add(plat_images.get(position));
                liste_plats_titles.add(plat_titles.get(position));
            }

        }


        myAdapter = new MyChangeMealAdapter(ChangeMealActivity.this,
                liste_plats_titles, liste_plats_images, liste_plats_ids);
        //Log.i("MyTAG",getContext().toString());
        myRecyclerView.setLayoutManager(new LinearLayoutManager(ChangeMealActivity.this));
        //Log.i("MyTAG","layout");
        myRecyclerView.setAdapter(myAdapter);
        //Log.i("MyTAG","set");
        //myRecyclerView.scrollToPosition(current_position - 1);

    }



    void storePlanDataInArraysMeals(){
        myPlanDB = new PlanificationDataBase(ChangeMealActivity.this);
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

}
