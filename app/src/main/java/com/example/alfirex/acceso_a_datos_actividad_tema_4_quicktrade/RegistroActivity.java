package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity {
    EditText etUsuario, etNombre,etApellidos, etEmail,etContrasenya, etDireccion;
    private FirebaseAuth mAuth;
    private ArrayList<String> listado = new ArrayList<String>();
    private  int cont = 0;
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellidos = (EditText) findViewById(R.id.etApellidos);
        etDireccion= (EditText) findViewById(R.id.etDireccion);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContrasenya = (EditText) findViewById(R.id.etContraseña);

        // Cogemos la referencia del Nodo de Firebase
        bbdd = FirebaseDatabase.getInstance().getReference( getString(R.string.nodo_usuarios) );


//        bbdd.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot datasnapshot : dataSnapshot.getChildren()){
//                    Usuario oUsuario = datasnapshot.getValue(Usuario.class);
//
//                    String usuario = oUsuario.getUsuario();
//                    //Log.d("Prueba", String.valueOf(usuario));
//                    listado.add(usuario);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        final Button button = (Button) findViewById(R.id.btnRegistrarse);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String sUsuario = etUsuario.getText().toString();
                final String sNombre = etNombre.getText().toString();
                final String sApellido = etApellidos.getText().toString();
                final String sDireccion = etDireccion.getText().toString();
                final String sEmail = etEmail.getText().toString();
                final String sContrasenya= etContrasenya.getText().toString();

                // En esta linea lo que hacemos es comprobar de que hay un usuario en la BD igual
                Query q = bbdd.orderByChild(getString(R.string.campo_usuario)).equalTo(sUsuario);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                            cont++; //si hay interaccion significa que hay usuario
                        }
                        if (cont == 0){ // Si la variable contador sigue sindo 0 es que no ha encontrado un usuario con el mismo nombre
                            // Registramos Usuario
                            registrar(sUsuario,sNombre,sApellido,sEmail, sContrasenya,sDireccion);
                        }else{ // Ha encontrado un usuario con el mismo nombre
                            cont = 0;// Reiniciamos la variable ha 0  para que  si insertamos varios usuarios compruebe de nuevo
                            Toast.makeText(RegistroActivity.this, "El Usuario Insertado Ya Existe." ,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private void registrar(final String usuario,final String nombre,final String apellido,final String email, final String contrasenya,final String sDireccion){
   
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, contrasenya)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegistroActivity.this, "Registro creado correctamente." + user.getUid(),
                                    Toast.LENGTH_SHORT).show();
                            mAuth.signOut();//para que se desconecte el ususario nos mas se registre

                            // Actividad 2B
                            // Generamos el objeto Usuario con sus parametros
                            Usuario oUsuario = new Usuario(usuario, email ,nombre,apellido,contrasenya,sDireccion,user.getUid());
                            //Generamos una Clave
                            String clave = bbdd.push().getKey();
                            // Añadimos esa clave el objeto Usuario
                            bbdd.child(clave).setValue(oUsuario);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistroActivity.this, "El registro a fallado: " + task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}
