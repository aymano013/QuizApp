package com.hachame.quizapp_c;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Step 1: Declaration
    EditText etLogin, etPassword;
    Button bLogin;
    TextView tvRegister;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //step 0:
        mAuth = FirebaseAuth.getInstance();
        //Step 2: Recuperation des ids
        etLogin = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etLogin.getText().toString();
                String pwd= etPassword.getText().toString();
                if(TextUtils.isEmpty(email)){
                    etLogin.setError("Entrer une adresse valide");
                    etLogin.requestFocus();
                }else if(TextUtils.isEmpty(pwd)){
                    etLogin.setError("Mot de passe incorrecte");
                    etLogin.requestFocus();
                }else {

                    mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "utilisateur connecté", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,Quiz1.class));
                            }else {
                                Toast.makeText(MainActivity.this, "email ou mot de passe invalide !!    ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                };
            }

        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Step 4: Traitement
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Intent x = new Intent(MainActivity.this,MainActivity.class);
            startActivityIfNeeded(x,1);
        }
    }
}
