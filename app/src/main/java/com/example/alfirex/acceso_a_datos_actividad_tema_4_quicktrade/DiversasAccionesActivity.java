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

        // Boton que nos enviara al Perfil
        final Button btnPerfil =  findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityPerfil = new Intent(v.getContext(), PerfilActivity.class);
                startActivity(iActivityPerfil);
            }
        });

        final Button btnAddProducto =  findViewById(R.id.btnAñadirProductos);
        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityAñadirProducto = new Intent(v.getContext(), addProductoActivity.class);
                startActivity(iActivityAñadirProducto);
            }
        });

        final Button btnModificarProducto =  findViewById(R.id.btnModificarlos);
        btnModificarProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityModificarProducto = new Intent(v.getContext(), modificarProductoActivity.class);
                startActivity(iActivityModificarProducto);
            }
        });

        final Button btnBorrarProducto =  findViewById(R.id.btnBorrar);
        btnBorrarProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityBorrarProducto = new Intent(v.getContext(), BorrarProductoActivity.class);
                startActivity(iActivityBorrarProducto);
            }
        });

        final Button btnBusquedaPorCategoriaProducto =  findViewById(R.id.btnBusquedaCategoria);
        btnBusquedaPorCategoriaProducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityBusquedaCategoriaProducto = new Intent(v.getContext(), BusquedaPorCategoriaActivity.class);
                startActivity(iActivityBusquedaCategoriaProducto);
            }
        });

        final Button btnBusquedaProdUsuario =  findViewById(R.id.btnBuscarProdUsser);
        btnBusquedaProdUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityBusquedaUsuarioProducto = new Intent(v.getContext(), BusquedaProdUserActivity2.class);
                startActivity(iActivityBusquedaUsuarioProducto);
            }
        });

        final Button btnAddFav =  findViewById(R.id.btnAddFavr);
        btnAddFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityBusquedaUsuarioProducto = new Intent(v.getContext(), FavoritoActivity.class);
                startActivity(iActivityBusquedaUsuarioProducto);
            }
        });
        final Button btnMostrarFav =  findViewById(R.id.btnMostrarFav);
        btnMostrarFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iActivityBusquedaUsuarioProducto = new Intent(v.getContext(), MostrarFavActivity.class);
                startActivity(iActivityBusquedaUsuarioProducto);
            }
        });
    }


}
