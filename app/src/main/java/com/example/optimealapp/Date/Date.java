package com.example.optimealapp.Date;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Date {
    private int day, month, year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public Date(String date){
        String[] str = date.split("/");
        List<String> list_str = Arrays.asList(str);
        this.day = Integer.parseInt(list_str.get(0));
        this.month = Integer.parseInt(list_str.get(1));
        this.year = Integer.parseInt(list_str.get(2));

    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean before(Date d) {
        return this.getYear()<d.getYear()
                || (this.getYear()==d.getYear() && ( this.getMonth()<d.getMonth()
                || (this.getMonth()==d.getMonth() && this.getDay()<d.getDay())));
    }

    private static boolean bissextile(int a) {
        return  (((a%400)==0)||(((a%4)==0)&& ((a%100)!=0)));
    }

    private static int nbJoursMois(int m, int a) {
        int nbJ;
        if (m==4 || m==6 || m==9 || m==11) {
            nbJ=30;
        }else if (m==2) {
            if (Date.bissextile(a)) {
                nbJ=29;
            } else {
                nbJ=28;
            }
        } else { //1, 3, 5, 7, 8, 10 ou 12
            nbJ=31;
        }

        return nbJ;
    }
    private static boolean coherente(int j, int m, int a) {
        return (m>0	&& m<13 && j>0 && j<=Date.nbJoursMois(m,a));
    }
    public String toString() {
        return (this.getDay() + "/" + this.getMonth() + "/" + this.getYear() );
    }
    public static int calculateAge(Date today, Date birth){
        int age = today.getYear()-birth.getYear();
        if (today.getMonth()<birth.getMonth()){
            age-=1;
        }
        else if(today.getMonth() == birth.getMonth()){
            if (today.getDay()<birth.getDay()){
                age-=1;
            }
        }
        return age;
    }

    public int calculateDaysDifferenceOneMonthMax(Date olderdate){
        int difference = this.getDay()-olderdate.getDay();
        if (this.getMonth() != olderdate.getMonth()){
            difference += nbJoursMois(olderdate.getMonth(),olderdate.getYear());
        }
        return (difference);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String toStringLetters(){
        Calendar calendar = Calendar.getInstance();
        Date today = getToday();

        int difference = this.calculateDaysDifferenceOneMonthMax(today);

        if(this.getMonth() - today.getMonth() > 1 && today.getMonth() != 12){
            return "Jour trop lointain";
        }
        if (this.before(today)){
            return ("Jour déjà passé");
        } else if (difference == 0){
            return ("Aujourd'hui");
        } else if (difference == 1) {
            return ("Demain");
        }
        int day_of_the_week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        int day_of_the_week_date = (difference+day_of_the_week)%7;
        String str = "Erreur";
        String month = "Erreur month";
        switch (this.getMonth()) {
            case 1:
                month = "janvier";
                break;
            case 2:
               month = "février";
                break;
            case 3:
                month = "mars";
                break;
            case 4:
                month = "avril";
                break;
            case 5:
                month = "mai";
                break;
            case 6:
                month = "juin";
                break;
            case 7:
                month = "juillet";
                break;
            case 8:
                month = "aout";
                break;
            case 9:
                month = "septembre";
                break;
            case 10:
                month = "octobre";
                break;
            case 11:
                month = "novembre";
                break;
            case 12:
                month = "décembre";
                break;
        }

        switch (day_of_the_week_date) {
            case 1:
                str = "Lundi " + this.getDay() + " " + month;
            break;
            case 2:
                str = "Mardi " + this.getDay() + " " + month;
            break;
            case 3:
                str = "Mercredi " + this.getDay() + " " + month;
            break;
            case 4:
                str = "Jeudi " + this.getDay() + " " + month;
            break;
            case 5:
                str = "Vendredi " + this.getDay() + " " + month;
            break;
            case 6:
                str = "Samedi " + this.getDay() + " " + month;
            break;
            case 0:
                str = "Dimanche " + this.getDay() + " " + month;
            break;
        }
        return str;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Date getToday(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String str = dtf.format(now);
        List<String> str_list = new ArrayList<String>(Arrays.asList(str.split("/")));
        return (new Date(Integer.parseInt(str_list.get(0)), Integer.parseInt(str_list.get(1)),
                Integer.parseInt(str_list.get(2))));
    }


    public Date getNextDay(){
        int day_today = this.getDay();
        int month_today = this.getMonth();
        int year_today = this.getYear();

        if (month_today!=12 || day_today!=31){
            if (day_today!=nbJoursMois(month_today,year_today)){
                return new Date(day_today+1, month_today,year_today);
            } else{
                return new Date(1, month_today+1, year_today);
            }
        } else{
            return new Date(1,1,year+1);
        }
    }

    public Date getPreviousDay(){
        int day_today = this.getDay();
        int month_today = this.getMonth();
        int year_today = this.getYear();

        if (month_today!=1 || day_today!=1){
            if (day_today!=1){
                return new Date(day_today-1, month_today,year_today);
            } else{
                return new Date(nbJoursMois(month_today-1, year_today), month_today-1, year_today);
            }
        } else{
            return new Date(31,12,year-1);
        }
    }

}
