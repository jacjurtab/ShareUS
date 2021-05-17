package com.example.shareus.ui.misviajes.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shareus.R;
import com.example.shareus.ui.Drawer;
import com.example.shareus.ui.ViajesViewModel;

public class ViajesPasajeroFragment extends Fragment {

    public ViajesPasajeroFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViajesViewModel viajesViewModel = new ViewModelProvider(this).get(ViajesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_viajes_pasajero, container, false);

        viajesViewModel.getViajes(ViajesViewModel.Tipo.PASAJERO).observe(getViewLifecycleOwner(), viajes -> {
            Drawer.renderViajes(viajes, this.getContext(), root);
        });

        return root;
    }
}