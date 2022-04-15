package com.hachame.quizapp_c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
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
                                Toast.makeText(Login.this, "Connecté", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this,Quiz1.class));
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
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}