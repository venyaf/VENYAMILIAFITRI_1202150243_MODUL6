package com.example.veny.venyamiliafitri_1202150243_modul6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Veny on 3/31/2018.
 */

public class AdapterPost extends  RecyclerView.Adapter<AdapterPost.PostViewHolder>{
    private List<DBPost> list;
    private Context con;

    //Constructor dari adapter
    public AdapterPost(List<DBPost> list, Context con) {
        this.list = list;
        this.con = con;
    }

    //Return ViewHolder untuk adapter
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //mengembalikan CommentHolder dengan parameter LayoutInflater dari layout rec_feed, ViewGroup, ViewType false
        return new PostViewHolder(LayoutInflater.from(con).inflate(R.layout.rec_feed, parent, false));
    }

    //Mengikat nilai dari list dengan view
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        //get position dari list pada DBPost class
        DBPost current = list.get(position);
        //String array dari current user yang displit sebelum @
        String [] user = current.user.split("@");
        //holder yang didapat dengan getter dari DBPost
        holder.user.setText(user[0]);
        holder.user.setTag(current.getKey());
        holder.title.setText(current.getTitle());
        holder.caption.setText(current.getCaption());
        holder.caption.setTag(current.getImage());
        Glide.with(con).load(current.getImage()).placeholder(R.drawable.add_image).override(450, 450).into(holder.image);
    }

    //Mendapatkan jumlah item pada recyclerview
    @Override
    public int getItemCount() {
        //mengembalikan ukuran list
        return list.size();
    }

    //Subclass sebagai viewholder
    class PostViewHolder extends RecyclerView.ViewHolder{
        //deklarasi variable TextView dan ImageView
        ImageView image;
        TextView user, title, caption;
        public PostViewHolder(View itemView) {
            super(itemView);
            //itemView dengan findView
            image = itemView.findViewById(R.id.postGambar);
            user = itemView.findViewById(R.id.userPengupload);
            title = itemView.findViewById(R.id.postJudul);
            caption = itemView.findViewById(R.id.postDeskripsi);

            //Operasi ketika item pada recyclerview diklik
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent berpindah dari Context ke class CommentActivity
                    Intent pindah = new Intent(con, CommentActivity.class);
                    pindah.putExtra("user", user.getText());
                    pindah.putExtra("key", user.getTag().toString());
                    pindah.putExtra("title", title.getText());
                    pindah.putExtra("caption", caption.getText());
                    pindah.putExtra("image", caption.getTag().toString());
                    //Context startActivity(variable Intent)
                    con.startActivity(pindah);
                }
            });
        }
    }
}
