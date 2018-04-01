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

public class RegisterUserActivity extends AppCompatActivity {
    Button daftar;
    EditText edEmail, edPass;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        edEmail = (EditText)findViewById(R.id.edEmail);
        edPass = (EditText)findViewById(R.id.edPass);
        daftar = (Button)findViewById(R.id.daftar);

        //PROGRESS DIALOG CONTEXT
        mProgressDialog = new ProgressDialog(this);

        //FIREBASE AUTHENTICATION INSTANCES
        mAuth = FirebaseAuth.getInstance();

        //membuat FirebaseAuth.AuthStateListener baru
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //nama variable getCurrentUser dari FirebaseAuth pada FirebaseUser
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user!=null){
                    //Intent dari RegisterUserActivity ke PopotoanHome
                    Intent moveToHome = new Intent(RegisterUserActivity.this,PopotoanHome.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    startActivity(moveToHome);
                }
            }
        };

        //FirebaseAuth menambahkan AuthStateListener dengan FirebaseAuth.AuthStateListener
        mAuth.addAuthStateListener(mAuthListener);


        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.setTitle("Create Account");
                mProgressDialog.setMessage("Wait while account is being created...");
                mProgressDialog.show();

                //method createUserAccount
                createUserAccount();
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

    private void createUserAccount() {

        String emailUser, passUser;

        //getText dari Edit Text pada email dan password ketika login
        emailUser = edEmail.getText().toString().trim();
        passUser = edPass.getText().toString().trim();

        if(!TextUtils.isEmpty(emailUser) && !TextUtils.isEmpty(passUser)){

            //FirebaseAuth dengan signInWithEmailAndPassword
            mAuth.createUserWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(RegisterUserActivity.this,"Account Created Success",Toast.LENGTH_LONG).show();
                        //dismiss progress dialog
                        mProgressDialog.dismiss();

                        //Intent dari RegisterUserActivity ke PopotoanHome
                        Intent moveToHome = new Intent(RegisterUserActivity.this, PopotoanHome.class);
                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                        startActivity(moveToHome);

                    }else{
                        Toast.makeText(RegisterUserActivity.this,"Account Created Failed",Toast.LENGTH_LONG).show();
                        //dismiss progress dialog
                        mProgressDialog.dismiss();
                    }

                }
            });
        }
    }
}
