package com.example.shareus.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shareus.DetailActivity;
import com.example.shareus.R;
import com.example.shareus.Utils;
import com.example.shareus.model.ListAdapter;
import com.example.shareus.model.Viaje;

import java.util.Date;
import java.util.List;

public class ViajesDrawer {

    public static void renderViajes(List<Viaje> viajes, Context cxt, View view) {
        ListView lista = view.findViewById(R.id.listado);
        View empty = view.findViewById(R.id.empty);
        View loading = view.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        lista.setEmptyView(loading);
        lista.setAdapter(new ListAdapter(cxt, R.layout.viaje, viajes) {
            @SuppressLint("SetTextI18n")
            @Override
            public void crearEntrada(Object entrada, View view) {
                Viaje viaje = (Viaje) entrada;
                if(viaje != null) {
                    TextView origen = view.findViewById(R.id.tw_origen);
                    origen.setText(viaje.getOrigen());

                    TextView destino = view.findViewById(R.id.tw_destino);
                    destino.setText(viaje.getDestino());

                    TextView fecha = view.findViewById(R.id.tw_fecha);
                    fecha.setText(Utils.prettyParse(new Date(viaje.getFecha_hora().getTime())));

                    TextView pasajeros = view.findViewById(R.id.tw_pasajeros);
                    pasajeros.setText(viaje.getNum_pasajeros() + "/" + viaje.getMax_plazas());

                    TextView conductor = view.findViewById(R.id.tw_conductor);
                    conductor.setText(viaje.getConductor());

                    TextView valoracion = view.findViewById(R.id.tw_valoracion);
                    valoracion.setText(Float.toString(viaje.getNota_conductor()));

                    TextView precio = view.findViewById(R.id.tw_precio);
                    precio.setText(viaje.getPrecio() + "€/plaza");
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        });

        lista.setOnItemClickListener((adapterView, objectView, i, l) -> {
            Intent intent = new Intent(cxt, DetailActivity.class);
            intent.putExtra("id", viajes.get(i).getId());
            cxt.startActivity(intent);
            Activity act = (Activity) cxt;
            act.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
        loading.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
        lista.setEmptyView(empty);
    }

}
