package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DiversasAccionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diversas_acciones);

        final Button btnAcceder = (Button) findViewById(R.id.btnPerfil);
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityPerfil = new Intent(v.getContext(), PerfilActivity.class);
                startActivity(iActivityPerfil);
            }
        });

        final Button btnAddProducto= (Button) findViewById(R.id.btnAñadirProductos);
        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityAñadirProducto = new Intent(v.getContext(), addProductoActivity.class);
                startActivity(iActivityAñadirProducto);
            }
        });

        final Button btnModificarProducto= (Button) findViewById(R.id.btnModificarlos);
        btnModificarProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityModificarProducto = new Intent(v.getContext(), modificarProductoActivity.class);
                startActivity(iActivityModificarProducto);
            }
        });

        final Button btnBorrarProducto= (Button) findViewById(R.id.btnBorrar);
        btnBorrarProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityBorrarProducto = new Intent(v.getContext(), BorrarProductoActivity.class);
                startActivity(iActivityBorrarProducto);
            }
        });

        final Button btnBusquedaPorCategoriaProducto= (Button) findViewById(R.id.btnBusquedaCategoria);
        btnBusquedaPorCategoriaProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityBorrarProducto = new Intent(v.getContext(), BusquedaPorCategoriaActivity.class);
                startActivity(iActivityBorrarProducto);
            }
        });
    }


}
