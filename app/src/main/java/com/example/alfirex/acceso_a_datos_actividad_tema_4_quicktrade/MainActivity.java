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


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etEmail, etContrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail =  findViewById(R.id.etEmail);
        etContrasenya =  findViewById(R.id.etContraseña);

        final Button btnAcceder =  findViewById(R.id.btnAcceder);
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sEmail = etEmail.getText().toString();
                String sContrasenya= etContrasenya.getText().toString();

                login(sEmail, sContrasenya);// Vamos a la funcion de login para hacer las comprobaciones de que si existe en Firebase
            }
        });

        final Button btnRegistrarse =  findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iRegistro = new Intent(v.getContext(), RegistroActivity.class);
                startActivity(iRegistro);
            }
        });

    }
    private void login(String email, String contrasenya){

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, contrasenya)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Iniciar sesión correctamente, actualizar la interfaz de usuario con la información del usuario registrado

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication succesful." + user.getUid(),
                                    Toast.LENGTH_SHORT).show();

                            Intent iDiversasAcciones = new Intent(MainActivity.this, DiversasAccionesActivity.class);
                            startActivity(iDiversasAcciones);

                        } else {
                            // Si falla el inicio de sesión, muestre un mensaje al usuario.
                            Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}

