package com.example.shareus.ui.encontrar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shareus.R;
import com.example.shareus.ui.ViajesDrawer;
import com.example.shareus.ui.ViajesViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViajesEncontradosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViajesEncontradosFragment extends Fragment {

    private static final String ARG_DESTINO = "or";
    private static final String ARG_ORIGEN = "dest";
    String origen;
    String destino;
    private static ViajesViewModel viajesViewModel;
    private static Context context;


    public ViajesEncontradosFragment() {
        // Required empty public constructor
    }

    public static ViajesEncontradosFragment newInstance(String origen, String destino) {
        ViajesEncontradosFragment fragment = new ViajesEncontradosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ORIGEN, origen);
        args.putString(ARG_DESTINO, destino);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            origen = getArguments().getString(ARG_ORIGEN);
            destino = getArguments().getString(ARG_DESTINO);

            System.out.println("ORIGEN: " + origen);
            System.out.println("DESTINO: " + destino);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viajes_encontrados, container, false);
        context = getContext();
        viajesViewModel = new ViewModelProvider(this).get(ViajesViewModel.class);
        viajesViewModel.actualizarViajes(ViajesViewModel.Tipo.OR_DEST, origen, destino, context);
        Log.d("DEBUG", "Actualizando viajes coincidentes");
        ExtendedFloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Fragment encontrarFragment = new EncontrarFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.encontrar_fr, encontrarFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViajesViewModel viajesViewModel = new ViewModelProvider(this).get(ViajesViewModel.class);
        viajesViewModel.getViajes(ViajesViewModel.Tipo.OR_DEST, origen, destino, context).observe(getViewLifecycleOwner(), viajes -> {
            Log.d("DEBUG", "Recibida actualizacion: " + viajes.size());
            ViajesDrawer.renderViajes(viajes, this.getContext(), this.getView());
        });
    }
}