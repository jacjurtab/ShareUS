package com.example.shareus.ui.encontrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shareus.R;

public class EncontrarFragment extends Fragment {

    private EncontrarViewModel encontrarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        encontrarViewModel =
                new ViewModelProvider(this).get(EncontrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_encontrar, container, false);
        final TextView textView = root.findViewById(R.id.text_encontrar);
        encontrarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}