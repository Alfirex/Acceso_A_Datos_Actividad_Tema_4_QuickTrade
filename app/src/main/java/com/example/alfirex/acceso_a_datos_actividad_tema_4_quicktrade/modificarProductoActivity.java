package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade.model.Producto;
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


public class modificarProductoActivity extends AppCompatActivity {
    EditText  etDescripcion, etPrecio;
    Spinner spin_group_producto;
    private String idUsuario;
    DatabaseReference bbdd,bbdd2;
    private ArrayList<Usuario> listado = new ArrayList<>();
    private ArrayList<String> listadoOptions = new ArrayList<>();
    Spinner spin_group_categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();// Obtenemos la conexion de FIrebase
        final FirebaseUser user = mAuth.getCurrentUser();// Obtenemos el valor del usuario logueado

        // Obtenemos las referencias de los editTex
        etDescripcion =  findViewById(R.id.etDescripcion);
        etPrecio =  findViewById(R.id.etPrecio);
        // Obtenemos el id
        spin_group_categorias =  findViewById(R.id.spinnerCategoria);
        spin_group_producto =  findViewById(R.id.spinner);

        ArrayAdapter<String> adaptador;
        listadoOptions.add("tecnologıa");
        listadoOptions.add("coches");
        listadoOptions.add("hogar");

        adaptador = new ArrayAdapter<>(modificarProductoActivity.this,android.R.layout.simple_list_item_1, listadoOptions);
        spin_group_categorias.setAdapter(adaptador);// Llenamos las opciones del Select

        // Cogemos la referencia del Nodo de Firebase
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Recorremos todos  los Usuarios para despues almacenarlos en un ArrayList
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Usuario oUsuario = datasnapshot.getValue(Usuario.class);

                    String usuario = oUsuario.getUsuario();
                    String nombre = oUsuario.getNombre();
                    String apellido = oUsuario.getApellidos();
                    String correo = oUsuario.getCorreo();
                    String contrasenya = oUsuario.getContraseña();
                    String sDireccion = oUsuario.getDireccion();
                    String userid = oUsuario.getIduser();

                    // Añadimos toda al ArrayLiadaptadorst la informacion de cada Usuario
                    listado.add(new Usuario(usuario, correo, nombre, apellido, contrasenya, sDireccion, userid));
                }
                // Iteramos el ArrayList para mostrar la informacion de cada objeto
                for (Usuario elemento : listado) {
                    // En el IF comprovamos de que el user logueado actualmente coincide con el que este en el ArrayList, y si esta que almacene/sette el texto de los valores
                    if (elemento.getIduser().compareTo(user.getUid()) == 0) {
                        // obtenemos el Usuario loqueado
                        idUsuario = elemento.getUsuario();
                    }
                }
                bbdd2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_producto));//Obtenemos el Nodo Prodcuto

                bbdd2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                        ArrayAdapter<String> adaptador;
                        ArrayList<String> listado = new ArrayList<>();


                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                            Producto oProducto = datasnapshot.getValue(Producto.class);
                            String nombre = oProducto.getNombre();
                            String usuario = oProducto.getUsuario();

                            if (usuario.equals(idUsuario)){
                                listado.add(nombre);
                            }
                        }
                        adaptador = new ArrayAdapter<>(modificarProductoActivity.this,android.R.layout.simple_list_item_1,listado);
                        spin_group_producto.setAdapter(adaptador);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        final Button btnmodificar =  findViewById(R.id.btnModificarPROD);
        btnmodificar.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
              // Optenemos los 4 valores de los editText
               final String sNombre = spin_group_producto.getSelectedItem().toString();
               final String sDescripcion = etDescripcion.getText().toString();
               final String sCategoria = spin_group_categorias.getSelectedItem().toString();
               final String sPrecio = etPrecio.getText().toString();


                // Hacemos una comprobacion sobre el si los campos no estan vacios
               if (!TextUtils.isEmpty(sNombre) ||  !TextUtils.isEmpty(sDescripcion) || !TextUtils.isEmpty(sCategoria)  || !TextUtils.isEmpty(sPrecio)){

                   Query q=bbdd2.orderByChild("nombre").equalTo(sNombre); // Hacemos una Busqueda por nombre del Usuario
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                                String clave =  datasnapshot.getKey();// clave obtiene el id de la fila del JSON

                                // En las 3 siguientes lineas lo que hacemos es Cambiar los valores del JSON
                                bbdd2.child(clave).child("descripcion").setValue(sDescripcion);
                                bbdd2.child(clave).child("categoria").setValue(sCategoria);
                                bbdd2.child(clave).child("precio").setValue(sPrecio);
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