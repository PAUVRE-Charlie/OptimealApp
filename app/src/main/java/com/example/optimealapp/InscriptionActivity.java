package com.example.optimealapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.optimealapp.User.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class InscriptionActivity extends AppCompatActivity {

    private SeekBar age_bar;
    private ImageView avatar_imageView;
    private Button button;
    private ImageButton add_image_button;
    private TextView email_textView, age_textView;
    private EditText nom_editText, prenom_editText, adresse_editText, taille_m_editText,
    taille_cm_editText, poids_editText, nombre_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        age_bar = findViewById(R.id.inscription_age_seekBar);
        email_textView = findViewById(R.id.inscription_email_textView2);
        age_textView = findViewById(R.id.inscription_age_textView2);
        nom_editText = findViewById(R.id.inscription_name_editText);
        prenom_editText = findViewById(R.id.inscription_prenom_editText);
        adresse_editText = findViewById(R.id.inscription_adresse_editText);
        taille_m_editText = findViewById(R.id.inscription_height_meter_editText);
        taille_cm_editText = findViewById(R.id.inscription_cm_editText);
        poids_editText = findViewById(R.id.inscription_weight_editText);
        nombre_editText = findViewById(R.id.inscription_nombre_editText);
        button = findViewById(R.id.inscription_button);
        avatar_imageView = findViewById(R.id.inscription_imageView);
        add_image_button = findViewById(R.id.inscription_add_image_imageButton);

        age_textView.setText("0");
        age_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                age_textView.setText(Integer.toString(age_bar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        email_textView.setText(LogInActivity.email_editText.getText().toString().trim());

        add_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        @SuppressLint("CutPasteId") final Spinner sportif_spinner = (Spinner) findViewById(R.id.inscription_sportif_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sportif_adapter = ArrayAdapter.createFromResource(this,
                R.array.sportif_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        sportif_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sportif_spinner.setAdapter(sportif_adapter);

        @SuppressLint("CutPasteId") final Spinner regime_spinner = (Spinner) findViewById(R.id.inscription_regime_spinner);
        ArrayAdapter<CharSequence> regime_adapter = ArrayAdapter.createFromResource(this,
                R.array.regime_array, android.R.layout.simple_spinner_item);
        regime_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regime_spinner.setAdapter(regime_adapter);

        @SuppressLint("CutPasteId") final Spinner genre_spinner = (Spinner) findViewById(R.id.inscription_genre_spinner);
        ArrayAdapter<CharSequence> genre_adapter = ArrayAdapter.createFromResource(this,
                R.array.genre_array, android.R.layout.simple_spinner_item);
        genre_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genre_spinner.setAdapter(genre_adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nom_editText.getText().toString().trim().equals("") &&
                        !prenom_editText.getText().toString().trim().equals("") &&
                        !age_textView.getText().toString().trim().equals("") &&
                        !adresse_editText.getText().toString().trim().equals("") &&
                        !poids_editText.getText().toString().trim().equals("") &&
                        !taille_m_editText.getText().toString().trim().equals("") &&
                        !taille_cm_editText.getText().toString().trim().equals("") &&
                        !nombre_editText.getText().toString().trim().equals("")){
                    button.setBackgroundColor(Color.BLACK);
                    Bitmap bitmap = ((BitmapDrawable)avatar_imageView.getDrawable()).getBitmap();
                    String avatar_str = Integer.toString(getRandomNumberInRange(999999999));
                    saveToInternalStorage(bitmap, avatar_str);

                    String nom = nom_editText.getText().toString().trim();
                    String prenom = prenom_editText.getText().toString().trim();
                    String age = age_textView.getText().toString().trim();
                    //Log.i("MyTAG", age);
                    String email = LogInActivity.email_editText.getText().toString().trim();
                    String password = LogInActivity.mdp_editText.getText().toString().trim();
                    String adresse = adresse_editText.getText().toString().trim();
                    String sexe = genre_spinner.getSelectedItem().toString();
                    String poids = poids_editText.getText().toString().trim();
                    String taille = taille_m_editText.getText().toString().trim() + "m" +
                            taille_cm_editText.getText().toString().trim();
                    String nombre = nombre_editText.getText().toString().trim();
                    String sportif = sportif_spinner.getSelectedItem().toString();
                    String regime = regime_spinner.getSelectedItem().toString();

                    LogInActivity.myUserDB.addUser(avatar_str, nom, prenom, age, email, password,
                            adresse, sexe, poids, taille, nombre, sportif, regime);

                    //Log.i("MyTAG", age);

                    Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
                    startActivity(intent);
                    int id = LogInActivity.users.size()+1;
                    MainActivity.user = new User(id, prenom, nom, email, password,age, avatar_str,
                            adresse, sexe, poids, taille, nombre, sportif, regime);
                }
            }
        });




    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                avatar_imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String name){
        ContextWrapper cw = new ContextWrapper(InscriptionActivity.this);
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
