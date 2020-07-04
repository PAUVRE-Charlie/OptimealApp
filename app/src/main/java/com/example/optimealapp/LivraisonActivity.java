package com.example.optimealapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.optimealapp.User.User;

public class LivraisonActivity extends AppCompatActivity {

    private TextView user_textView, adresse_textView, total_textView;
    private ImageButton pay_button;
    private User user;
    private EditText numero_carte, cryptogramme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livraison);

        user = MainActivity.user;
        user_textView = findViewById(R.id.livraison_user_textView);
        adresse_textView = findViewById(R.id.livraison_adresse_textView);
        total_textView = findViewById(R.id.livraison_total_textView);
        numero_carte = findViewById(R.id.livraison_numero_carte_editText);
        cryptogramme = findViewById(R.id.livraison_cryptogramme_editText);

        user_textView.setText((user.getFirst_name() + " " + user.getName()));
        adresse_textView.setText(user.getAdresse());
        total_textView.setText("...$");

        @SuppressLint("CutPasteId") final Spinner month_spinner = (Spinner) findViewById(R.id.livraison_month_spinner);
        ArrayAdapter<CharSequence> month_adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(month_adapter);

        @SuppressLint("CutPasteId") final Spinner year_spinner = (Spinner) findViewById(R.id.livraison_year_spinner);
        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(year_adapter);

        pay_button = findViewById(R.id.livraison_pay_imageButton);
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numero_carte.getText().length() == 16 && cryptogramme.getText().length() == 3){
                    Toast.makeText(LivraisonActivity.this, "Paiement effectué", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LivraisonActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (numero_carte.getText().length()!=16){
                    Toast.makeText(LivraisonActivity.this, "Le numéro de carte n'a pas le bon nombre de chiffres", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LivraisonActivity.this, "Le cryptogramme visuel n'a pas le bon nombre de chiffres", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
