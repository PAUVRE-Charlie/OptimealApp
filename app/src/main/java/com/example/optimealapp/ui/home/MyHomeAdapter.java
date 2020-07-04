package com.example.optimealapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.optimealapp.R;
import com.example.optimealapp.User.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MyHomeAdapter extends RecyclerView.Adapter<MyHomeAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> pub_ids, pub_titles, pub_meal_titles,pub_likes, pub_dislikes, pub_comments;
    private ArrayList<String> pub_avatars, pub_meal_images;
    private HomeFragment fragment;
    private User user;

    MyHomeAdapter(HomeFragment fragment, Context context, ArrayList<String> pub_ids,
                  ArrayList<String> pub_avatars, ArrayList<String> pub_titles,
                  ArrayList<String> pub_meal_titles, ArrayList<String> pub_meal_images,
                  ArrayList<String> pub_likes, ArrayList<String> pub_dislikes,
                  ArrayList<String> pub_comments){
        this.fragment = fragment;
        this.context = context;
        this.pub_ids = pub_ids;
        this.pub_avatars = pub_avatars;
        this.pub_titles = pub_titles;
        this.pub_meal_titles = pub_meal_titles;
        this.pub_meal_images = pub_meal_images;
        this.pub_likes = pub_likes;
        this.pub_dislikes = pub_dislikes;
        this.pub_comments = pub_comments;
    }

    @NonNull
    @Override
    public MyHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_home_row,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyHomeAdapter.MyViewHolder holder, final int position) {
        //Log.i("MyTAG", pub_avatars.get(position));
        //Log.i("MyTAG", pub_meal_images.get(position));
        //Log.i("MyTAG", holder.pub_avatar_image.getDrawable().toString());
        holder.pub_title_textView.setText(String.valueOf(pub_titles.get(position)));
        holder.pub_meal_title_textView.setText(String.valueOf(pub_meal_titles.get(position)));
        holder.pub_likes_textView.setText(String.valueOf(pub_likes.get(position)));
        //Log.i("MyTAG",pub_avatars.get(position));
        holder.pub_avatar_image.setImageBitmap(loadImageFromStorageFromImageDir(pub_avatars.get(position)));
        holder.pub_meal_image.setImageBitmap(loadImageFromStorageFromImageDir(pub_meal_images.get(position)));
        holder.pub_dislikes_textView.setText(String.valueOf(pub_dislikes.get(position)));
        holder.liked = (Integer.parseInt(pub_likes.get(position)) == 1);
        holder.disliked = (Integer.parseInt(pub_dislikes.get(position)) == 1);
        holder.pub_comments_textView.setText(String.valueOf(pub_comments.get(position)));

        if (holder.liked) {
            holder.pub_like_button.getDrawable().setTint(Color.rgb(0,139,0));
        }
        if (holder.disliked) {
                    holder.pub_dislike_button.getDrawable().setTint(Color.rgb(0,139,0));
                }

        holder.pub_like_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                //holder.pub_like_button.setBackgroundResource(R.color.colorPrimary);
                holder.liked = !holder.liked;
                holder.disliked = false;
                int like;
                int dislike = 0;
                if (holder.liked){
                    like = 1;
                }else{
                    like =0;
                }

                HomeDataBase db = new HomeDataBase(context);
                db.updateData(pub_ids.get(position), pub_avatars.get(position), pub_titles.get(position),
                        pub_meal_titles.get(position), pub_meal_images.get(position), like, dislike,
                        Integer.parseInt(pub_comments.get(position)));

                fragment.current_position = position;
                fragment.refreshDataPublications();
            }
        });
        holder.pub_dislike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.pub_dislike_button.setBackgroundResource(R.color.colorPrimary);
                holder.disliked = !holder.disliked;
                holder.liked = true;
                int like = 0;
                int dislike;
                if (holder.disliked){
                    dislike = 1;
                }else{
                    dislike =0;
                }
                HomeDataBase db = new HomeDataBase(context);
                db.updateData(pub_ids.get(position), pub_avatars.get(position), pub_titles.get(position),
                        pub_meal_titles.get(position), pub_meal_images.get(position), like, dislike,
                        Integer.parseInt(pub_comments.get(position)));

                fragment.current_position = position;
                fragment.refreshDataPublications();
            }
        });
        holder.pub_comments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.pub_comments_button.setBackgroundResource(R.color.colorPrimary);
                HomeDataBase db = new HomeDataBase(context);
                db.updateData(pub_ids.get(position), pub_avatars.get(position), pub_titles.get(position),
                        pub_meal_titles.get(position), pub_meal_images.get(position),
                        Integer.parseInt(pub_likes.get(position)), Integer.parseInt(pub_dislikes.get(position)),
                        Integer.parseInt(pub_comments.get(position)) + 1);
                fragment.current_position = position;
                fragment.refreshDataPublications();
            }
        });
        /*holder.pub_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeDataBase mydb = new HomeDataBase(context);
                mydb.deleteRow(pub_ids.get(position));
                fragment.current_position = position;
                fragment.refreshDataPublications();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return pub_ids.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView pub_title_textView, pub_meal_title_textView, pub_likes_textView,
            pub_dislikes_textView, pub_comments_textView;
        LinearLayout mainLayout;
        ImageView pub_avatar_image, pub_meal_image;
        ImageButton pub_like_button, pub_dislike_button, pub_comments_button;
        Boolean liked = false;
        Boolean disliked = false;
        //FloatingActionButton pub_delete_button;

        @SuppressLint({"CutPasteId", "NewApi"})
        MyViewHolder(View itemView) {
            super(itemView);
            pub_title_textView = itemView.findViewById(R.id.home_row_title_text);
            pub_meal_title_textView = itemView.findViewById(R.id.home_row_meal_title_text);
            pub_likes_textView = itemView.findViewById(R.id.home_row_like_count);
            pub_dislikes_textView = itemView.findViewById(R.id.home_row_dislike_count);
            pub_comments_textView = itemView.findViewById(R.id.home_row_comment_count);
            pub_avatar_image = itemView.findViewById(R.id.home_row_avatar_image);
            pub_meal_image = itemView.findViewById(R.id.home_row_meal_image);
            pub_like_button = itemView.findViewById(R.id.home_row_like_button);
            pub_dislike_button = itemView.findViewById(R.id.home_row_dislike_button);
            pub_comments_button = itemView.findViewById(R.id.home_row_comment_button);
            //pub_delete_button = itemView.findViewById(R.id.home_row_delete_floatingActionButton3);
            mainLayout = itemView.findViewById(R.id.homeMainLayout);
        }
    }

    public static Bitmap convertByteArrayToBitmap(byte[] byteArrayToBeConvertedIntoBitMap) {
        Bitmap bitMapImage = BitmapFactory.decodeByteArray(
                byteArrayToBeConvertedIntoBitMap, 0,
                byteArrayToBeConvertedIntoBitMap.length);
        return bitMapImage;
    }

    private Bitmap loadImageFromStorageFromImageDir(String name)
    {
        String path = context.getDir("imagedir", Context.MODE_PRIVATE).getAbsolutePath();
        Bitmap b = null;
        try {
            File f=new File(path, name);
            //Log.i("MyTAG",f.toSwitring());
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
