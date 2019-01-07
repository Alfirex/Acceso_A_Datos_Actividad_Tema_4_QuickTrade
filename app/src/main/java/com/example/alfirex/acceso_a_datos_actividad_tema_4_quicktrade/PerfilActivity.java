package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {
    EditText etNombre, etApellidos, etDireccion;
    private String  idUsuario;
    DatabaseReference bbdd;
    private ArrayList<Usuario> listado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();// Obtenemos el valor del usuario logueado
        etNombre =  findViewById(R.id.etNombre);
        etApellidos =  findViewById(R.id.etApellidos);
        etDireccion =  findViewById(R.id.etDireccion);

        // Cogemos la referencia del Nodo de Firebase
        bbdd = FirebaseDatabase.getInstance().getReference( getString(R.string.nodo_usuarios) );
        /**
         * Obtenemos la informacion del Usuario Logueado
         */
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Recorremos todos  los Usuarios para despues almacenarlos en un ArrayList
                for(DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                    Usuario oUsuario = datasnapshot.getValue(Usuario.class);

                    String usuario = oUsuario.getUsuario();
                    String nombre = oUsuario.getNombre();
                    String apellido = oUsuario.getApellidos();
                    String correo = oUsuario.getCorreo();
                    String contrasenya= oUsuario.getContraseña();
                    String sDireccion = oUsuario.getDireccion();
                    String userid = oUsuario.getIduser();

                    listado.add(new Usuario(usuario, correo, nombre, apellido, contrasenya, sDireccion, userid));// Añadimos toda al ArrayList la informacion de cada Usuario
                }
                // Iteramos el ArrayList para mostrar la informacion de cada objeto
                for (Usuario elemento : listado) {
                    // En el IF comprovamos de que el user logueado actualmente coincide con el que este en el ArrayList, y si esta que almacene/sette el texto de los valores
                    if (elemento.getIduser().compareTo(user.getUid()) == 0) {
                        idUsuario = elemento.getUsuario();// Almacenamos el nombre de Usuario del ArrayList

                        // Setteamos los edtext con la informacion del Usuario Actual
                        etNombre.setText(elemento.getNombre());
                        etDireccion.setText(elemento.getDireccion());
                        etApellidos.setText(elemento.getApellidos());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /**
         * Modificamos
         */
        final Button btnModificar =  findViewById(R.id.btnModificar);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Optenemos los 3 valores de los editText
                final String sNombre = etNombre.getText().toString();
                final String sApellidos = etApellidos.getText().toString();
                final String sDireccion = etDireccion.getText().toString();

                // Hacemos una comprobacion sobre el si los campos no estan vacios
                if (!TextUtils.isEmpty(sNombre) ||  !TextUtils.isEmpty(sApellidos) || !TextUtils.isEmpty(sDireccion) ){
                    // Hacemos una Busqueda por nombre del Usuario
                    Query q=bbdd.orderByChild(getString(R.string.campo_usuario)).equalTo(idUsuario);
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                // clave obtiene el id de la fila del JSON
                                String clave =  datasnapshot.getKey();

                                // En las 3 siguientes lineas lo que hacemos es Cambiar los valores del JSON
                                bbdd.child(clave).child(getString(R.string.campo_nombre)).setValue(sNombre);
                                bbdd.child(clave).child(getString(R.string.campo_apellidos)).setValue(sApellidos);
                                bbdd.child(clave).child(getString(R.string.campo_direccion)).setValue(sDireccion);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
}
