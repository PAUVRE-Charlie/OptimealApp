package com.example.optimealapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.optimealapp.User.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ProfilFragment extends Fragment {

    private View root;
    private User user;
    private ImageView avatar_imageView;
    private TextView name_textView, name_value_textView;
    private TextView age_textView, age_value_textView;
    private TextView adresse_textView, adresse_value_textView;
    private TextView gender_textView, gender_value_textView;
    private TextView height_textView, height_value_textView;
    private TextView weight_textView, weight_value_textView;
    private TextView email_textView, email_value_textView;
    private TextView nombre_textView, nombre_value_textView;
    private TextView sportif_textView, sportif_value_textView;
    private TextView regime_textView, regime_value_textView;
    private Button logout_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profil, container, false);

        user = MainActivity.user;

        avatar_imageView = root.findViewById(R.id.profile_imageView);
        name_textView = root.findViewById(R.id.profile_name_textView);
        name_value_textView = root.findViewById(R.id.profile_name_textView2);
        age_textView = root.findViewById(R.id.profile_age_textView);
        age_value_textView = root.findViewById(R.id.profile_age_textView2);
        adresse_textView = root.findViewById(R.id.profile_adresse_textView);
        adresse_value_textView = root.findViewById(R.id.profile_adresse_textView2);
        gender_textView = root.findViewById(R.id.profile_gender_textView);
        gender_value_textView = root.findViewById(R.id.profile_gender_textView2);
        height_textView = root.findViewById(R.id.profile_height_textView);
        height_value_textView = root.findViewById(R.id.profile_height_textView2);
        weight_textView = root.findViewById(R.id.profile_weight_textView);
        weight_value_textView = root.findViewById(R.id.profile_weight_textView2);
        email_textView = root.findViewById(R.id.profile_email_textView);
        email_value_textView = root.findViewById(R.id.profile_email_textView2);
        nombre_textView = root.findViewById(R.id.profile_nombre_textView);
        nombre_value_textView = root.findViewById(R.id.profile_nombre_textView2);
        sportif_textView = root.findViewById(R.id.profile_sportif_textView);
        sportif_value_textView = root.findViewById(R.id.profile_sportif_textView2);
        regime_textView = root.findViewById(R.id.profile_regime_textView);
        regime_value_textView = root.findViewById(R.id.profile_regime_textView2);

        storeProfileValues();

        logout_button = root.findViewById(R.id.profile_logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.login_preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.login_preference_stay_connected_boolean), false);
                editor.apply();
                Intent intent = new Intent(getContext(), LogInActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void storeProfileValues(){
        //Log.i("MyTAG", user.getAvatar());
        avatar_imageView.setImageBitmap(loadImageFromStorageFromImageDir(user.getAvatar()));
        name_value_textView.setText((user.getFirst_name() + " " + user.getName()));
        age_value_textView.setText((user.getAge() + " ans"));
        adresse_value_textView.setText(user.getAdresse());
        gender_value_textView.setText(user.getSexe());
        height_value_textView.setText(user.getTaille());
        weight_value_textView.setText(user.getPoids() + " kg");
        email_value_textView.setText(user.getEmail());
        nombre_value_textView.setText(user.getNombre());
        sportif_value_textView.setText(user.getSportif());
        regime_value_textView.setText(user.getRegime());
    }

    private Bitmap loadImageFromStorageFromImageDir(String name)
    {
        String path = getContext().getDir("imagedir", Context.MODE_PRIVATE).getAbsolutePath();
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
