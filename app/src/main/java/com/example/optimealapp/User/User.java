package com.example.optimealapp.User;

import com.example.optimealapp.Date.Date;

import java.util.ArrayList;
import java.util.Calendar;

public class User {

    private String first_name, name, email, password, age, avatar, adresse, sexe, poids, taille,
            nombre, sportif, regime;
    private int id;
    private Date date_of_birth;
    private ArrayList<String> id_pub_liked_list = new ArrayList<>();
    private ArrayList<String> id_pub_disliked_list = new ArrayList<>();

    public User(int id, String first_name, String name, String email, String password,
                String age, String avatar, String adresse, String sexe, String poids,
                String taille, String nombre, String sportif, String regime) {
        this.first_name = first_name;
        this.avatar = avatar;
        this.name = name;
        this.id = id;
        this.age = age;
        this.adresse = adresse;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.nombre = nombre;
        this.sportif = sportif;
        this.regime = regime;

        this.email = email;
        this.password = password;

        int day = Calendar.DAY_OF_MONTH;
        int month = Calendar.MONTH;
        int year = Calendar.YEAR;
        Date today = new Date(day, month, year);
    }


    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return age;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getSexe() {
        return sexe;
    }

    public String getPoids() {
        return poids;
    }

    public String getTaille() {
        return taille;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSportif() {
        return sportif;
    }

    public String getRegime() {
        return regime;
    }

    public ArrayList<String> getId_pub_liked_list() {
        return id_pub_liked_list;
    }

    public ArrayList<String> getId_pub_disliked_list() {
        return id_pub_disliked_list;
    }

    public void setId_pub_liked_list(ArrayList<String> id_pub_liked_list) {
        this.id_pub_liked_list = id_pub_liked_list;
    }

    public void setId_pub_disliked_list(ArrayList<String> id_pub_disliked_list) {
        this.id_pub_disliked_list = id_pub_disliked_list;
    }

    public void add_like(String id){
        this.getId_pub_liked_list().add(id);
        this.getId_pub_disliked_list().remove(id);
    }
    public void add_dislike(String id){
        this.getId_pub_disliked_list().add(id);
        this.getId_pub_liked_list().remove(id);
    }
}

