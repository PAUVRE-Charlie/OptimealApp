package com.example.optimealapp.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.optimealapp.AddPublicationActivity;
import com.example.optimealapp.R;
import com.example.optimealapp.User.User;
import com.example.optimealapp.User.UserDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View root;
    RecyclerView myRecyclerView;
    Cursor cursor;
    ArrayList<String> pub_ids, pub_titles, pub_meal_titles, pub_likes, pub_dislikes, pub_comments, user_ids,
        user_names, user_firstnames, user_ages;
    ArrayList<String> pub_avatars, pub_meal_images, user_avatars;
    HomeDataBase myPublicationsDB;
    UserDataBase myUserDB;
    MyHomeAdapter myAdapter;
    FloatingActionButton add_button;
    int current_position = 0;
    ArrayList<User> users = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Log.i("MyTAG","&&&");


        root = inflater.inflate(R.layout.fragment_home, container, false);

        add_button = root.findViewById(R.id.home_add_floatingActionButton);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPublicationActivity.class);
                startActivity(intent);
            }
        });
        myRecyclerView = root.findViewById(R.id.recycler_view);
        refreshDataPublications();
        //Log.i("MyTAG","fail");
        return root;
    }

    private void storeDataPublicationsInArrays() {
        Cursor cursor = myPublicationsDB.readAllData();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                pub_ids.add(cursor.getString(0));
                pub_avatars.add(cursor.getString(1));
                pub_titles.add(cursor.getString(2));
                pub_meal_titles.add(cursor.getString(3));
                pub_meal_images.add(cursor.getString(4));
                pub_likes.add(cursor.getString(5));
                pub_dislikes.add(cursor.getString(6));
                pub_comments.add(cursor.getString(7));
                //Log.i("MyTAG","data    "+cursor.getString(1));
                //Log.i("MyTAG",cursor.getString(4));
            }
        }
    }
 private void storeDataUsersInArrays() {
        Cursor cursor = myUserDB.readAllData();
        HomeDataBase.max_id = cursor.getCount();
        //Log.i("MyTAG","hehe");
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                user_ids.add(cursor.getString(0));
                user_avatars.add(cursor.getString(1));
                user_names.add(cursor.getString(2));
                user_firstnames.add(cursor.getString(3));
                user_ages.add(cursor.getString(4));
            }
        }
    }

    void refreshDataPublications() {
        //Log.i("MyTAG","refresh");
        myPublicationsDB = new HomeDataBase(getContext());
        pub_ids = new ArrayList<>();
        pub_avatars = new ArrayList<>();
        pub_titles = new ArrayList<>();
        pub_meal_titles = new ArrayList<>();
        pub_meal_images = new ArrayList<>();
        pub_likes = new ArrayList<>();
        pub_dislikes = new ArrayList<>();
        pub_comments = new ArrayList<>();
        storeDataPublicationsInArrays();
        //Log.i("MyTAG","stored");
        myAdapter = new MyHomeAdapter(this, getContext(), pub_ids, pub_avatars, pub_titles,
                pub_meal_titles, pub_meal_images, pub_likes, pub_dislikes, pub_comments);
        //Log.i("MyTAG","adapt");
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Log.i("MyTAG","layout");
        myRecyclerView.setAdapter(myAdapter);
        //Log.i("MyTAG","set");
        myRecyclerView.scrollToPosition(current_position - 1);

    }

    private void refreshDataUsers() {
        //Log.i("MyTAG","refresh");
        myUserDB = new UserDataBase(getContext());
        user_ids = new ArrayList<>();
        user_avatars = new ArrayList<>();
        user_names = new ArrayList<>();
        user_firstnames = new ArrayList<>();
        user_ages = new ArrayList<>();

        storeDataUsersInArrays();
    }

}