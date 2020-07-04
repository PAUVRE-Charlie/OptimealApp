package com.example.optimealapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.optimealapp.User.User;
import com.example.optimealapp.ui.home.HomeDataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class AddPublicationActivity extends AppCompatActivity {

    Button publication_button;
    ImageButton change_photo_button;
    EditText meal_editText;
    ImageView imageView;
    HomeDataBase myPublicationsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);
        meal_editText = findViewById(R.id.add_pub_meal_title);
        imageView = findViewById(R.id.add_pub_imageView);
        change_photo_button = findViewById(R.id.add_pub_imageButton);
        change_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        publication_button = findViewById(R.id.add_pub_button);
        publication_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                String meal_title = meal_editText.getText().toString().trim();
                String meal_image_str = Integer.toString(getRandomNumberInRange(999999999));
                saveToInternalStorage(bitmap, meal_image_str);
                User user = MainActivity.user;
                myPublicationsDB = new HomeDataBase(AddPublicationActivity.this);
                myPublicationsDB.addPublication("avatar2",
                        user.getFirst_name() + " " + user.getName() + " a posté ceci :",
                        meal_title, meal_image_str,0, 0, 0);
                Intent intent = new Intent(AddPublicationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private String saveToInternalStorage(Bitmap bitmapImage, String name){
        ContextWrapper cw = new ContextWrapper(AddPublicationActivity.this);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imagedir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
            //Log.i("MyTAG","fuckkk");
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private static int getRandomNumberInRange(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }
}
