package com.example.shareus.ui.encontrar;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shareus.R;
import com.example.shareus.ui.ViajesDrawer;
import com.example.shareus.ui.ViajesViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ViajesEncontradosFragment extends Fragment {

    ViajesViewModel viajesViewModel;

    public ViajesEncontradosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viajes_encontrados, container, false);

        viajesViewModel = new ViewModelProvider(requireActivity()).get(ViajesViewModel.class);
        viajesViewModel.actualizarViajes(ViajesViewModel.Tipo.OR_DEST, getContext());

        ExtendedFloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController((Activity) getContext(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_encontrar);
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viajesViewModel.getViajes(ViajesViewModel.Tipo.OR_DEST, getContext()).observe(getViewLifecycleOwner(), viajes -> {
            ViajesDrawer.renderViajes(viajes, this.getContext(), this.getView());
        });
    }
}