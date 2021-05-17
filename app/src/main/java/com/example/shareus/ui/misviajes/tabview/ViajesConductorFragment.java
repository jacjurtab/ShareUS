package com.example.shareus.ui.misviajes.tabview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shareus.DetailActivity;
import com.example.shareus.R;
import com.example.shareus.Utils;
import com.example.shareus.model.Viaje;
import com.example.shareus.model.ListAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViajesConductorFragment extends Fragment {

    ListView lista;
    List<Viaje> viajes;

    public ViajesConductorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viajes = new ArrayList<>();

        viajes.add(new Viaje(6, "Ángel", "La Macarena", "CAMPUS VIAPOL", new Timestamp(1620117000000L), 2, 4, null, 5.7f));
        viajes.add(new Viaje(5, "Celia", "Sevilla Este", "FACULTAD COMUNICACIÓN", new Timestamp(1621060200000L), 0, 3, null, 8.4f));
        viajes.add(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viajes_conductor, container, false);
        ExtendedFloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action",
                Snackbar.LENGTH_LONG).setAction("Action", null).show());

        Context cxt = this.getContext();
        lista = root.findViewById(R.id.listado);
        lista.setAdapter(new ListAdapter(this.getContext(), R.layout.viaje, viajes) {
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
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        });

        lista.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(cxt, DetailActivity.class);
            intent.putExtra("id", viajes.get(i).getId());
            startActivity(intent);
            Activity act = (Activity) cxt;
            if (act != null) {
                act.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

        });

        return root;
    }
}