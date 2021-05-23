package com.example.shareus.ui.misviajes.tabs;

import android.content.Context;
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

public class ViajesPasadosFragment extends Fragment {

    private static ViajesViewModel viajesViewModel;

    public ViajesPasadosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viajesViewModel = new ViewModelProvider(this).get(ViajesViewModel.class);
        viajesViewModel.actualizarViajes(ViajesViewModel.Tipo.PASADOS, getContext());

        return inflater.inflate(R.layout.fragment_viajes_pasados, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viajesViewModel = new ViewModelProvider(this).get(ViajesViewModel.class);

        viajesViewModel.getViajes(ViajesViewModel.Tipo.PASADOS, getContext()).observe(getViewLifecycleOwner(), viajes -> {
            ViajesDrawer.renderViajes(viajes, this.getContext(), this.getView());
        });
    }

    public static void update(Context context) {
        viajesViewModel.actualizarViajes(ViajesViewModel.Tipo.PASADOS, context);
    }
}