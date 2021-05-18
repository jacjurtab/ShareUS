package com.example.shareus.ui.misviajes.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shareus.R;
import com.example.shareus.ui.ViajesDrawer;
import com.example.shareus.ui.ViajesViewModel;

public class ViajesPasajeroFragment extends Fragment {

    public ViajesPasajeroFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViajesViewModel viajesViewModel = new ViewModelProvider(this).get(ViajesViewModel.class);
        viajesViewModel.actualizarViajes(ViajesViewModel.Tipo.PASAJERO);

        return inflater.inflate(R.layout.fragment_viajes_pasajero, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViajesViewModel viajesViewModel = new ViewModelProvider(this).get(ViajesViewModel.class);

        viajesViewModel.getViajes(ViajesViewModel.Tipo.PASAJERO).observe(getViewLifecycleOwner(), viajes -> {
            ViajesDrawer.renderViajes(viajes, this.getContext(), this.getView());
        });
    }
}