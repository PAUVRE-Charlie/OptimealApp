package com.example.optimealapp.ui.calendrier;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.example.optimealapp.Date.Date;
import com.example.optimealapp.GenerateActivity;
import com.example.optimealapp.MainActivity;
import com.example.optimealapp.R;
import com.example.optimealapp.RecipeActivity;
import com.example.optimealapp.User.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class CalendrierFragment extends DialogFragment {

    //private OnFragmentInteractionListener mListener;
    private View root;
    public DatePickerDialog.OnDateSetListener datePickerListener;
    private Button reset_plans_button;
    private User user;
    private static Date date;

    public static String plan_id;
    public static String ptitdej_id, collation_id, dej_entree_id, dej_plat_id, dej_dessert_id,
            gouter_id, diner_entree_id, diner_plat_id, diner_dessert_id;

    private TextView date_textView, ptitdej_textView, collation_textView, dej_entree_textView, dej_plat_textView
            , dej_dessert_textView, gouter_textView, diner_entree_textView, diner_plat_textView
            , diner_dessert_textView, generate_textView, generate_days_textView;

    private CardView ptitdej_cardView, collation_cardView, dej_cardView, gouter_cardView, diner_cardView;
    private EditText generate_days_editText;
    private ImageButton next_day_imageButton, previous_day_imageButton, change_date_button, generate_imageButton;
    private ImageView pnns_imageView;
    private ImageButton ptitdej_imageButton, collation_imageButton, dej_entree_imageButton
            , dej_plat_imageButton, dej_dessert_imageButton, gouter_imageButton
            , diner_entree_imageButton, diner_plat_imageButton, diner_dessert_imageButton;

    private PlanificationDataBase myPlanDB;

    private ArrayList<String> aliment_ids, aliment_names, aliment_calories, aliment_images;

    private ArrayList<String> ing_ids, ing_aliments, ing_quantities;

    private ArrayList<String> plat_ids, plat_titles, plat_categories, plat_ingredients, plat_images;

    private ArrayList<String> plan_ids, plan_users, plan_dates, plan_ptitdejs, plan_collations, plan_dejentrees
            , plan_dejplats, plan_dejdesserts, plan_gouters, plan_dinerentrees, plan_dinerplats, plan_dinerdesserts;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_calendrier, container, false);
        myPlanDB = new PlanificationDataBase(getContext());
        user = MainActivity.user;
        refreshPlans();

        ptitdej_cardView = root.findViewById(R.id.calendrier_ptitdej_cardView);
        collation_cardView = root.findViewById(R.id.calendrier_collation_cardView);
        dej_cardView = root.findViewById(R.id.calendrier_dej_cardView);
        gouter_cardView = root.findViewById(R.id.calendrier_gouter_cardView);
        diner_cardView = root.findViewById(R.id.calendrier_diner_cardView);

        ptitdej_textView = root.findViewById(R.id.plan_ptitdej_textView);
        collation_textView = root.findViewById(R.id.plan_collation_textView);
        dej_entree_textView = root.findViewById(R.id.plan_dej_entree_textView);
        dej_plat_textView = root.findViewById(R.id.plan_dej_plat_textView);
        dej_dessert_textView = root.findViewById(R.id.plan_dej_dessert_textView);
        gouter_textView = root.findViewById(R.id.plan_gouter_textView);
        diner_entree_textView = root.findViewById(R.id.plan_diner_entree_textView);
        diner_plat_textView = root.findViewById(R.id.plan_diner_plat_textView);
        diner_dessert_textView = root.findViewById(R.id.plan_diner_dessert_textView);
        date_textView = root.findViewById(R.id.calendrier_date_text_view);

        ptitdej_imageButton = root.findViewById(R.id.calendrier_ptitdej_imageButton);
        collation_imageButton = root.findViewById(R.id.calendrier_collation_imageButton);
        dej_entree_imageButton = root.findViewById(R.id.calendrier_dej_entree_imageButton);
        dej_plat_imageButton = root.findViewById(R.id.calendrier_dej_plat_imageButton);
        dej_dessert_imageButton = root.findViewById(R.id.calendrier_dej_dessert_imageButton);
        gouter_imageButton = root.findViewById(R.id.calendrier_gouter_imageButton);
        diner_entree_imageButton = root.findViewById(R.id.calendrier_diner_entree_imageButton);
        diner_plat_imageButton = root.findViewById(R.id.calendrier_diner_plat_imageButton);
        diner_dessert_imageButton = root.findViewById(R.id.calendrier_diner_dessert_imageButton);

        ptitdej_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), RecipeActivity.class);
                startActivity(intent);
                RecipeActivity.meal_id = ptitdej_id;
                RecipeActivity.meal_type = PlanificationDataBase.COLUMN_PTITDEJ;
            }
        });

        collation_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), RecipeActivity.class);
                startActivity(intent);
                RecipeActivity.meal_id = collation_id;
                RecipeActivity.meal_type = PlanificationDataBase.COLUMN_COLLATION;
            }
        });

        dej_entree_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), RecipeActivity.class);
                startActivity(intent);
                RecipeActivity.meal_id = dej_entree_id;
                RecipeActivity.meal_type = PlanificationDataBase.COLUMN_DEJENTREE;
            }
        });

        dej_plat_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), RecipeActivity.class);
                startActivity(intent);
                RecipeActivity.meal_id = dej_plat_id;
                RecipeActivity.meal_type = PlanificationDataBase.COLUMN_DEJPLAT;
            }
        });

        dej_dessert_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), RecipeActivity.class);
                startActivity(intent);
                RecipeActivity.meal_id = dej_dessert_id;
                RecipeActivity.meal_type = PlanificationDataBase.COLUMN_DEJDESSERT;

            }
        });

        gouter_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), RecipeActivity.class);
                startActivity(intent);
                RecipeActivity.meal_id = gouter_id;
                RecipeActivity.meal_type = PlanificationDataBase.COLUMN_GOUTER;
            }
        });

        diner_entree_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), RecipeActivity.class);
                startActivity(intent);
                RecipeActivity.meal_id = diner_entree_id;
                RecipeActivity.meal_type = PlanificationDataBase.COLUMN_DINERENTREE;
            }
        });

        diner_plat_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), RecipeActivity.class);
                startActivity(intent);
                RecipeActivity.meal_id = diner_plat_id;
                RecipeActivity.meal_type = PlanificationDataBase.COLUMN_DINERPLAT;
            }
        });

        diner_dessert_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getContext(), RecipeActivity.class);
                startActivity(intent);
                RecipeActivity.meal_id = diner_dessert_id;
                RecipeActivity.meal_type = PlanificationDataBase.COLUMN_DINERDESSERT    ;
            }
        });

        next_day_imageButton = root.findViewById(R.id.calendrier_right_arrow_imageButton);
        next_day_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = date.getNextDay();
                storePlanInTextViews((date).toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date_textView.setText(date.toStringLetters());
                }
            }
        });
        previous_day_imageButton = root.findViewById(R.id.calendrier_left_arrow_imageButton);
        previous_day_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = date.getPreviousDay();
                storePlanInTextViews((date).toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date_textView.setText(date.toStringLetters());
                }
            }
        });
        reset_plans_button = root.findViewById(R.id.calendrier_reset_plans_button);
        reset_plans_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                myPlanDB.deleteAllPlansFromUser(Integer.toString(user.getId()));
                refreshPlans();
                storePlanInTextViews(date.toString());
            }
        });

        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                String year1 = String.valueOf(selectedYear);
                String month1 = String.valueOf(selectedMonth + 1);
                String day1 = String.valueOf(selectedDay);
                //date.setText(day1 + "/" + month1 + "/" + year1);
                date = new Date(Integer.parseInt(day1), Integer.parseInt(month1), Integer.parseInt(year1));
                storePlanInTextViews(date.toString());
                date_textView.setText((date).toStringLetters());

            }
        };

        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date

// Create the DatePickerDialog instance
        final DatePickerDialog datePicker = new DatePickerDialog(getContext(),datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));


        change_date_button =root.findViewById(R.id.calendrier_change_date_button);

        change_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = Date.getToday();
            storePlanInTextViews(date.toString());
            date_textView.setText(date.toStringLetters());
        }


        generate_imageButton = root.findViewById(R.id.calendrier_generate_imageButton);
        generate_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GenerateActivity.class);
                startActivity(intent);
                GenerateActivity.date = date;
            }
        });

        return root;
    }

    void refreshPlans(){
        storePlanDataInArraysPlan();
        storePlanDataInArraysMeals();
        storePlanDataInArraysIngredients();
        storePlanDataInArraysAliments();
    }

    private void storePlanInTextViews(String date) {
        //Log.i("MyTAG","date = "+date);
        for (String element:plan_dates){
            //Log.i("MyTAG","element = "+element);
        }
        //Log.i("MyTAG", Integer.toString(user.getId()));
        if (plan_dates.indexOf(date)!=-1){
            //Log.i("MyTAG", plan_users.get(plan_dates.indexOf(date)));
        }

        ptitdej_imageButton.setImageDrawable(null);
        collation_imageButton.setImageDrawable(null);
        dej_entree_imageButton.setImageDrawable(null);
        dej_plat_imageButton.setImageDrawable(null);
        dej_dessert_imageButton.setImageDrawable(null);
        gouter_imageButton.setImageDrawable(null);
        diner_entree_imageButton.setImageDrawable(null);
        diner_plat_imageButton.setImageDrawable(null);
        diner_dessert_imageButton.setImageDrawable(null);

        if (plan_dates.contains(date)){
            Boolean run = true;
            for (int position=0;position<plan_dates.size();position++){
                if((Integer.toString(user.getId())).equals(plan_users.get(position)) && plan_dates.get(position).equals(date)){
                    //Log.i("MyTAG","yeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    ptitdej_textView.setText(plat_titles.get(Integer.parseInt(plan_ptitdejs.get(position))));
                    collation_textView.setText(plat_titles.get(Integer.parseInt(plan_collations.get(position))));
                    dej_entree_textView.setText(plat_titles.get(Integer.parseInt(plan_dejentrees.get(position))));
                    dej_plat_textView.setText(plat_titles.get(Integer.parseInt(plan_dejplats.get(position))));
                    dej_dessert_textView.setText(plat_titles.get(Integer.parseInt(plan_dejdesserts.get(position))));
                    gouter_textView.setText(plat_titles.get(Integer.parseInt(plan_gouters.get(position))));
                    diner_entree_textView.setText(plat_titles.get(Integer.parseInt(plan_dinerentrees.get(position))));
                    diner_plat_textView.setText(plat_titles.get(Integer.parseInt(plan_dinerplats.get(position))));
                    diner_dessert_textView.setText(plat_titles.get(Integer.parseInt(plan_dinerdesserts.get(position))));

                    ptitdej_id = plan_ptitdejs.get(position);
                    collation_id = plan_collations.get(position);
                    dej_entree_id = plan_dejentrees.get(position);
                    dej_plat_id = plan_dejplats.get(position);
                    dej_dessert_id = plan_dejdesserts.get(position);
                    gouter_id = plan_gouters.get(position);
                    diner_entree_id = plan_dinerentrees.get(position);
                    diner_plat_id = plan_dinerplats.get(position);
                    diner_dessert_id = plan_dinerdesserts.get(position);

                    ptitdej_imageButton.setImageBitmap(loadImageFromStorageFromImageDir(
                            plat_images.get(plat_ids.indexOf(ptitdej_id))));
                    collation_imageButton.setImageBitmap(loadImageFromStorageFromImageDir(
                            plat_images.get(plat_ids.indexOf(collation_id))));
                    dej_entree_imageButton.setImageBitmap(loadImageFromStorageFromImageDir(
                            plat_images.get(plat_ids.indexOf(dej_entree_id))));
                    dej_plat_imageButton.setImageBitmap(loadImageFromStorageFromImageDir(
                            plat_images.get(plat_ids.indexOf(dej_plat_id))));
                    dej_dessert_imageButton.setImageBitmap(loadImageFromStorageFromImageDir(
                            plat_images.get(plat_ids.indexOf(dej_dessert_id))));
                    gouter_imageButton.setImageBitmap(loadImageFromStorageFromImageDir(
                            plat_images.get(plat_ids.indexOf(gouter_id))));
                    diner_entree_imageButton.setImageBitmap(loadImageFromStorageFromImageDir(
                            plat_images.get(plat_ids.indexOf(diner_entree_id))));
                    diner_plat_imageButton.setImageBitmap(loadImageFromStorageFromImageDir(
                            plat_images.get(plat_ids.indexOf(diner_plat_id))));
                    diner_dessert_imageButton.setImageBitmap(loadImageFromStorageFromImageDir(
                            plat_images.get(plat_ids.indexOf(diner_dessert_id))));
                    run = false;
                    plan_id = plan_ids.get(position);
                }
            }
            if (run){

                ptitdej_textView.setText("");
                collation_textView.setText("");
                dej_entree_textView.setText("");
                dej_plat_textView.setText("");
                dej_dessert_textView.setText("");
                gouter_textView.setText("");
                diner_entree_textView.setText("");
                diner_plat_textView.setText("");
                diner_dessert_textView.setText("");

            }

        } else{
            ptitdej_textView.setText("");
            collation_textView.setText("");
            dej_entree_textView.setText("");
            dej_plat_textView.setText("");
            dej_dessert_textView.setText("");
            gouter_textView.setText("");
            diner_entree_textView.setText("");
            diner_plat_textView.setText("");
            diner_dessert_textView.setText("");
        }

        if (!hasImage(ptitdej_imageButton)){
            ptitdej_cardView.setVisibility(View.GONE);
        } else {ptitdej_cardView.setVisibility(View.VISIBLE);}

        if (!hasImage(collation_imageButton)){
            collation_cardView.setVisibility(View.GONE);
        } else {collation_cardView.setVisibility(View.VISIBLE);}
        if (!hasImage(dej_entree_imageButton)&& !hasImage(dej_plat_imageButton) && !hasImage(dej_dessert_imageButton)){
            dej_entree_imageButton.setClickable(hasImage(dej_entree_imageButton));
            dej_plat_imageButton.setClickable(hasImage(dej_plat_imageButton));
            dej_dessert_imageButton.setClickable(hasImage(dej_dessert_imageButton));
            dej_cardView.setVisibility(View.GONE);
        } else {dej_cardView.setVisibility(View.VISIBLE);}
        if (!hasImage(gouter_imageButton)){
            gouter_cardView.setVisibility(View.GONE);
        } else {gouter_cardView.setVisibility(View.VISIBLE);}
        if (!hasImage(diner_entree_imageButton)&& !hasImage(diner_plat_imageButton) && !hasImage(diner_dessert_imageButton)){
            diner_entree_imageButton.setClickable(hasImage(diner_entree_imageButton));
            diner_plat_imageButton.setClickable(hasImage(diner_plat_imageButton));
            diner_dessert_imageButton.setClickable(hasImage(diner_dessert_imageButton));
            diner_cardView.setVisibility(View.GONE);
        } else {diner_cardView.setVisibility(View.VISIBLE);}

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
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
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
        plat_ingredients = new ArrayList<>();

        Cursor cursor = myPlanDB.readAllDataMeals();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
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
        myPlanDB = new PlanificationDataBase(getContext());
        ing_ids = new ArrayList<>();
        ing_aliments = new ArrayList<>();
        ing_quantities = new ArrayList<>();

        Cursor cursor = myPlanDB.readAllDataIngredients();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                ing_ids.add(cursor.getString(0));
                ing_aliments.add(cursor.getString(1));
                ing_quantities.add(cursor.getString(2));
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
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                aliment_ids.add(cursor.getString(0));
                aliment_names.add(cursor.getString(1));
                aliment_images.add(cursor.getString(2));
                aliment_calories.add(cursor.getString(3));
            }
        }
    }

    private Bitmap loadImageFromStorageFromImageDir(String name) {
        String path = getContext().getDir("imagedir", Context.MODE_PRIVATE).getAbsolutePath();
        Bitmap b = null;
        try {
            File f = new File(path, name);
            //Log.i("MyTAG",f.toString());
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //Log.i("MyTAG", "notFound");
        } finally {
            return b;
        }
    }

    private boolean hasImage(ImageButton view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }
    public static Date getDate(){
        return date;
    }
}

