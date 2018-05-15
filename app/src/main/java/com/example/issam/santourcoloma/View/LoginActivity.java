package com.example.issam.santourcoloma.View;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.issam.santourcoloma.R;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.issam.santourcoloma.Model.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 123;
    private EditText mailIn;
    private EditText password;
    private CardView entrarGoogle;
    private CardView entrarManual;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mailIn = findViewById(R.id.mailIn);
        password = findViewById(R.id.password);
        entrarGoogle = findViewById(R.id.sign_in);
        entrarManual = findViewById(R.id.entrar);

        progressDialog = new ProgressDialog(this);
        firebaseAuth =  FirebaseAuth.getInstance();

       if(firebaseAuth.getCurrentUser() != null){
           finish();
         startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

       entrarManual.setOnClickListener(this);

        findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        findViewById(R.id.registrobtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister();
            }
        });
        comeIn();


    }


    void comeIn() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            final String userId = firebaseUser.getUid();

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if(user == null){
                        databaseReference.setValue(new User(userId, firebaseUser.getDisplayName(), firebaseUser.getEmail()));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getMessage());
                }
            });
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    void signIn(){
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);

    }

    void goRegister(){
        startActivity(new Intent(this, RegistroActivity.class));
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                comeIn();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    return;
                }
            }
        }
    }

    private void userLogin() {
        String email = mailIn.getText().toString().trim();
        String passwordIn = password.getText().toString().trim();

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

        progressDialog.setMessage("Accediendo a Usuario...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, passwordIn)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                        Toast.makeText(LoginActivity.this, "Error de Registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                });

    }

    @Override
    public void onClick(View v) {
        if(v == entrarManual){
            userLogin();
        }

    }


}
