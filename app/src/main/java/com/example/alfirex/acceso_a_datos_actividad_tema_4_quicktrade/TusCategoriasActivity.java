package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

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

public class TusCategoriasActivity extends AppCompatActivity {
    Spinner spin_group_categorias;
    private ArrayList<String> listado2 = new ArrayList<>();
    DatabaseReference bbdd,bbddUsu;
    ListView lv;

    private ArrayList<Usuario> listadoUsuario = new ArrayList<>();
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tus_categorias);

        lv =  findViewById(R.id.listviewTusCate) ;
        spin_group_categorias = findViewById(R.id.spinner_tus_categoria);
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_producto));

        ArrayAdapter<String> adaptador;
        listado2.add("tecnologıa");
        listado2.add("coches");
        listado2.add("hogar");

        adaptador = new ArrayAdapter<>(TusCategoriasActivity.this,android.R.layout.simple_list_item_1,listado2);
        spin_group_categorias.setAdapter(adaptador);// Rellenamos los Option del Select

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();// Obtenemos el valor del usuario logueado

        bbddUsu = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        bbddUsu.addValueEventListener(new ValueEventListener() {
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
                    listadoUsuario.add(new Usuario(usuario, correo, nombre, apellido, contrasenya, sDireccion, userid));
                }
                // Iteramos el ArrayList para mostrar la informacion de cada objeto
                for (Usuario elemento : listadoUsuario) {
                    // En el IF comprovamos de que el user logueado actualmente coincide con el que este en el ArrayList, y si esta que almacene/sette el texto de los valores
                    if (elemento.getIduser().compareTo(user.getUid()) == 0) {
                        idUsuario = elemento.getUsuario();// obtenemos el Usuario loqueado
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        final Button btnBusquedaPorCategoriaProducto = findViewById(R.id.btnBuscarTusCategoriaPROD);
        btnBusquedaPorCategoriaProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Log.d("Prueba", idUsuario);


                bbdd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String sCategoria = spin_group_categorias.getSelectedItem().toString();
                        ArrayAdapter<String> adaptador;
                        ArrayList<Producto> listado = new ArrayList<>();
                        ArrayList<String> listadoString = new ArrayList<>();


                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                            Producto oProducto = datasnapshot.getValue(Producto.class);
                            String sNombre = oProducto.getNombre();
                            String sDescripcion = oProducto.getDescripcion();
                            String sPrecio = oProducto.getPrecio();
                            String sUsuario = oProducto.getUsuario();
                            String categoria = oProducto.getCategoria();

                            listado.add(new Producto(sNombre, sDescripcion, categoria, sPrecio, sUsuario));// Rellenamos el Objeto Producto

                        }
                        // Iteramos el ArrayList para mostrar la informacion de cada objeto
                        for (Producto elemento : listado) {
                            Log.d("Prueba", "Nombre: " + elemento.getNombre() + " CAtegotia: " + elemento.getCategoria());
                            Log.d("Prueba", "GetUsuario: "+ elemento.getUsuario() + ", idUsuario: "+ idUsuario);


                            if (elemento.getUsuario().compareTo(idUsuario) == 0) {

                                if (elemento.getCategoria().compareTo(sCategoria) == 0) {

                                    listadoString.add(elemento.toString());
                                }
                            }
                        }
                        adaptador = new ArrayAdapter<>(TusCategoriasActivity.this,android.R.layout.simple_list_item_1,listadoString);
                        lv.setAdapter(adaptador);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
