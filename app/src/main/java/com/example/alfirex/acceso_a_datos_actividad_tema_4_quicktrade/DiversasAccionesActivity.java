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
    }


}
