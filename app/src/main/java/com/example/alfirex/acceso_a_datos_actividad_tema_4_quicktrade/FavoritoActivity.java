package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade.model.Producto;
import com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritoActivity extends AppCompatActivity {
    Spinner spin_group_user;
    DatabaseReference bbdd, bbdd2;
    private ArrayList<String> listado = new ArrayList<>();
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorito);

        spin_group_user =  findViewById(R.id.spinner_fav);

        // Cogemos la referencia del Nodo de Firebase
        bbdd2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_producto));//Obtenemos el Nodo Prodcuto

        bbdd2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<>();


                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto oProducto = datasnapshot.getValue(Producto.class);
                    String nombre = oProducto.getNombre();

                        listado.add(nombre);
                }
                adaptador = new ArrayAdapter<>(FavoritoActivity.this,android.R.layout.simple_list_item_1,listado);
                spin_group_user.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        final Button btnmodificar =  findViewById(R.id.btnFavorito);
        btnmodificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Optenemos los 4 valores de los editText
                final String sNombre = spin_group_user.getSelectedItem().toString();

                    Query q=bbdd2.orderByChild("nombre").equalTo(sNombre); // Hacemos una Busqueda por nombre del Usuario
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                                String clave =  datasnapshot.getKey();// clave obtiene el id de la fila del JSON

                                // En las 3 siguientes lineas lo que hacemos es Cambiar los valores del JSON
                                bbdd2.child(clave).child("fav").setValue("true");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


            }
        });

    }
}
