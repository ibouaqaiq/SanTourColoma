package com.example.issam.santourcoloma.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.issam.santourcoloma.Model.User;
import com.example.issam.santourcoloma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private EditText displayName;
    private EditText mailIn;
    private EditText password;
    private CardView unirse;
    private TextView yaRegistrado;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    System.out.println("");
                }
            }
        };

        progressDialog = new ProgressDialog(this);

        displayName = findViewById(R.id.displayName);
        mailIn = findViewById(R.id.mailIn);
        password = findViewById(R.id.password);
        yaRegistrado = findViewById(R.id.yaRegistrado);
        unirse = findViewById(R.id.unirse);

        unirse.setOnClickListener(this);
        yaRegistrado = findViewById(R.id.yaRegistrado);



        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void registerUser(){
        String dName = displayName.getText().toString().trim();
        String email = mailIn.getText().toString().trim();
        String passwordIn = password.getText().toString().trim();

        if(TextUtils.isEmpty(dName)){
            Toast.makeText(this,"Porfavor ponga un nombre", Toast.LENGTH_LONG).show();
            mailIn.setError("Obligatorio");

            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Introduzca un correo electronico", Toast.LENGTH_SHORT).show();
            mailIn.setError("Obligatorio");
            return;
        }

        if(TextUtils.isEmpty(passwordIn)){
            Toast.makeText(this, "Introduzca una contraseña ", Toast.LENGTH_SHORT).show();
            password.setError("Obligatorio");
            return;

        }
        if(password.length()<6){
            Toast.makeText(this,"Porfavor introduzca una etPassword de más de 6 caracteres", Toast.LENGTH_LONG).show();
            password.setError("Obligatorio");
            return;
        }


        progressDialog.setMessage("Registrando Usuario...");
        progressDialog.show();



         firebaseAuth.createUserWithEmailAndPassword(email,passwordIn)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             onAuthSuccess(task.getResult().getUser());
                             finish();
                             startActivity(new Intent(getApplicationContext(), MainActivity.class));

                         }else{
                             Toast.makeText(RegistroActivity.this, "No se ha podido registrar... Intentalo de nuevo", Toast.LENGTH_SHORT).show();
                         }
                         progressDialog.dismiss();
                     }
                 });


        }

        private void onAuthSuccess(FirebaseUser user) {
          //  User newUser = new User(user.getUid();
            String dName = displayName.getText().toString();
            String email = mailIn.getText().toString();
            mDatabase.child("users").child(user.getUid()).setValue(new User(user.getUid(), dName, email));

            startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
            finish();
        }




    @Override
    public void onClick(View v) {
        if(v == unirse){
            registerUser();
        }

        if(v == yaRegistrado){
            //will
        }

    }
}
