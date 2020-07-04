package com.example.optimealapp.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "Users";
    private static int DATABASE_VERSION = 1;
    private static String COLUMN_ID = "_id";
    private static String COLUMN_AVATAR = "Avatar";
    private static String COLUMN_NAME = "Name";
    private static String COLUMN_FIRSTNAME = "First_Name";
    private static String COLUMN_AGE = "Age";
    private static String COLUMN_EMAIL = "Email";
    private static String COLUMN_PASSWORD = "Password";
    private static String COLUMN_ADRESSE = "Adresse";
    private static String COLUMN_SEXE = "Sexe";
    private static String COLUMN_POIDS = "Poids";
    private static String COLUMN_TAILLE = "Taille";
    private static String COLUMN_NOMBRE = "Nombre_de_personnes";
    private static String COLUMN_SPORTIF = "Sportif";
    private static String COLUMN_REGIME = "Regime";
    private Context context;

    public UserDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AVATAR + " TEXT, " + COLUMN_NAME + " TEXT, " + COLUMN_FIRSTNAME + " TEXT, " + COLUMN_AGE +
                " TEXT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ADRESSE + " TEXT, " + COLUMN_SEXE + " TEXT, " + COLUMN_POIDS +
                " TEXT, " + COLUMN_TAILLE + " TEXT, " + COLUMN_NOMBRE + " TEXT, " + COLUMN_SPORTIF + " TEXT, " +
                COLUMN_REGIME +" TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
    }

    public void addUser(String avatar, String name, String first_name, String age, String email,
                        String password, String adresse, String sexe, String poids, String taille,
                        String nombre, String sportif, String regime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_AVATAR, avatar);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_FIRSTNAME, first_name);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_ADRESSE, adresse);
        cv.put(COLUMN_SEXE, sexe);
        cv.put(COLUMN_POIDS, poids);
        cv.put(COLUMN_TAILLE, taille);
        cv.put(COLUMN_NOMBRE, nombre);
        cv.put(COLUMN_SPORTIF, sportif);
        cv.put(COLUMN_REGIME, regime);

        long result = db.insert(TABLE_NAME,null, cv);
/*
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }*/

    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id, String avatar, String name, String first_name, String age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_AVATAR, avatar);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_FIRSTNAME, first_name);
        cv.put(COLUMN_AGE, age);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        /*
        if (result == -1){
            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
        }*/
    }
    void deleteRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?", new String[]{id});
        /*if (result == -1){
            Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Row deleted", Toast.LENGTH_SHORT).show();
        }*/
    }



}
