package com.example.shareus.ui.encontrar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.shareus.DetailActivity;
import com.example.shareus.R;
import com.example.shareus.Utils;
import com.example.shareus.api.ApiREST;
import com.example.shareus.model.Ubicacion;
import com.example.shareus.ui.publicar.PublicarViewModel;
import java.util.List;

public class EncontrarFragment extends Fragment {

    List<Ubicacion> ubicaciones;
    List<Ubicacion> universidades;
    RequestQueue mRequestQueue;
    Button btnBuscar;
    Spinner spinnerOr;
    Spinner spinnerDest;


    public static EncontrarFragment newInstance() {
        return new EncontrarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.encontrar_fragment, container, false);
        Context context = this.getContext();

        btnBuscar = root.findViewById(R.id.btn_buscar);
        spinnerOr = root.findViewById(R.id.spinnerOr);
        spinnerDest = root.findViewById(R.id.spinnerDest);
        mRequestQueue = ApiREST.getInstance(getContext()).getQueue();

        ApiREST.obtenerUbicaciones(0,  mRequestQueue, res -> {
            ubicaciones = (List<Ubicacion>) res;
            ApiREST.obtenerUbicaciones(1, mRequestQueue, res1 -> {
                System.out.println("om tipo cero"+ubicaciones.toString());
                universidades = (List<Ubicacion>) res1;
                ubicaciones.addAll(universidades);
                System.out.println("Todas las ubis"+ubicaciones.toString());
                String[] ubs = Utils.getListaNombres(ubicaciones);
                System.out.println("ubis string "+ubs.toString());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ubs);
                spinnerOr.setAdapter(adapter);
                spinnerDest.setAdapter(adapter);
            });
        });

        spinnerOr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerDest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Buscar pulsado");

                Fragment viajesEncontrados = ViajesEncontradosFragment.newInstance((String) spinnerOr.getSelectedItem(), (String) spinnerDest.getSelectedItem());

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.encontrar_fr, viajesEncontrados);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return root;
    }
}