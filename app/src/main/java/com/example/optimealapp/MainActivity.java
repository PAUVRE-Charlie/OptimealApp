package com.example.optimealapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.optimealapp.User.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextWrapper cw = new ContextWrapper(MainActivity.this);
        File directory = cw.getDir("imagedir", Context.MODE_PRIVATE);
        saveToInternalStorageFromDrawable(R.drawable.burger_bbq,"burger_bbq");

        if (!imageStored(R.drawable.abricot, "abricot")){
            saveToInternalStorageFromDrawable(R.drawable.abricot, "abricot");
            saveToInternalStorageFromDrawable(R.drawable.addition, "addition");
            saveToInternalStorageFromDrawable(R.drawable.airelle, "airelle");
            saveToInternalStorageFromDrawable(R.drawable.alcool, "amande");
            saveToInternalStorageFromDrawable(R.drawable.ananas, "ananas");
            saveToInternalStorageFromDrawable(R.drawable.aperitif, "aperitif");
            saveToInternalStorageFromDrawable(R.drawable.artichaud, "artichaud");
            saveToInternalStorageFromDrawable(R.drawable.asperge, "asperge");
            saveToInternalStorageFromDrawable(R.drawable.aubergine, "aubergine");
            saveToInternalStorageFromDrawable(R.drawable.avatar2, "avatar2");
            saveToInternalStorageFromDrawable(R.drawable.avocat, "avocat");
            saveToInternalStorageFromDrawable(R.drawable.bacon, "bacon");
            saveToInternalStorageFromDrawable(R.drawable.banane, "banane");
            saveToInternalStorageFromDrawable(R.drawable.barre, "barre");
            saveToInternalStorageFromDrawable(R.drawable.betterave, "betterave");
            saveToInternalStorageFromDrawable(R.drawable.beurre, "beurre");
            saveToInternalStorageFromDrawable(R.drawable.biere, "biere");
            saveToInternalStorageFromDrawable(R.drawable.biscuit, "biscuit");
            saveToInternalStorageFromDrawable(R.drawable.brochettes_de_lotte_et_saumon_jardiniere_de_printemps, "brochettes_de_lotte_et_saumon_jardiniere_de_printemps");
            saveToInternalStorageFromDrawable(R.drawable.brocoli, "brocoli");
            saveToInternalStorageFromDrawable(R.drawable.burger_bbq, "burger_bbq");
            saveToInternalStorageFromDrawable(R.drawable.cafe, "cafe");
            saveToInternalStorageFromDrawable(R.drawable.cake_au_citron, "cake_au_citron");
            saveToInternalStorageFromDrawable(R.drawable.calamar, "calamar");
            saveToInternalStorageFromDrawable(R.drawable.carrote, "carrote");
            saveToInternalStorageFromDrawable(R.drawable.celeri, "celeri");
            saveToInternalStorageFromDrawable(R.drawable.champagne, "champagne");
            saveToInternalStorageFromDrawable(R.drawable.champignons, "champignons");
            saveToInternalStorageFromDrawable(R.drawable.chataigne, "chataigne");
            saveToInternalStorageFromDrawable(R.drawable.chips, "chips");
            saveToInternalStorageFromDrawable(R.drawable.chocolat, "chocolat");
            saveToInternalStorageFromDrawable(R.drawable.chou, "chou");
            saveToInternalStorageFromDrawable(R.drawable.citron, "citron");
            saveToInternalStorageFromDrawable(R.drawable.citrouille, "citrouille");
            saveToInternalStorageFromDrawable(R.drawable.concombre, "concombre");
            saveToInternalStorageFromDrawable(R.drawable.condiment, "condiment");
            saveToInternalStorageFromDrawable(R.drawable.cornichon, "cornichon");
            saveToInternalStorageFromDrawable(R.drawable.courgette, "courgette");
            saveToInternalStorageFromDrawable(R.drawable.creme, "creme");
            saveToInternalStorageFromDrawable(R.drawable.crepe, "crepe");
            saveToInternalStorageFromDrawable(R.drawable.crepes, "crepes");
            saveToInternalStorageFromDrawable(R.drawable.crustace, "crustace");
            saveToInternalStorageFromDrawable(R.drawable.curry_agneau_coco, "curry_agneau_coco");
            saveToInternalStorageFromDrawable(R.drawable.dorade_four_pommes_de_terre, "dorade_four_pommes_de_terre");
            saveToInternalStorageFromDrawable(R.drawable.eau, "eau");
            saveToInternalStorageFromDrawable(R.drawable.epice, "epice");
            saveToInternalStorageFromDrawable(R.drawable.farine, "farine");
            saveToInternalStorageFromDrawable(R.drawable.fleur, "fleur");
            saveToInternalStorageFromDrawable(R.drawable.fraise, "fraise");
            saveToInternalStorageFromDrawable(R.drawable.framboise, "framboise");
            saveToInternalStorageFromDrawable(R.drawable.fromage, "fromage");
            saveToInternalStorageFromDrawable(R.drawable.fruit_de_la_passion, "fruit_de_la_passion");
            saveToInternalStorageFromDrawable(R.drawable.galette_de_ble_legumes_printemps, "galette_de_ble_legumes_printemps");
            saveToInternalStorageFromDrawable(R.drawable.gateau, "gateau");
            saveToInternalStorageFromDrawable(R.drawable.hamburger, "hamburger");
            saveToInternalStorageFromDrawable(R.drawable.haricot_sec, "haricot_sec");
            saveToInternalStorageFromDrawable(R.drawable.haricot_vert, "haricot_vert");
            saveToInternalStorageFromDrawable(R.drawable.hot_dog, "hot_dog");
            saveToInternalStorageFromDrawable(R.drawable.huile, "huile");
            saveToInternalStorageFromDrawable(R.drawable.jus, "jus");
            saveToInternalStorageFromDrawable(R.drawable.kefta_veau_menthe, "kefta_veau_menthe");
            saveToInternalStorageFromDrawable(R.drawable.kiwi, "kiwi");
            saveToInternalStorageFromDrawable(R.drawable.lait, "lait");
            saveToInternalStorageFromDrawable(R.drawable.legumes_verts, "legumes_verts");
            saveToInternalStorageFromDrawable(R.drawable.litchi, "litchi");
            saveToInternalStorageFromDrawable(R.drawable.logooo, "logooo");
            saveToInternalStorageFromDrawable(R.drawable.mais, "mais");
            saveToInternalStorageFromDrawable(R.drawable.mangue, "mangue");
            saveToInternalStorageFromDrawable(R.drawable.melon, "melon");
            saveToInternalStorageFromDrawable(R.drawable.miel, "miel");
            saveToInternalStorageFromDrawable(R.drawable.moule, "moule");
            saveToInternalStorageFromDrawable(R.drawable.muffin, "muffin");
            saveToInternalStorageFromDrawable(R.drawable.myrtille, "myrtille");
            saveToInternalStorageFromDrawable(R.drawable.navet, "navet");
            saveToInternalStorageFromDrawable(R.drawable.noisette, "noisette");
            saveToInternalStorageFromDrawable(R.drawable.noix, "noix");
            saveToInternalStorageFromDrawable(R.drawable.nutella, "nutella");
            saveToInternalStorageFromDrawable(R.drawable.oeuf, "oeuf");
            saveToInternalStorageFromDrawable(R.drawable.oignon, "oignon");
            saveToInternalStorageFromDrawable(R.drawable.olive, "olive");
            saveToInternalStorageFromDrawable(R.drawable.orange, "orange");
            saveToInternalStorageFromDrawable(R.drawable.pain, "pain");
            saveToInternalStorageFromDrawable(R.drawable.pain_de_mie, "pain_de_mie");
            saveToInternalStorageFromDrawable(R.drawable.pamplemousse, "pamplemousse");
            saveToInternalStorageFromDrawable(R.drawable.pasteque, "pasteque");
            saveToInternalStorageFromDrawable(R.drawable.pate, "pate");
            saveToInternalStorageFromDrawable(R.drawable.pates, "pates");
            saveToInternalStorageFromDrawable(R.drawable.patisserie, "patisserie");
            saveToInternalStorageFromDrawable(R.drawable.peche, "peche");
            saveToInternalStorageFromDrawable(R.drawable.persil, "persil");
            saveToInternalStorageFromDrawable(R.drawable.petits_pois, "petits_pois");
            saveToInternalStorageFromDrawable(R.drawable.poire, "poire");
            saveToInternalStorageFromDrawable(R.drawable.poisson, "poisson");
            saveToInternalStorageFromDrawable(R.drawable.pomme, "pomme");
            saveToInternalStorageFromDrawable(R.drawable.pomme_de_terre, "pomme_de_terre");
            saveToInternalStorageFromDrawable(R.drawable.popcorn, "pocorn");
            saveToInternalStorageFromDrawable(R.drawable.poulet, "poulet");
            saveToInternalStorageFromDrawable(R.drawable.prune, "prune");
            saveToInternalStorageFromDrawable(R.drawable.puree, "puree");
            saveToInternalStorageFromDrawable(R.drawable.radis, "radis");
            saveToInternalStorageFromDrawable(R.drawable.ragout, "ragout");
            saveToInternalStorageFromDrawable(R.drawable.raisin, "raisin");
            saveToInternalStorageFromDrawable(R.drawable.risotto_legumes_printemps, "risotto_legumes_printemps");
            saveToInternalStorageFromDrawable(R.drawable.riz, "riz");
            saveToInternalStorageFromDrawable(R.drawable.salade, "salade");
            saveToInternalStorageFromDrawable(R.drawable.salade_melon_feta, "salade_melon_feta");
            saveToInternalStorageFromDrawable(R.drawable.salade_piemontaise, "salade_piemontaise");
            saveToInternalStorageFromDrawable(R.drawable.sauce, "sauce");
            saveToInternalStorageFromDrawable(R.drawable.saucisse, "saucisse");
            saveToInternalStorageFromDrawable(R.drawable.semoule, "semoule");
            saveToInternalStorageFromDrawable(R.drawable.soda, "soda");
            saveToInternalStorageFromDrawable(R.drawable.soupe, "soupe");
            saveToInternalStorageFromDrawable(R.drawable.sucre, "sucre");
            saveToInternalStorageFromDrawable(R.drawable.tajine_dorade_legumes, "tajine_dorade_legumes");
            saveToInternalStorageFromDrawable(R.drawable.tomate, "tomate");
            saveToInternalStorageFromDrawable(R.drawable.viande, "viande");
            saveToInternalStorageFromDrawable(R.drawable.yaourt, "yaourt");


        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_calendrier, R.id.navigation_home, R.id.navigation_liste, R.id.navigation_profil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    private String saveToInternalStorageFromDrawable(int drawable, String name){
        Bitmap bm = BitmapFactory.decodeResource( getResources(), drawable);
        ContextWrapper cw = new ContextWrapper(MainActivity.this);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imagedir", Context.MODE_PRIVATE);
        // Create imageDir
        File my_path=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(my_path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    private String saveToInternalStorage(Bitmap bitmapImage, String name){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
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
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private Boolean imageStored (int drawable, String name){
        ContextWrapper cw = new ContextWrapper(MainActivity.this);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imagedir", Context.MODE_PRIVATE);
        // Create imageDir
        File my_path=new File(directory,name);

        Boolean isStored = my_path.isFile();
        Log.i("MyTAG", isStored.toString());
        return isStored;
    }

}
