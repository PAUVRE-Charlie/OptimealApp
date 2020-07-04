package com.example.optimealapp.ui.liste;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.optimealapp.Date.Date;
import com.example.optimealapp.LivraisonActivity;
import com.example.optimealapp.MainActivity;
import com.example.optimealapp.R;
import com.example.optimealapp.User.User;
import com.example.optimealapp.ui.calendrier.PlanificationDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListeFragment extends Fragment {

    private View root;
    private User user;
    private RecyclerView myRecyclerView;
    private MyListeAdapter myAdapter;
    private FloatingActionButton add_button;
    private ImageButton reset_button;
    private Button livraison_button;
    private EditText name_editText, quantity_editText;

    private ArrayList<String> aliment_ids, aliment_names, aliment_calories, aliment_images;

    private ArrayList<String> ing_ids, ing_aliments, ing_quantities, ing_plat;
    private ArrayList<String> ing_bonus_ids, ing_bonus_aliments, ing_bonus_quantities, ing_bonus_plat;
    private ArrayList<String> ing_modifs_ids, ing_modifs_aliments, ing_modifs_quantities, ing_modif_plat;

    private ArrayList<String> plat_ids, plat_titles, plat_categories, plat_images, plat_saison, plat_calories;

    private ArrayList<String> plan_ids, plan_users, plan_dates, plan_ptitdejs, plan_collations, plan_dejentrees
            , plan_dejplats, plan_dejdesserts, plan_gouters, plan_dinerentrees, plan_dinerplats, plan_dinerdesserts;

    private PlanificationDataBase myPlanDB;

    private ArrayList<String> liste_plats_id, liste_ingredients_id, liste_aliments_id, liste_images,
            liste_quantités, liste_aliments_name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_liste, container, false);
        user = MainActivity.user;
        myRecyclerView = root.findViewById(R.id.liste_recycler_view);
        myPlanDB = new PlanificationDataBase(getContext());
        //Log.i("MyTAG","helloooo");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            refreshDataListe();
        }
        name_editText = root.findViewById(R.id.liste_add_name_editText);
        quantity_editText = root.findViewById(R.id.liste_add_quantity_editText);
        livraison_button = root.findViewById(R.id.liste_livraison_button);
        livraison_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LivraisonActivity.class);
                startActivity(intent);
            }
        });
        reset_button = root.findViewById(R.id.liste_reset_imageButton);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPlanDB.deleteAllModifAndBonus();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    refreshDataListe();
                    Toast.makeText(getContext(), "Reset effectué", Toast.LENGTH_SHORT).show();
                }
            }
        });
        add_button = root.findViewById(R.id.list_add_floatingActionButton);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name_editText.getText().toString().trim().equals("") &&
                        !quantity_editText.getText().toString().trim().equals("")){
                            myPlanDB.addIngredientToList(PlanificationDataBase.TABLE_NAME_INGREDIENTS_BONUS,
                            name_editText.getText().toString().trim(),
                            quantity_editText.getText().toString().trim(), "");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        refreshDataListe();
                        myRecyclerView.scrollToPosition(liste_aliments_id.size()-1);
                    }

                }
            }
        });


        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void refreshDataListe() {
        liste_plats_id = new ArrayList<>();
        liste_ingredients_id = new ArrayList<>();
        liste_aliments_id = new ArrayList<>();
        liste_images = new ArrayList<>();
        liste_quantités = new ArrayList<>();
        liste_aliments_name = new ArrayList<>();
        storePlanDataInArraysPlan();
        storePlanDataInArraysMeals();
        storePlanDataInArraysIngredients();
        storePlanDataInArraysIngredientsBonus();
        storePlanDataInArraysIngredientsModif();
        storePlanDataInArraysAliments();
        Date today = Date.getToday();
        for (int position=0; position<plan_dates.size();position++){
            String date_str = plan_dates.get(position);
            Date date = new Date(date_str);
            if (date.before(today)){
                myPlanDB.deleteRow(PlanificationDataBase.TABLE_NAME_PLAN,plan_ids.get(position));
            }
            else{
                if (plan_users.get(position).equals(Integer.toString(user.getId()))){
                    liste_plats_id.add(plan_ptitdejs.get(position));
                    liste_plats_id.add(plan_collations.get(position));
                    liste_plats_id.add(plan_dejentrees.get(position));
                    liste_plats_id.add(plan_dejplats.get(position));
                    liste_plats_id.add(plan_dejdesserts.get(position));
                    liste_plats_id.add(plan_gouters.get(position));
                    liste_plats_id.add(plan_dinerentrees.get(position));
                    liste_plats_id.add(plan_dinerplats.get(position));
                    liste_plats_id.add(plan_dinerdesserts.get(position));
                }
            }

        }
        //Log.i("MyTAG","liste"+liste_plats_id.toString());
        /*Log.i("MyTAG","ing"+ing_plat.toString());
        Log.i("MyTAG","ing"+ing_plat.size());*/
        for (String plat_id: liste_plats_id){
            if (!plat_id.equals("0")) {
                /*Log.i("MyTAG","yes"+plat_id);
                Log.i("MyTAG","yes"+ing_plat.toString());
                Log.i("MyTAG","yes"+plat_titles.get(plat_ids.indexOf(plat_id)));*/
                for (int i = 0; i<ing_plat.size();i++){
                    if (ing_plat.get(i).equals(plat_titles.get(plat_ids.indexOf(plat_id)))){
                        //Log.i("MyTAG","yeah");
                        liste_ingredients_id.add(ing_ids.get(i));
                    }
                }
            }
        }
        //Log.i("MyTAG", "liste"+liste_ingredients_id.toString());
        for (String ingredient_id:liste_ingredients_id){
            //Log.i("MyTAG",ingredient_id);
            int indice = ing_ids.indexOf(ingredient_id);
            String aliment = ing_aliments.get(indice);
            /*Log.i("MyTAG","id"+indice);
            Log.i("MyTAG","id"+aliment);
            Log.i("MyTAG","id"+aliment_names.toString());
            Log.i("MyTAG","id"+aliment_names.size());
            Log.i("MyTAG","id"+aliment_names.indexOf(aliment));
            Log.i("MyTAG","id"+ing_aliments.toString());
            */
            String aliment_id = aliment_ids.get(aliment_names.indexOf(aliment));
            String quantité = ing_quantities.get(indice);

            if (liste_aliments_id.contains(aliment_id)){
                int index = liste_aliments_id.indexOf(aliment_id);
                liste_quantités.set(index, Float.toString(
                        Float.parseFloat(liste_quantités.get(index)) + Float.parseFloat(quantité)));
            } else {
                if (ing_modifs_aliments.contains(aliment_id)) {

                    String quantité_modif = ing_modifs_quantities.get(ing_modifs_aliments.indexOf(aliment_id));

                    if (!quantité_modif.equals("0")) {
                        liste_aliments_id.add(aliment_id);
                        liste_quantités.add(quantité_modif);
                        liste_images.add(aliment_images.get(aliment_ids.indexOf(aliment_id)));
                        liste_aliments_name.add(aliment_names.get(aliment_ids.indexOf(aliment_id)));
                    }
                }
                else {
                    liste_aliments_id.add(aliment_id);
                    liste_quantités.add(quantité);
                    liste_images.add(aliment_images.get(aliment_ids.indexOf(aliment_id)));
                    liste_aliments_name.add(aliment_names.get(aliment_ids.indexOf(aliment_id)));
                }
            }
        }

        for (int position=0; position<ing_bonus_ids.size();position++){
            String aliment_bonus_id = ing_bonus_aliments.get(position);
            liste_aliments_id.add(aliment_bonus_id);
            liste_quantités.add(ing_bonus_quantities.get(position));
            liste_images.add(aliment_images.get(aliment_ids.indexOf(aliment_bonus_id)));
            liste_aliments_name.add(aliment_names.get(aliment_ids.indexOf(aliment_bonus_id)));
        }

        //Log.i("MyTAG","adapter"+liste_aliments_name.toString());
        //Log.i("MyTAG","adapter"+liste_aliments_name.toString());
        myAdapter = new MyListeAdapter(this, myRecyclerView, getContext(), ing_bonus_ids, liste_images, liste_aliments_name, liste_quantités);
        //Log.i("MyTAG",getContext().toString());
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Log.i("MyTAG","layout");
        myRecyclerView.setAdapter(myAdapter);
        //Log.i("MyTAG","set");
        //myRecyclerView.scrollToPosition(current_position - 1);

    }

    void storePlanDataInArraysPlan(){
        myPlanDB = new PlanificationDataBase(getContext());
        plan_ids = new ArrayList<>();
        plan_users = new ArrayList<>();
        plan_dates = new ArrayList<>();
        plan_ptitdejs = new ArrayList<>();
        plan_collations = new ArrayList<>();
        plan_dejentrees = new ArrayList<>();
        plan_dejplats = new ArrayList<>();
        plan_dejdesserts = new ArrayList<>();
        plan_gouters = new ArrayList<>();
        plan_dinerentrees = new ArrayList<>();
        plan_dinerplats = new ArrayList<>();
        plan_dinerdesserts = new ArrayList<>();

        Cursor cursor = myPlanDB.readAllDataPlan();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            //Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                plan_ids.add(cursor.getString(0));
                plan_users.add(cursor.getString(1));
                plan_dates.add((new Date(cursor.getString(2))).toString());
                plan_ptitdejs.add(cursor.getString(3));
                plan_collations.add(cursor.getString(4));
                plan_dejentrees.add(cursor.getString(5));
                plan_dejplats.add(cursor.getString(6));
                plan_dejdesserts.add(cursor.getString(7));
                plan_gouters.add(cursor.getString(8));
                plan_dinerentrees.add(cursor.getString(9));
                plan_dinerplats.add(cursor.getString(10));
                plan_dinerdesserts.add(cursor.getString(11));

            }
        }


    }
    void storePlanDataInArraysMeals(){
        myPlanDB = new PlanificationDataBase(getContext());
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
        myPlanDB = new PlanificationDataBase(getContext());
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
                //Log.i("MyTAG",(cursor.getString(1)));
                ing_quantities.add(cursor.getString(2));
                ing_plat.add(cursor.getString(3));
            }
        }
    }
    void storePlanDataInArraysIngredientsBonus(){
        myPlanDB = new PlanificationDataBase(getContext());
        ing_bonus_ids = new ArrayList<>();
        ing_bonus_aliments = new ArrayList<>();
        ing_bonus_quantities = new ArrayList<>();

        Cursor cursor = myPlanDB.readAllDataIngredientsBonus();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            //Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                ing_bonus_ids.add(cursor.getString(0));
                ing_bonus_aliments.add(cursor.getString(1));
                ing_bonus_quantities.add(cursor.getString(2));
            }
        }
    }
    void storePlanDataInArraysIngredientsModif(){
        myPlanDB = new PlanificationDataBase(getContext());
        ing_modifs_ids = new ArrayList<>();
        ing_modifs_aliments = new ArrayList<>();
        ing_modifs_quantities = new ArrayList<>();

        Cursor cursor = myPlanDB.readAllDataIngredientsModif();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            //Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                ing_modifs_ids.add(cursor.getString(0));
                ing_modifs_aliments.add(cursor.getString(1));
                ing_modifs_quantities.add(cursor.getString(2));
            }
        }
    }
    void storePlanDataInArraysAliments(){
        myPlanDB = new PlanificationDataBase(getContext());
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
}