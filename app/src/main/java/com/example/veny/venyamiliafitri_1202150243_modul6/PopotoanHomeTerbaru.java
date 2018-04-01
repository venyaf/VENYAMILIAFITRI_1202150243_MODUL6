package com.example.veny.venyamiliafitri_1202150243_modul6;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PopotoanHomeTerbaru extends android.support.v4.app.Fragment{

    RecyclerView rv;
    DatabaseReference dataref;
    ArrayList<DBPost> list;
    AdapterPost adapterPost;

    public PopotoanHomeTerbaru() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inisialisasi semua objek
        View v = inflater.inflate(R.layout.activity_popotoan_home_terbaru, container, false);
        rv = v.findViewById(R.id.rvhometerbaru);
        list = new ArrayList<>();
        dataref = FirebaseDatabase.getInstance().getReference().child("post");
        adapterPost = new AdapterPost(list, this.getContext());

        //Get jumlah kolom yang sesuai
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        //Menampilkan recyclerview
        rv.setLayoutManager(new GridLayoutManager(this.getContext(),gridColumnCount));
        rv.setAdapter(adapterPost);

        //Event listener ketika value pada Firebase berubah
        dataref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //DBPost dataSnapshot.getValue dari class DBPost
                DBPost cur = dataSnapshot.getValue(DBPost.class);
                //dataSnapshot dari DBPost untuk getKey
                cur.key = dataSnapshot.getKey();
                //add list DBPost
                list.add(cur);
                //set notifyDataSetChanged pada adapter
                adapterPost.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

}
