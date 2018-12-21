package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.Iterator;

public class BorrarProductoActivity extends AppCompatActivity {

    Spinner spin_group;
    private String idUsuario;
    DatabaseReference bbdd,bbdd2;
    private ArrayList<Usuario> listado = new ArrayList<Usuario>();
    private ArrayList<String> listado2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_producto);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();// Obtenemos el valor del usuario logueado
        spin_group = (Spinner) findViewById(R.id.spinner);

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
                Iterator<Usuario> nombreIterator = listado.iterator();
                while (nombreIterator.hasNext()) {
                    Usuario elemento = nombreIterator.next();
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
                        ArrayList<String> listado = new ArrayList<String>();


                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                            Producto oProducto = datasnapshot.getValue(Producto.class);
                            String nombre = oProducto.getNombre();
                            String usuario = oProducto.getUsuario();

                            if (usuario.equals(idUsuario)){
                                listado.add(nombre);
                            }
                        }
                        adaptador = new ArrayAdapter<>(BorrarProductoActivity.this,android.R.layout.simple_list_item_1,listado);
                        spin_group.setAdapter(adaptador);

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


        /**
         * Borramos el elemento
         */
        final Button btnBorrar = (Button) findViewById(R.id.btnBorrarPROD);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Optenemos los 3 valores de los editText
                final String sNombre = spin_group.getSelectedItem().toString();

                // Hacemos una comprobacion sobre el si los campos no estan vacios
                if (!TextUtils.isEmpty(sNombre) ){

                    // Hacemos una Busqueda por nombre del Usuario
                    Query q=bbdd2.orderByChild("nombre").equalTo(sNombre);
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                // clave obtiene el id de la fila del JSON
                                String clave =  datasnapshot.getKey();

                                DatabaseReference ref = bbdd2.child(clave);
                                ref.removeValue();

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