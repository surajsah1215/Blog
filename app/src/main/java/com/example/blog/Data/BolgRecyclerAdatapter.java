package com.example.blog.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.Model.Bolg;
import com.example.blog.R;
import com.squareup.picasso.Picasso;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class BolgRecyclerAdatapter extends RecyclerView.Adapter<BolgRecyclerAdatapter.ViewHolder> {
   public Context context;
   public List<Bolg> Bloglist;

    public BolgRecyclerAdatapter(Context context, List<Bolg> bloglist) {
        this.context = context;
        this.Bloglist = bloglist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent,false);
        return new ViewHolder(view , context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bolg bolg = Bloglist.get(position);
        String imageurl = bolg.getImage();

        holder.title.setText(bolg.getTitle());
        holder.desc.setText(bolg.getDesc());



        holder.timestamp.setText(bolg.getTimestmap());




        Picasso.get()
                .load(imageurl)
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return Bloglist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView desc;
        public TextView timestamp;
        public ImageView image;
        String userid;

        public ViewHolder(View view, Context ctx){
            super(view);
            context = ctx;

            title = (TextView) view.findViewById(R.id.postTitleList);
            desc = (TextView) view.findViewById(R.id.postTextList);
            timestamp = (TextView) view.findViewById(R.id.timestampList);
            image = (ImageView) view.findViewById(R.id.postImageList);

            userid = null;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //go to the next activity.....
                }
            });
        }

    }
}
