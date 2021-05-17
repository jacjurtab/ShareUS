package com.example.shareus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shareus.model.ListAdapter;
import com.example.shareus.model.Pasajero;
import com.example.shareus.model.Viaje;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ListView lista;
    List<Pasajero> pasajeros;
    Activity cxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cxt = this;
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ExtendedFloatingActionButton back = findViewById(R.id.backwards);
        back.setOnClickListener(view -> {
            finish();
            cxt.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        });

        int id = getIntent().getIntExtra("id", -1);

        pasajeros = new ArrayList<>();
        pasajeros.add(new Pasajero(1, "Juan"));
        pasajeros.add(new Pasajero(2, "Maria"));
        pasajeros.add(new Pasajero(3, "Paula"));

        Viaje viaje = new Viaje(id, "Ángel", "Espartinas", "CAMPUS REINA MERCEDES",
                new Timestamp(1621274024680L), 3, 4, pasajeros, 5.6f);

        render(viaje);
    }


    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void render(Viaje viaje) {
        boolean esConductor = false;
        int idUsuario = MainActivity.getUserId();

        TextView titulo = findViewById(R.id.titulo);
        titulo.setText("Viaje programado para " + Utils.prettyParse(viaje.getFecha_hora()));
        TextView conductor = findViewById(R.id.conductor);
        if(esConductor)
            conductor.setText("Tú");
        else
            conductor.setText(viaje.getConductor());
        TextView valoracion = findViewById(R.id.valoracion);
        valoracion.setText(Float.toString(viaje.getNota_conductor()));
        TextView origen = findViewById(R.id.origen_value);
        origen.setText(viaje.getOrigen());
        TextView destino = findViewById(R.id.destino_value);
        destino.setText(viaje.getDestino());
        TextView precio = findViewById(R.id.precio_value);
        precio.setText("8€");
        TextView numPasajeros = findViewById(R.id.pasajeros_count);
        numPasajeros.setText("Pasajeros (" + viaje.getNum_pasajeros() + "/" + viaje.getMax_plazas() + ")");

        lista = findViewById(R.id.pasajeros_detalle);
        lista.setAdapter(new ListAdapter(this, R.layout.user_list, viaje.getPasajeros()) {
            @Override
            public void crearEntrada(Object entrada, View view) {
                Pasajero pasajero = (Pasajero) entrada;
                Button btn = view.findViewById(R.id.round_icon);
                TextView name = view.findViewById(R.id.user_name);
                if(pasajero.getId() == idUsuario)
                    name.setText(pasajero.getNombre() + " (Tú)");
                else
                    name.setText(pasajero.getNombre());
                btn.setText(pasajero.getNombre().substring(0, 1));
            }
        });

        ExtendedFloatingActionButton option = findViewById(R.id.btn_opcion);
        if(esConductor) {
            option.setIconResource(R.drawable.ic_baseline_delete_forever_24);
            option.setText("Eliminar Viaje");
            option.setOnClickListener(view -> {
                Snackbar.make(view, "Viaje eliminado correctamente", Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        finish();
                        cxt.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    }
                }).show();
            });
        } else {
            option.setIconResource(R.drawable.ic_baseline_add_24);
            option.setText("Inscribir en Viaje");
            option.setOnClickListener(view -> {
                Snackbar.make(view, "Añadido como pasajero al viaje", Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        finish();
                        cxt.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    }
                }).show();
            });
        }

    }
}