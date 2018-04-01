package com.example.veny.venyamiliafitri_1202150243_modul6;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    TextView user, title, caption;
    ImageView image;
    EditText sourcekomentar;
    RecyclerView rv;
    AdapterComment adapter;
    ArrayList<DBcomment> list;
    DatabaseReference dref;
    ProgressDialog pd;
    String namaUser, idfoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //Inisialisi semua objek yang digunakan
        user = (TextView) findViewById(R.id.userDariPost);
        image = (ImageView) findViewById(R.id.gambarDariPost);
        sourcekomentar = (EditText) findViewById(R.id.isiKomentar);
        pd = new ProgressDialog(this);
        title = findViewById(R.id.judulDariPost);
        caption = findViewById(R.id.deskripsiDariPost);
        dref = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("comment");
        rv = (RecyclerView) findViewById(R.id.rvkomentar);
        list = new ArrayList<>();
        adapter = new AdapterComment(this, list);

        //Menampilkan recyclerview
        rv.setHasFixedSize(true);
        //LinearLayoutManager
        rv.setLayoutManager(new LinearLayoutManager(this));
        //set adapter
        rv.setAdapter(adapter);

        //Memberikan tulisan atau nilai untuk View pada class
        String [] currentUser  = FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@");
        namaUser = currentUser[0];
        idfoto = getIntent().getStringExtra("key");
        user.setText(getIntent().getStringExtra("user"));
        title.setText(getIntent().getStringExtra("title"));
        caption.setText(getIntent().getStringExtra("caption"));
        Glide.with(this).load(getIntent().getStringExtra("image")).override(250,250).into(image);

        //Event listener ketika data berubah di Firebase
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //DBcomment dataSnapshot.getValue dari class DBcomment
                DBcomment cur = dataSnapshot.getValue(DBcomment.class);
                //kondisi getFotoKomentar() equals dengan idfoto
                if (cur.getFotoKomentar().equals(idfoto)){
                    //add list Dbcomment
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
    }

    //Method untuk mempost komentar
    public void post(View view) {
        //Menampilkan dialog
        pd.setMessage("Adding comment. . . "); pd.show();

        //Inisialisasi objek
        DBcomment com = new DBcomment(namaUser, sourcekomentar.getText().toString(), idfoto);

        //Input data ke Firebase
        dref.push().setValue(com).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CommentActivity.this, "Comment added", Toast.LENGTH_SHORT).show();
                    //set kosong pada EditText sourcekomentar setelah input komentar
                    sourcekomentar.setText(null);
                }else{
                    Toast.makeText(CommentActivity.this, "Failed to comment", Toast.LENGTH_SHORT).show();
                }
                //dismiss pada ProgressDialog
                pd.dismiss();
            }
        });
    }
}
