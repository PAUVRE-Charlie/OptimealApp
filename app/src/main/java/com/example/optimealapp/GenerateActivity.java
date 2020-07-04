package com.example.optimealapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.optimealapp.Date.Date;
import com.example.optimealapp.ui.calendrier.PlanificationDataBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class GenerateActivity extends AppCompatActivity {

    public static Date date;

    CheckBox ptitdej_checkBox, collation_checkBox, dej_entree_checkBox, dej_plat_checkBox
            , dej_dessert_checkBox, gouter_checkBox, diner_entree_checkBox
            , diner_plat_checkBox, diner_dessert_checkBox;

    EditText jours_editText;
    TextView jours_textView;
    Button generer_button;

    ImageButton date_imageButton;
    TextView date_textView;

    private PlanificationDataBase myPlanDB;
    private ArrayList<String> plan_ids, plan_users, plan_dates, plan_ptitdejs, plan_collations, plan_dejentrees
            , plan_dejplats, plan_dejdesserts, plan_gouters, plan_dinerentrees, plan_dinerplats, plan_dinerdesserts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        refreshPlans();

        jours_editText= findViewById(R.id.generer_editText);
        jours_textView = findViewById(R.id.generer_textView2);
        date_textView = findViewById(R.id.generer_date_text_view);

        ptitdej_checkBox = findViewById(R.id.generer_ptitdej_checkBox);
        collation_checkBox = findViewById(R.id.generer_collation_checkBox);
        dej_entree_checkBox = findViewById(R.id.generer_dej_entree_checkBox);
        dej_plat_checkBox = findViewById(R.id.generer_dej_plat_checkBox);
        dej_dessert_checkBox = findViewById(R.id.generer_dej_dessert_checkBox);
        gouter_checkBox = findViewById(R.id.generer_gouter_checkBox);
        diner_entree_checkBox = findViewById(R.id.generer_diner_entree_checkBox);
        diner_plat_checkBox = findViewById(R.id.generer_diner_plat_checkBox);
        diner_dessert_checkBox = findViewById(R.id.generer_diner_dessert_checkBox);

        generer_button = findViewById(R.id.generer_button);
        generer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!jours_editText.getText().toString().equals("")){
                    int N = Integer.parseInt(jours_editText.getText().toString());
                    if (N>0) {
                        Date date2 = date;
                        Boolean run =true;
                        for (int i = 0; i<N; i++){
                            if (plan_dates.contains(date2.toString()) && plan_users.get(plan_dates.indexOf(date2.toString())).equals(Integer.toString(MainActivity.user.getId()))){
                                Toast.makeText(GenerateActivity.this, "Il y a déja des plats générés pour ces dates"
                                        , Toast.LENGTH_LONG).show();
                                run = false;
                            }
                            date2 = date2.getNextDay();
                        }
                        if (run){
                            Boolean ptitdej, collation, dej_entree, dej_plat, dej_dessert, gouter,
                                    diner_entree, diner_plat, diner_dessert;
                            ptitdej = ptitdej_checkBox.isChecked();
                            collation = collation_checkBox.isChecked();
                            dej_entree = dej_entree_checkBox.isChecked();
                            dej_plat = dej_plat_checkBox.isChecked();
                            dej_dessert = dej_dessert_checkBox.isChecked();
                            gouter = gouter_checkBox.isChecked();
                            diner_entree = diner_entree_checkBox.isChecked();
                            diner_plat = diner_plat_checkBox.isChecked();
                            diner_dessert = diner_dessert_checkBox.isChecked();
                            //Log.i("MyTAG", "generate");
                            myPlanDB.generatePlansForNdays(N, MainActivity.user, date, ptitdej, collation,
                                    dej_entree, dej_plat, dej_dessert, gouter, diner_entree, diner_plat,
                                    diner_dessert);
                            Intent intent = new Intent (GenerateActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                String year1 = String.valueOf(selectedYear);
                String month1 = String.valueOf(selectedMonth + 1);
                String day1 = String.valueOf(selectedDay);
                //date.setText(day1 + "/" + month1 + "/" + year1);
                date = new Date(Integer.parseInt(day1), Integer.parseInt(month1), Integer.parseInt(year1));
                date_textView.setText((date).toStringLetters());

            }
        };

        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date

// Create the DatePickerDialog instance
        final DatePickerDialog datePicker = new DatePickerDialog(GenerateActivity.this,datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));


        date_imageButton =findViewById(R.id.generer_change_date_button);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date_textView.setText((new Date(cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))).toStringLetters());
        }

        date_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });



    }

    void refreshPlans(){
        storePlanDataInArraysPlan();
    }

    void storePlanDataInArraysPlan(){
        myPlanDB = new PlanificationDataBase(GenerateActivity.this);
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
            Toast.makeText(GenerateActivity.this, "No data", Toast.LENGTH_SHORT).show();
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

}
