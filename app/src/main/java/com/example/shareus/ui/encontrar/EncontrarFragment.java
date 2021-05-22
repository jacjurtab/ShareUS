package com.example.shareus.ui.encontrar;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.example.shareus.R;
import com.example.shareus.Utils;
import com.example.shareus.api.ApiREST;
import com.example.shareus.model.Ubicacion;
import com.example.shareus.ui.ViajesViewModel;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = ApiREST.getInstance(getContext()).getQueue();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.encontrar_fragment, container, false);
        ViajesViewModel viajesViewModel = new ViewModelProvider(requireActivity()).get(ViajesViewModel.class);

        btnBuscar = root.findViewById(R.id.btn_buscar);
        spinnerOr = root.findViewById(R.id.spinnerOr);
        spinnerDest = root.findViewById(R.id.spinnerDest);

        ApiREST.obtenerUbicaciones(0,  mRequestQueue, res -> {
            ubicaciones = (List<Ubicacion>) res;
            ApiREST.obtenerUbicaciones(1, mRequestQueue, res1 -> {
                universidades = (List<Ubicacion>) res1;

                ubicaciones.addAll(universidades);
                String[] ubs = Utils.getListaNombres(ubicaciones);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.simple_item, ubs);
                spinnerOr.setAdapter(adapter);
                spinnerDest.setAdapter(adapter);
            });
        });

        btnBuscar.setOnClickListener(v -> {
            viajesViewModel.changeLocations((String) spinnerOr.getSelectedItem(), (String) spinnerDest.getSelectedItem());
            NavController navController = Navigation.findNavController((Activity) getContext(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_resultados);
        });


        /*spinnerOr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });*/
        return root;
    }
}