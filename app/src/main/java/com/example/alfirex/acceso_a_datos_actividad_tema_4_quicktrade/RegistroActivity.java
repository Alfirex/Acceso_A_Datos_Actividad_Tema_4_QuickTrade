package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistroActivity extends AppCompatActivity {
    EditText etEmail,etContrasenya;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etContrasenya = (EditText) findViewById(R.id.etEmail);

        final Button button = (Button) findViewById(R.id.btnRegistrarse);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String sEmail = etEmail.getText().toString();
                String sContrasenya= etContrasenya.getText().toString();
                registrar(sEmail, sContrasenya);
            }
        });
    }

    private void registrar(String email, String contrasenya){
   
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, contrasenya)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegistroActivity.this, "Authentication succesful." + user.getUid(),
                                    Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistroActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}
