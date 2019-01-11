package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

public class MostrarFavActivity extends AppCompatActivity {
    Spinner spin_group_user;
    DatabaseReference bbdd, bbdd2;
    private ArrayList<String> listado = new ArrayList<>();
    ListView lv;
    private ArrayList<Usuario> listadoUsuario = new ArrayList<>();
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_fav);
        lv =  findViewById(R.id.listviewMostrarFAV);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();// Obtenemos el valor del usuario logueado
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
                    listadoUsuario.add(new Usuario(usuario, correo, nombre, apellido, contrasenya, sDireccion, userid));
                }
                // Iteramos el ArrayList para mostrar la informacion de cada objeto
                for (Usuario elemento : listadoUsuario) {
                    // En el IF comprovamos de que el user logueado actualmente coincide con el que este en el ArrayList, y si esta que almacene/sette el texto de los valores
                    if (elemento.getIduser().compareTo(user.getUid()) == 0) {
                        idUsuario = elemento.getUsuario();// obtenemos el Usuario loqueado
                    }
                }
                // Cogemos la referencia del Nodo de Firebase
                bbdd = FirebaseDatabase.getInstance().getReference( getString(R.string.nodo_producto) );

                bbdd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Recorremos todos  los Usuarios para despues almacenarlos en un ArrayList
                        for(DataSnapshot datasnapshot : dataSnapshot.getChildren()){

                            Producto oProducto = datasnapshot.getValue(Producto.class);// Obtenemos el objeto de usuario
                            String nombre = oProducto.getNombre(); // Obtenemos el usuario del objeto Usuario


                            ArrayAdapter<String> adaptador;

                                if (oProducto.getFav().compareTo("true") == 0) { // Comprovamos de que el producto sea del usuario seleccionado

                                    listado.add(nombre);// Añadimos al ArrayList la usuario
                                }





                            adaptador = new ArrayAdapter<>(MostrarFavActivity.this,android.R.layout.simple_list_item_1,listado);
                            lv.setAdapter(adaptador);
                        }

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







    }
}
