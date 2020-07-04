package com.example.optimealapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.optimealapp.User.User;
import com.example.optimealapp.User.UserDataBase;

import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {

    public static EditText email_editText, mdp_editText;
    private Button login_button;
    private CheckBox checkBox;
    public static ArrayList<User> users;
    public ArrayList<String> user_ids, user_names, user_firstnames, user_ages, user_avatars, user_emails,
            user_passwords, user_adresses, user_sexes, user_poids, user_taille, user_nombre, user_sportif,
            user_regime;
    public static UserDataBase myUserDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getUsers();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.login_preference_file_key), Context.MODE_PRIVATE);
        Boolean stay_connected = sharedPref.getBoolean(getString(R.string.login_preference_stay_connected_boolean), false);
        //Log.i("MyTAG","stay_connected"+stay_connected.toString());
        if (stay_connected){

            int default_id = 0;
            int last_user_id = sharedPref.getInt(getString(R.string.login_preference_last_user_id), default_id);
            //Log.i("MyTAG",Integer.toString(last_user_id));
            //Log.i("MyTAG",user_ids.toString());
            int last_user=user_ids.indexOf(Integer.toString(last_user_id));
            if (last_user!=-1){
                MainActivity.user = users.get(last_user);
            }
            else{
                sharedPref = getSharedPreferences(getString(R.string.login_preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.login_preference_stay_connected_boolean), false);
                editor.apply();
            }

            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
        }
        email_editText = findViewById(R.id.login_email_editText);
        mdp_editText = findViewById(R.id.login_mdp_editText);
        checkBox = findViewById(R.id.login_checkBox);

        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email_editText.getText().toString().trim().equals("") &&
                        !mdp_editText.getText().toString().trim().equals("")){
                    login_button.setBackgroundColor(Color.BLACK);
                    Boolean unknown_user = true;
                    //Log.i("MyTAG","hey");
                    for (User user:users){
                        //Log.i("MyTAG","heyyyy"+user.getEmail()+user.getPassword());
                        if (user.getEmail().equals(email_editText.getText().toString().trim())
                                && user.getPassword().equals(mdp_editText.getText().toString().trim())){
                            //Log.i("MyTAG", "yeeees");
                            MainActivity.user = user;
                            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.login_preference_file_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean(getString(R.string.login_preference_stay_connected_boolean), checkBox.isChecked());
                            editor.putInt(getString(R.string.login_preference_last_user_id),user.getId());
                            editor.apply();
                            /*
                            Boolean stay_connected = sharedPref.getBoolean(getString(R.string.login_preference_stay_connected_boolean), false);
                            int last_user_id = sharedPref.getInt(getString(R.string.login_preference_last_user_id), -1);
                            Log.i("MyTAG","stay_connected"+stay_connected.toString());
                            Log.i("MyTAG","last_user"+last_user_id);
                            Log.i("MyTAG","isSelected"+checkBox.isChecked());
                            */
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                            unknown_user = false;
                        }
                    }
                    if (unknown_user){
                        Intent intent = new Intent(LogInActivity.this, InscriptionActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    void getUsers(){
        myUserDB = new UserDataBase(LogInActivity.this);
        user_ids = new ArrayList<>();
        user_avatars = new ArrayList<>();
        user_names = new ArrayList<>();
        user_firstnames = new ArrayList<>();
        user_ages = new ArrayList<>();
        user_adresses = new ArrayList<>();
        user_sexes = new ArrayList<>();
        user_poids = new ArrayList<>();
        user_taille = new ArrayList<>();
        user_nombre = new ArrayList<>();
        user_sportif = new ArrayList<>();
        user_regime = new ArrayList<>();

        user_emails = new ArrayList<>();
        user_passwords = new ArrayList<>();
        users = new ArrayList<>();
        storeDataUsersInArrays();

        for (int i =0;i<user_ids.size();i++){
            Log.i("MyTAG",user_names.get(i));
            users.add(new User(Integer.parseInt(user_ids.get(i)), user_firstnames.get(i),
                    user_names.get(i), user_emails.get(i), user_passwords.get(i), user_ages.get(i),
                    user_avatars.get(i), user_adresses.get(i), user_sexes.get(i), user_poids.get(i)
                    , user_taille.get(i), user_nombre.get(i), user_sportif.get(i), user_regime.get(i)));
        }
    }

    private void storeDataUsersInArrays() {
        Cursor cursor = myUserDB.readAllData();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            //Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                user_ids.add(cursor.getString(0));
                //Log.i("MyTAG", "hohoho");
                user_avatars.add(cursor.getString(1));
                //Log.i("MyTAG", "hihihi");
                user_names.add(cursor.getString(2));
                user_firstnames.add(cursor.getString(3));
                user_ages.add(cursor.getString(4));
                user_emails.add(cursor.getString(5));
                user_passwords.add(cursor.getString(6));
                user_adresses.add(cursor.getString(7));
                user_sexes.add(cursor.getString(8));
                user_poids.add(cursor.getString(9));
                user_taille.add(cursor.getString(10));
                user_nombre.add(cursor.getString(11));
                user_sportif.add(cursor.getString(12));
                user_regime.add(cursor.getString(13));
            }
        }
    }



}
