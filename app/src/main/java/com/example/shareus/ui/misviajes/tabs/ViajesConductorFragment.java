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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ViajesConductorFragment extends Fragment {

    public ViajesConductorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viajes_conductor, container, false);

        ExtendedFloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action",
                Snackbar.LENGTH_LONG).setAction("Action", null).show());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViajesViewModel viajesViewModel = new ViewModelProvider(this).get(ViajesViewModel.class);

        viajesViewModel.getViajes(ViajesViewModel.Tipo.CONDUCTOR).observe(getViewLifecycleOwner(), viajes -> {
            ViajesDrawer.renderViajes(viajes, this.getContext(), this.getView());
        });
    }
}