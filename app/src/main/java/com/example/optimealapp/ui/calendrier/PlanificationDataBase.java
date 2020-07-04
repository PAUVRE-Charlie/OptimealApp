package com.example.optimealapp.ui.calendrier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.optimealapp.Date.Date;
import com.example.optimealapp.User.User;

import java.util.ArrayList;
import java.util.Random;

public class PlanificationDataBase extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "planifications.db";
    public  static final String TABLE_NAME_PLAN = "Planifications";
    private static final int DATABASE_VERSION = 1;
    public static String COLUMN_ID = "_id";
    public static String COLUMN_USER = "User_id";
    public static String COLUMN_DATE = "Date";
    public static String COLUMN_PTITDEJ = "Petit_Déjeuner";
    public static String COLUMN_COLLATION = "Collation";
    public static String COLUMN_DEJENTREE = "Déjeuner_entrée";
    public static String COLUMN_DEJPLAT = "Déjeuner_plat";
    public static String COLUMN_DEJDESSERT = "Déjeuner_dessert";
    public static String COLUMN_GOUTER = "Goûter";
    public static String COLUMN_DINERENTREE = "Diner_entrée";
    public static String COLUMN_DINERPLAT = "Diner_plat";
    public static String COLUMN_DINERDESSERT = "Diner_dessert";

    public static final String TABLE_NAME_PLATS = "Plats";
    private static String COLUMN_IMAGE_PLAT = "Images";
    private static String COLUMN_TITLE = "Titre";
    private static String COLUMN_CATEGORIE = "Catégorie";
    private static String COLUMN_SAISON = "Saison";
    private static String COLUMN_CALORIES_PLAT = "Calories";

    public static final String TABLE_NAME_INGREDIENTS = "Ingrédients";
    private static String COLUMN_ALIMENT = "Aliment";
    private static String COLUMN_QUANTITY = "Quantité";
    private static String COLUMN_MEAL = "Plat";

    public static final String TABLE_NAME_ALIMENTS = "Aliments";
    private static String COLUMN_NAME = "Nom";
    private static String COLUMN_IMAGE_ALIMENT = "Image";
    private static String COLUMN_CALORIES = "Calories";

    public static final String TABLE_NAME_INGREDIENTS_BONUS = "Ingrédients_bonus";

    public  static final String TABLE_NAME_INGREDIENTS_MODIF = "Ingrédients_modifs";

    public PlanificationDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.i("MyTAG","hello");
        String query = "CREATE TABLE " + TABLE_NAME_PLAN + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " INTEGER, " + COLUMN_DATE + " TEXT, " + COLUMN_PTITDEJ + " INTEGER, " + COLUMN_COLLATION +
                " INTEGER, " + COLUMN_DEJENTREE + " INTEGER, " + COLUMN_DEJPLAT + " INTEGER, " + COLUMN_DEJDESSERT +
                " INTEGER, " + COLUMN_GOUTER + " INTEGER, " + COLUMN_DINERENTREE + " INTEGER, " + COLUMN_DINERPLAT +
                " INTGEGER, " + COLUMN_DINERDESSERT + " INTEGER);";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + TABLE_NAME_PLATS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_PLAT + " TEXT, " + COLUMN_TITLE + " TEXT, " + COLUMN_CATEGORIE + " TEXT, " + COLUMN_SAISON +
                " TEXT, " + COLUMN_CALORIES_PLAT  + " TEXT);";
        db.execSQL(query2);

        String query3 = "CREATE TABLE " + TABLE_NAME_INGREDIENTS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ALIMENT + " INTEGER, " + COLUMN_QUANTITY + " INTEGER, " + COLUMN_MEAL + " TEXT);";
        db.execSQL(query3);

        String query4 = "CREATE TABLE " + TABLE_NAME_ALIMENTS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " + COLUMN_IMAGE_ALIMENT + " TEXT, " + COLUMN_CALORIES + " INTEGER);";
        db.execSQL(query4);

        //Log.i("MyTAG", "hohhhhhhhhooooooooooooo");
        String query5 = "CREATE TABLE " + TABLE_NAME_INGREDIENTS_BONUS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ALIMENT + " INTEGER, " + COLUMN_QUANTITY + " INTEGER);";
        db.execSQL(query5);

        String query6 = "CREATE TABLE " + TABLE_NAME_INGREDIENTS_MODIF + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ALIMENT + " INTEGER, " + COLUMN_QUANTITY + " INTEGER);";
        db.execSQL(query6);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PLAN );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PLATS );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INGREDIENTS );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ALIMENTS );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INGREDIENTS_MODIF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INGREDIENTS_BONUS );
    }

    public void deleteAllModifAndBonus(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_INGREDIENTS_MODIF);
        db.execSQL("DELETE FROM " + TABLE_NAME_INGREDIENTS_BONUS);
    }

    public void deleteAllPlans(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_PLAN);
    }

    public void deleteAllPlansFromUser(String user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_PLAN + " WHERE " + COLUMN_USER + " ='" + user_id + "'");
    }

    public Cursor readAllDataPlan(){
        String query = "SELECT * FROM " + TABLE_NAME_PLAN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
   }


    public Cursor readAllDataMeals(){
        String query = "SELECT * FROM " + TABLE_NAME_PLATS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    public Cursor readAllDataIngredients(){
        String query = "SELECT * FROM " + TABLE_NAME_INGREDIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readAllDataIngredientsBonus(){
        String query = "SELECT * FROM " + TABLE_NAME_INGREDIENTS_BONUS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readAllDataIngredientsModif(){
        String query = "SELECT * FROM " + TABLE_NAME_INGREDIENTS_MODIF;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readAllDataAliments(){
        String query = "SELECT * FROM " + TABLE_NAME_ALIMENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void deleteRow(String TABLE_NAME, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?", new String[]{id});
        /*if (result == -1){
            Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Row deleted", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void generatePlansForNdays(int N, User user, Date date, Boolean ptitdej, Boolean collation,
                                      Boolean dej_entree, Boolean dej_plat, Boolean dej_dessert,
                                      Boolean gouter, Boolean diner_entree, Boolean diner_plat,
                                      Boolean diner_dessert){
        Log.i("MyTAG", "GENERATE");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Random rd = new Random();
        Cursor cursor_plats = db.rawQuery("SELECT _id FROM " + TABLE_NAME_PLATS + " WHERE " +
                COLUMN_CATEGORIE + " ='plat'", null);
        Cursor cursor_entrees = db.rawQuery("SELECT _id FROM " + TABLE_NAME_PLATS + " WHERE " +
                COLUMN_CATEGORIE + " ='entree'", null);
        Cursor cursor_desserts = db.rawQuery("SELECT _id FROM " + TABLE_NAME_PLATS + " WHERE " +
                COLUMN_CATEGORIE + " ='dessert'", null);
        Cursor cursor_gouters = db.rawQuery("SELECT _id FROM " + TABLE_NAME_PLATS + " WHERE " +
                COLUMN_CATEGORIE + " ='gouter'", null);
        Cursor cursor_ptitdejs = db.rawQuery("SELECT _id FROM " + TABLE_NAME_PLATS + " WHERE " +
                COLUMN_CATEGORIE + " ='petit_dejeuner'", null);

        ArrayList<String> plats_ids = new ArrayList<>();
        ArrayList<String> entrees_ids = new ArrayList<>();
        ArrayList<String> desserts_ids = new ArrayList<>();
        ArrayList<String> gouters_ids = new ArrayList<>();
        ArrayList<String> ptitdejs_ids = new ArrayList<>();
        while (cursor_plats.moveToNext()){
            plats_ids.add(cursor_plats.getString(0));
        }
        while (cursor_entrees.moveToNext()){
            entrees_ids.add(cursor_entrees.getString(0));
        }
        while (cursor_desserts.moveToNext()){
            desserts_ids.add(cursor_desserts.getString(0));
        }
        while (cursor_gouters.moveToNext()){
            gouters_ids.add(cursor_gouters.getString(0));
        }
        while (cursor_ptitdejs.moveToNext()){
            ptitdejs_ids.add(cursor_ptitdejs.getString(0));
        }

        Log.i("MyTAG", "entree" + entrees_ids);
        Log.i("MyTAG", "entree" + dej_entree);
        for (int i=0; i<N; i++){
            cv.put(COLUMN_USER, Integer.toString(user.getId()));
            cv.put(COLUMN_DATE, date.toString());
            if (ptitdej){
                cv.put(COLUMN_PTITDEJ, ptitdejs_ids.get(rd.nextInt(ptitdejs_ids.size())));
            } else {
                cv.put(COLUMN_PTITDEJ, "0");
            }
            if (collation){
                cv.put(COLUMN_COLLATION, "0");
            } else {
                cv.put(COLUMN_COLLATION, "0");
            }
            if (dej_entree){
                cv.put(COLUMN_DEJENTREE, entrees_ids.get(rd.nextInt(entrees_ids.size())));
            } else {
                cv.put(COLUMN_DEJENTREE, "0");
            }
            if (dej_plat){
                cv.put(COLUMN_DEJPLAT, plats_ids.get(rd.nextInt(plats_ids.size())));
            } else {
                cv.put(COLUMN_DEJPLAT, "0");
            }
            if (dej_dessert){
                cv.put(COLUMN_DEJDESSERT, desserts_ids.get(rd.nextInt(desserts_ids.size())));
            } else {
                cv.put(COLUMN_DEJDESSERT, "0");
            }
            if (gouter){
                cv.put(COLUMN_GOUTER, gouters_ids.get(rd.nextInt(gouters_ids.size())));
            } else {
                cv.put(COLUMN_GOUTER, "0");
            }
            if (diner_entree){
                cv.put(COLUMN_DINERENTREE, entrees_ids.get(rd.nextInt(entrees_ids.size())));
            } else {
                cv.put(COLUMN_DINERENTREE, "0");
            }
            if (diner_plat){
                cv.put(COLUMN_DINERPLAT, plats_ids.get(rd.nextInt(plats_ids.size())));
            } else {
                cv.put(COLUMN_DINERPLAT, "0");
            }
            if (diner_dessert){
                cv.put(COLUMN_DINERDESSERT, desserts_ids.get(rd.nextInt(desserts_ids.size())));
            } else {
                cv.put(COLUMN_DINERDESSERT, "0");
            }
            db.insert(TABLE_NAME_PLAN,null, cv);
            date = date.getNextDay();
        }
        cursor_plats.close();
        cursor_desserts.close();
        cursor_entrees.close();
        cursor_gouters.close();
        cursor_ptitdejs.close();
    }

    public void addAliment(String aliment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, aliment);
        cv.put(COLUMN_IMAGE_ALIMENT, "addition");
        cv.put(COLUMN_CALORIES, -1);
        //Log.i("MyTAG", aliment);
        long result = db.insert(TABLE_NAME_ALIMENTS,null, cv);
    }

    public void addIngredientToList(String table_name, String aliment, String quantité, String plat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String id;
        aliment = aliment.replace("'","_");
        Cursor cursor = db.rawQuery("SELECT _id FROM "+ TABLE_NAME_ALIMENTS + " WHERE " +  COLUMN_NAME + " = '" + aliment + "'", null);
        if (cursor.moveToNext()) {
            id = cursor.getString(0);
        } else {
            Cursor cursor2 = db.rawQuery("SELECT MAX(_id) FROM " + TABLE_NAME_ALIMENTS, null);
            cursor2.moveToNext();
            id = Integer.toString(Integer.parseInt(cursor2.getString(0))+1);
            addAliment(aliment);
            cursor2.close();
        }
        cv.put(COLUMN_ALIMENT, Integer.parseInt(id));
        cv.put(COLUMN_QUANTITY, Integer.parseInt(quantité));
        cv.put(COLUMN_MEAL, plat);

        long result = db.insert(table_name,null, cv);
/*
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }*/
        cursor.close();
    }

    public void updateDataPlan(String row_id, String user_id, String date, String ptitdej, String collation,
                        String dej_entree, String dej_plat, String dej_dessert, String gouter,
                        String diner_entree, String diner_plat, String diner_dessert){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER, user_id);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_PTITDEJ, ptitdej);
        cv.put(COLUMN_COLLATION, collation);
        cv.put(COLUMN_DEJENTREE, dej_entree);
        cv.put(COLUMN_DEJPLAT, dej_plat);
        cv.put(COLUMN_DEJDESSERT, dej_dessert);
        cv.put(COLUMN_GOUTER, gouter);
        cv.put(COLUMN_DINERENTREE, diner_entree);
        cv.put(COLUMN_DINERPLAT, diner_plat);
        cv.put(COLUMN_DINERDESSERT, diner_dessert);

        db.update(TABLE_NAME_PLAN, cv, "_id=?", new String[]{row_id});

    }

    public Cursor getIngFromPlat(String plat){
        String query ="SELECT _id FROM "+ TABLE_NAME_INGREDIENTS + " WHERE " +  COLUMN_MEAL + " = '" + plat + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

}
