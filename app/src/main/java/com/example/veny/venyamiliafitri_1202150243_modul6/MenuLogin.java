package com.example.veny.venyamiliafitri_1202150243_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuLogin extends AppCompatActivity {
    //deklarasi nama variable
    Button daftar,masuk;
    EditText edEmail, edPass;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_login);

        edEmail = (EditText)findViewById(R.id.edEmail);
        edPass = (EditText)findViewById(R.id.edPass);
        masuk = (Button)findViewById(R.id.masuk);
        daftar = (Button)findViewById(R.id.daftar);

        //PROGRESS DIALOG CONTEXT
        mProgressDialog = new ProgressDialog(this);

        //FIREBASE AUTHENTICATION INSTANCES
        mAuth = FirebaseAuth.getInstance();

        //membuat FirebaseAuth.AuthStateListener baru
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //CHECKING USER PRESENCE
                //nama variable getCurrentUser dari FirebaseAuth pada FirebaseUser
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if( user != null )
                {

                    //Intent dari MenuLogin ke PopotoanHome
                    Intent moveToHome = new Intent(MenuLogin.this, PopotoanHome.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(moveToHome);

                }
            }
        };

        //FirebaseAuth menambahkan AuthStateListener dengan FirebaseAuth.AuthStateListener
        mAuth.addAuthStateListener(mAuthListener);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent dari MenuLogin ke RegisterUserActivity
                Intent intent = new Intent(MenuLogin.this,RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                mProgressDialog.setTitle("Loging in the user");
                mProgressDialog.setMessage("Please wait....");
                mProgressDialog.show();
                //method loginUser()
                loginUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //FirebaseAuth menambahkan AuthStateListener dengan FirebaseAuth.AuthStateListener
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //FirebaseAuth meremove AuthStateListener dengan FirebaseAuth.AuthStateListener
        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void loginUser() {

        String userEmail, userPassword;

        //getText dari Edit Text pada email dan password ketika login
        userEmail = edEmail.getText().toString().trim();
        userPassword = edPass.getText().toString().trim();

        if( !TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword))
        {

            //FirebaseAuth dengan signInWithEmailAndPassword
            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if( task.isSuccessful())
                    {

                        //dismiss progress dialog
                        mProgressDialog.dismiss();
                        //intent dari MenuLogin ke PopotoanHome
                        Intent moveToHome = new Intent(MenuLogin.this, PopotoanHome.class);
                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(moveToHome);

                    }else
                    {

                        Toast.makeText(MenuLogin.this, "Unable to login user", Toast.LENGTH_LONG).show();
                        //dismiss progress dialog
                        mProgressDialog.dismiss();

                    }

                }
            });

        }else
        {

            //Jika email atau password kosong
            Toast.makeText(MenuLogin.this, "Please enter user email and password", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();

        }
    }

}
