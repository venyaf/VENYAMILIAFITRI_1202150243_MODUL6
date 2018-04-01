package com.example.veny.venyamiliafitri_1202150243_modul6;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PopotoanHomeUser extends android.support.v4.app.Fragment{
    DatabaseReference ref;
    AdapterPost adapter;
    ArrayList<DBPost> list;
    RecyclerView rv;

    public PopotoanHomeUser() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inisialisasi semua objek pada database
        View v = inflater.inflate(R.layout.activity_popotoan_home_user, container, false);
        ref = FirebaseDatabase.getInstance().getReference().child("post");
        list = new ArrayList<>();
        adapter = new AdapterPost(list, this.getContext());
        rv = v.findViewById(R.id.rvhomeuser);

        //Get jumlah kolom yang sesuai
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        //Menampilkan recyclerview
        rv.setLayoutManager(new GridLayoutManager(this.getContext(),gridColumnCount));
        rv.setAdapter(adapter);

        //Event listener ketika data pada Firebase berubah
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //DBPost dataSnapshot.getValue dari class DBPost
                DBPost cur = dataSnapshot.getValue(DBPost.class);
                //kondisi getUser() apakah equals dengan authentication firebase dengan getCurrentUser dan getEmail
                if (cur.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                    //dataSnapshot dari DBPost untuk getKey
                    cur.setKey(dataSnapshot.getKey());
                    //add list DBPost
                    list.add(cur);
                    //set notifyDataSetChanged pada adapter
                    adapter.notifyDataSetChanged();
                }
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
