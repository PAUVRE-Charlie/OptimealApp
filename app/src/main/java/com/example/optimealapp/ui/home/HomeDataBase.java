package com.example.optimealapp.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HomeDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "publications.db";
    private static final String TABLE_NAME = "Publications";
    private static int DATABASE_VERSION = 1;
    private static String COLUMN_ID = "_id";
    private static String COLUMN_AVATAR = "Avatar";
    private static String COLUMN_TITLE = "Title";
    private static String COLUMN_MEAL_TITLE = "Meal_Title";
    private static String COLUMN_MEAL_IMAGE = "Meal_Image";
    private static String COLUMN_LIKE_COUNT = "Likes";
    private static String COLUMN_DISLIKE_COUNT = "Dislikes";
    private static String COLUMN_COMMENTS_COUNT = "Comments";
    private Context context;
    public static int max_id;

    public HomeDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        max_id = 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.i("MyTAG","hello");
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AVATAR + " TEXT, " + COLUMN_TITLE + " TEXT, " + COLUMN_MEAL_TITLE + " TEXT, " + COLUMN_MEAL_IMAGE +
                " TEXT, " + COLUMN_LIKE_COUNT + " INTEGER, " + COLUMN_DISLIKE_COUNT + " INTEGER, " +
                COLUMN_COMMENTS_COUNT + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
    }

    public void addPublication(String avatar, String title, String meal_title, String meal_image,
                        int like_count, int dislike_count, int comment_count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();



        cv.put(COLUMN_AVATAR, avatar);
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_MEAL_TITLE, meal_title);
        cv.put(COLUMN_MEAL_IMAGE, meal_image);
        cv.put(COLUMN_LIKE_COUNT, like_count);
        cv.put(COLUMN_DISLIKE_COUNT, dislike_count);
        cv.put(COLUMN_COMMENTS_COUNT, comment_count);

        long result = db.insert(TABLE_NAME,null, cv);
/*
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }*/

    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id, String avatar, String title, String meal_title, String meal_image,
                    int like_count, int dislike_count, int comment_count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_AVATAR, avatar);
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_MEAL_TITLE, meal_title);
        cv.put(COLUMN_MEAL_IMAGE, meal_image);
        cv.put(COLUMN_LIKE_COUNT, like_count);
        cv.put(COLUMN_DISLIKE_COUNT, dislike_count);
        cv.put(COLUMN_COMMENTS_COUNT, comment_count);

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
