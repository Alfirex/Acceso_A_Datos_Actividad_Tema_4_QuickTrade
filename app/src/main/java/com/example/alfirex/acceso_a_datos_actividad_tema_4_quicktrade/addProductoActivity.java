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
import android.widget.Toast;

import com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade.model.Producto;
import com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addProductoActivity extends AppCompatActivity {
    EditText etNombre, etDescripcion, etPrecio;
    private String idUsuario;
    DatabaseReference bbdd,bbdd2;
    private ArrayList<Usuario> listado = new ArrayList<>();
    private ArrayList<String> listadoOptions = new ArrayList<>();
    Spinner spin_group_categorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producto);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();// Obtenemos el valor del usuario logueado

        etNombre =  findViewById(R.id.etNombre);
        etDescripcion =  findViewById(R.id.etDescripcion);
        spin_group_categorias =  findViewById(R.id.spinnerCategoria);
        etPrecio =  findViewById(R.id.etPrecio);

        ArrayAdapter<String> adaptador;
        listadoOptions.add("tecnologıa");
        listadoOptions.add("coches");
        listadoOptions.add("hogar");

        adaptador = new ArrayAdapter<>(addProductoActivity.this,android.R.layout.simple_list_item_1, listadoOptions);
        spin_group_categorias.setAdapter(adaptador); // Llenamos los options del Select

        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));// Cogemos la referencia del Nodo de Firebase

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


                    listado.add(new Usuario(usuario, correo, nombre, apellido, contrasenya, sDireccion, userid));  // Añadimos toda al ArrayList la informacion de cada Usuario
                }
                // Iteramos el ArrayList para mostrar la informacion de cada objeto
                for (Usuario elemento : listado) {
                    // En el IF comprovamos de que el user logueado actualmente coincide con el que este en el ArrayList, y si esta que almacene/sette el texto de los valores
                    if (elemento.getIduser().compareTo(user.getUid()) == 0) {
                        idUsuario = elemento.getUsuario();// Almacenamos el nombre de Usuario del ArrayList
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final Button btnAñadir = findViewById(R.id.btnAñadir);
        btnAñadir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Optenemos los 3 valores de los editText
                final String sNombre = etNombre.getText().toString();
                final String sDescripcion = etDescripcion.getText().toString();
                final String sCategoria = spin_group_categorias.getSelectedItem().toString();
                final String sPrecio = etPrecio.getText().toString();


                // Hacemos una comprobacion sobre el si los campos no estan vacios
                if (!TextUtils.isEmpty(sNombre) ||  !TextUtils.isEmpty(sDescripcion) || !TextUtils.isEmpty(sCategoria)  || !TextUtils.isEmpty(sPrecio)){
                    // Creamos el Objeto Producto y Introducimos los valores de editText, ademas el usuario actual
                    Producto oProducto = new Producto(sNombre,sDescripcion,sCategoria,sPrecio,idUsuario);
                    bbdd2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_producto));//Obtenemos el Nodo

                    String clave = bbdd2.push().getKey();//Generamos una clave para el Nodo

                    bbdd2.child(clave).setValue(oProducto);// Insertamaos el Producto en la clave que hemos creado

                    Toast.makeText(addProductoActivity.this, "Se ha añadido el Producto." ,
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(addProductoActivity.this, "Deves Introducir todos los valores" ,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
