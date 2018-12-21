package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class BusquedaPorCategoriaActivity extends AppCompatActivity {
    Spinner spin_group_categorias;
    private ArrayList<String> listado2 = new ArrayList<String>();
    DatabaseReference bbdd;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_por_categoria);

        lv = (ListView) findViewById(R.id.listviewCate) ;
        spin_group_categorias = (Spinner) findViewById(R.id.spinner_categoria);
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_producto));

        ArrayAdapter<String> adaptador;
        listado2.add("tecnologıa");
        listado2.add("coches");
        listado2.add("hogar");
        adaptador = new ArrayAdapter<>(BusquedaPorCategoriaActivity.this,android.R.layout.simple_list_item_1,listado2);
        spin_group_categorias.setAdapter(adaptador);

        final Button btnBusquedaPorCategoriaProducto = (Button) findViewById(R.id.btnBuscarCategoriaPROD);
        btnBusquedaPorCategoriaProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                bbdd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String sCategoria = spin_group_categorias.getSelectedItem().toString();
                            ArrayAdapter<String> adaptador;
                            ArrayList<Producto> listado = new ArrayList<Producto>();
                            ArrayList<String> listadoString = new ArrayList<String>();


                            for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                Producto oProducto = datasnapshot.getValue(Producto.class);
                                String sNombre = oProducto.getNombre();
                                String sDescripcion = oProducto.getDescripcion();
                                String sPrecio = oProducto.getPrecio();
                                String sUsuario = oProducto.getUsuario();
                                String categoria = oProducto.getCategoria();

                                listado.add(new Producto(sNombre, sDescripcion, categoria, sPrecio, sUsuario));


                            }
                            // Iteramos el ArrayList para mostrar la informacion de cada objeto
                            Iterator<Producto> nombreIterator = listado.iterator();
                            while (nombreIterator.hasNext()) {
                                Producto elemento = nombreIterator.next();
                                Log.d("Prueba", "Nombre: "+ elemento.getNombre()+" CAtegotia: "+ elemento.getCategoria());
                                if(elemento.getCategoria().compareTo(sCategoria) == 0){
                                    Log.d("Prueba", "Entra en IF");
                                    listadoString.add(elemento.toString());
                                }
                            }
                            adaptador = new ArrayAdapter<>(BusquedaPorCategoriaActivity.this,android.R.layout.simple_list_item_1,listadoString);
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
